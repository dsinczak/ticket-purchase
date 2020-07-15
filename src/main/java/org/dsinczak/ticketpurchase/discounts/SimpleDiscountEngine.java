package org.dsinczak.ticketpurchase.discounts;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.dsinczak.ticketpurchase.domain.Customer;
import org.dsinczak.ticketpurchase.domain.Discount;
import org.dsinczak.ticketpurchase.domain.Flight;
import org.dsinczak.ticketpurchase.domain.Flight.Route.Continent;
import org.dsinczak.ticketpurchase.domain.Tenant;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class SimpleDiscountEngine implements DiscountEngine {

    private final Set<DiscountRule> rules;

    public SimpleDiscountEngine(Set<DiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    public Set<Discount> checkForDiscounts(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
        return rules.foldLeft(
                HashSet.empty(),
                (discounts, rule) -> discounts.addAll(rule.apply(customer, tenant, flight, dayOfWeek).toSet())
        );
    }

    @FunctionalInterface
    interface DiscountRule {
        Option<Discount> apply(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek);
    }

    @Component
    public static class CustomerBirthdayDiscountRule implements DiscountRule {

        @Override
        public Option<Discount> apply(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
            var today = LocalDate.now();
            if (today.getMonth()==customer.getDateOfBirth().getMonth() && today.getDayOfWeek() == customer.getDateOfBirth().getDayOfWeek()) {
                return Option.of(new Discount(Money.of(5, "PLN"),"Birthday discount"));
            }
            return Option.none();
        }
    }

    @Component
    public static class ThursdayAfricaFlightDiscountRule implements DiscountRule {

        @Override
        public Option<Discount> apply(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek) {
           if(dayOfWeek.equals(DayOfWeek.THURSDAY) && flight.getRoute().isToContinent(Continent.AF)) {
               return Option.of(new Discount(Money.of(5, "PLN"),"Thursday Africa flight discount"));
           }
           return Option.none();
        }
    }

}
