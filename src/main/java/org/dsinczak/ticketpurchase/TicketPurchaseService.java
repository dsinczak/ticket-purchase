package org.dsinczak.ticketpurchase;

import io.vavr.control.Either;
import org.dsinczak.ticketpurchase.domain.*;
import org.dsinczak.ticketpurchase.domain.Customer.CustomerId;
import org.dsinczak.ticketpurchase.domain.Flight.FlightId;
import org.dsinczak.ticketpurchase.domain.Tenant.TenantId;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

import static org.dsinczak.ticketpurchase.domain.DomainError.*;
import static org.dsinczak.ticketpurchase.functional.ForComprehension.For;

@Service
public class TicketPurchaseService {

    private final TenantRepository tenantRepository;
    private final FlightRepository flightRepository;
    private final CustomerRepository customerRepository;
    private final BookingFactory bookingFactory;
    private final BookingRepository bookingRepository;

    public TicketPurchaseService(TenantRepository tenantRepository,
                                 FlightRepository flightRepository,
                                 CustomerRepository customerRepository,
                                 BookingFactory bookingFactory,
                                 BookingRepository bookingRepository) {
        this.tenantRepository = tenantRepository;
        this.flightRepository = flightRepository;
        this.customerRepository = customerRepository;
        this.bookingFactory = bookingFactory;
        this.bookingRepository = bookingRepository;
    }

    public Either<DomainError, Booking> buyTicket(CustomerId customerId, TenantId tenantId, FlightId flightId, DayOfWeek dayOfWeek) {
        return For(
                customerRepository.find(customerId).toEither(CUSTOMER_NOT_FOUND),
                tenantRepository.find(tenantId).toEither(TENANT_NOT_FOUND),
                flightRepository.find(flightId).toEither(FLIGHT_NOT_FOUND),
                (customer, tenant, flight) -> bookingFactory.create(customer, tenant, flight, dayOfWeek)
        ).map(bookingRepository::save);
    }

}
