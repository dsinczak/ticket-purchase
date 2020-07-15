package org.dsinczak.ticketpurchase.domain;

import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class Customer {

    CustomerId customerId;
    LocalDate dateOfBirth;

    @Value
    public static class CustomerId {
        UUID uuid;
    }
}
