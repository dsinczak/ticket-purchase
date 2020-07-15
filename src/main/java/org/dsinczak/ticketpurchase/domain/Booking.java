package org.dsinczak.ticketpurchase.domain;

import io.vavr.collection.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.dsinczak.ticketpurchase.domain.Flight.FlightId;
import org.dsinczak.ticketpurchase.domain.Tenant.TenantId;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Booking {

    BookingId bookingId;
    TenantId tenantId;
    FlightId flightId;
    Money price;
    Set<Discount> discounts;

    Booking(TenantId tenantId, FlightId flightId, Money price, Set<Discount> discounts) {
        this(BookingId.random(), tenantId, flightId, price, discounts);
    }

    @Value
    public static class BookingId {
        UUID uuid;

        static BookingId random() {
            return new BookingId(UUID.randomUUID());
        }
    }

}
