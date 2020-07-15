package org.dsinczak.ticketpurchase.domain;

import io.vavr.control.Option;

public interface CustomerRepository {

    Option<Customer> find(Customer.CustomerId customerId);

}
