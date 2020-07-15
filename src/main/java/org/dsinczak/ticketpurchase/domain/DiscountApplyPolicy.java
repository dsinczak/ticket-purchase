package org.dsinczak.ticketpurchase.domain;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.Value;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

@FunctionalInterface
public interface DiscountApplyPolicy {

    AppliedDiscounts calculate(Money price, Set<Discount> discounts);

    @Component
    class ThresholdDiscountApplyPolicy implements DiscountApplyPolicy {

        // This might be a subject of moving to configuration
        private static final Money THRESHOLD = Money. of(20, "PLN");

        @Override
        public AppliedDiscounts calculate(Money flightPrice, Set<Discount> discounts) {
            /*
             * Idea is simple, go through all discounts and try to apply it by checking
             * threshold rule. If it can be applied then do it, otherwise go to next
             * discount.
             */
            return discounts.foldLeft(new AppliedDiscounts(flightPrice, HashSet.of()),
                    (appliedDiscounts, discount) -> {
                        var discountedPrice = appliedDiscounts.finalPrice.subtract(discount.getValue());
                        if (discountedPrice.isLessThan(THRESHOLD)) {
                            return appliedDiscounts;
                        } else {
                            return new AppliedDiscounts(
                                    discountedPrice,
                                    appliedDiscounts.getDiscounts().add(discount)
                            );
                        }
                    }
            );
        }
    }

    @Value
    class AppliedDiscounts {
        Money finalPrice;
        Set<Discount> discounts;
    }

}
