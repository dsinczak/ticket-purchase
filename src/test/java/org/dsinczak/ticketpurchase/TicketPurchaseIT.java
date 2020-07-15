package org.dsinczak.ticketpurchase;

import io.vavr.collection.HashSet;
import io.vavr.control.Option;
import org.dsinczak.ticketpurchase.domain.*;
import org.dsinczak.ticketpurchase.domain.Customer.CustomerId;
import org.dsinczak.ticketpurchase.domain.Flight.FlightId;
import org.dsinczak.ticketpurchase.domain.Flight.Route.Country;
import org.dsinczak.ticketpurchase.domain.Tenant.TenantId;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TicketPurchaseIT {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookingRepository bookingRepository() {
            return new BookingRepository() {
                @Override
                public Booking save(Booking booking) {
                    return booking;
                }
            };
        }
    }

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    FlightRepository flightRepository;

    @MockBean
    TenantRepository tenantRepository;

    @Autowired
    TicketPurchaseService ticketPurchaseService;

    @Test
    void shouldApplyDiscounts() {
        // Given
        var tenantId = TenantId.random();
        var flightId = FlightId.random();
        var customerId = CustomerId.random();

        when(tenantRepository.find(tenantId))
                .thenReturn(Option.of(new Tenant(tenantId, Tenant.Group.A)));
        when(flightRepository.find(flightId))
                .thenReturn(Option.of(
                        new Flight(
                                flightId,
                                Money.of(30, "PLN"),
                                LocalTime.MIDNIGHT,
                                HashSet.of(DayOfWeek.THURSDAY, DayOfWeek.TUESDAY),
                                new Flight.Route(Country.AF, Country.SA)
                        )
                ));
        when(customerRepository.find(customerId))
                .thenReturn(Option.of(
                        new Customer(customerId, LocalDate.now().minusYears(35))
                ));

        // When
        var result = ticketPurchaseService.buyTicket(customerId, tenantId, flightId, DayOfWeek.THURSDAY);

        // Then
        assertTrue(result.isRight());
        var booking = result.get();
        assertEquals(booking.getPrice(), Money.of(25,"PLN"));
        assertEquals(booking.getDiscounts().size(), 1);

    }

}
