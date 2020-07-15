package org.dsinczak.ticketpurchase.discounts;

import io.vavr.collection.Set;
import org.dsinczak.ticketpurchase.domain.Customer;
import org.dsinczak.ticketpurchase.domain.Discount;
import org.dsinczak.ticketpurchase.domain.Flight;
import org.dsinczak.ticketpurchase.domain.Tenant;

import java.time.DayOfWeek;

/**
 * In 'prod' like environment most probably some rule engine would be used. Hiding behind abstraction
 * allows such change.
 */
@FunctionalInterface
public interface DiscountEngine {

    Set<Discount> checkForDiscounts(Customer customer, Tenant tenant, Flight flight, DayOfWeek dayOfWeek);

}
