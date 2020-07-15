package org.dsinczak.ticketpurchase.domain;

import io.vavr.collection.HashSet;
import io.vavr.control.Either;
import org.dsinczak.ticketpurchase.discounts.DiscountEngine;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Set;

import static org.dsinczak.ticketpurchase.domain.DomainError.FLIGHT_DAY_NOT_SUPPORTED;
import static org.dsinczak.ticketpurchase.domain.DomainError.UNKNOWN_TENANT_TYPE;

/**
 * This is basically abstract factory pattern. I use inner classes as at this point
 * it was just more readable for me but of course when implementation grows we would have
 * to got higher.
 * I assumed case when we have different behaviour depending on tenant type is specific and might
 * be extended, using this approach answers the question: "and what if i would like to
 * differentiate behaviour basing on ...."
 */
@FunctionalInterface
public interface BookingFactory {

    /**
     * @return currently there are no validation rules. But i left either as result type
     * as factory responsibility is to validate creation request.
     */
    Either<DomainError, Booking> create(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek);

    @Primary
    @Component
    class BaseBookingFactory implements BookingFactory {

        private final GroupABookingFactory groupABookingFactory;
        private final GroupBBookingFactory groupBBookingFactory;

        public BaseBookingFactory(GroupABookingFactory groupABookingFactory, GroupBBookingFactory groupBBookingFactory) {
            this.groupABookingFactory = groupABookingFactory;
            this.groupBBookingFactory = groupBBookingFactory;
        }

        @Override
        public Either<DomainError, Booking> create(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
            if(!flight.getDays().contains(dayOfWeek)) {
                // This is just single validation. I suppose this would extend in
                // PRD code so io.vavr.control.Validation would be the best choice
                // to do so (and it smoothly integrates with Either).
                return Either.left(FLIGHT_DAY_NOT_SUPPORTED);
            }
            return switch (tenant.getType()) {
                case A -> groupABookingFactory.create(customer, tenant, flight, dayOfWeek);
                case B -> groupBBookingFactory.create(customer, tenant, flight, dayOfWeek);
                default -> Either.left(UNKNOWN_TENANT_TYPE);
            };
        }
    }

    @Component
    class GroupABookingFactory implements BookingFactory {
        private final DiscountEngine discountEngine;
        private final DiscountApplyPolicy discountApplyPolicy;

        public GroupABookingFactory(DiscountEngine discountEngine, DiscountApplyPolicy discountApplyPolicy) {
            this.discountEngine = discountEngine;
            this.discountApplyPolicy = discountApplyPolicy;
        }

        @Override
        public Either<DomainError, Booking> create(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
            var discounts = discountEngine.checkForDiscounts(customer, tenant, flight, dayOfWeek);
            var appliedDiscounts = discountApplyPolicy.calculate(flight.getPrice(), discounts);

            return Either.right(new Booking(
                    tenant.getTenantId(),
                    flight.getFlightId(),
                    appliedDiscounts.getFinalPrice(),
                    appliedDiscounts.getDiscounts()
            ));
        }
    }

    @Component
    class GroupBBookingFactory implements BookingFactory {
        private final DiscountEngine discountEngine;
        private final DiscountApplyPolicy discountApplyPolicy;

        public GroupBBookingFactory(DiscountEngine discountEngine, DiscountApplyPolicy discountApplyPolicy) {
            this.discountEngine = discountEngine;
            this.discountApplyPolicy = discountApplyPolicy;
        }

        @Override
        public Either<DomainError, Booking> create(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
            var discounts = discountEngine.checkForDiscounts(customer, tenant, flight, dayOfWeek);
            var appliedDiscounts = discountApplyPolicy.calculate(flight.getPrice(), discounts);

            return Either.right(new Booking(
                    tenant.getTenantId(),
                    flight.getFlightId(),
                    appliedDiscounts.getFinalPrice(),
                    HashSet.empty()
            ));
        }
    }

}
