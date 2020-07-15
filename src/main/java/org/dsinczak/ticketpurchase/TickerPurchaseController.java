package org.dsinczak.ticketpurchase;

import lombok.Value;
import org.dsinczak.ticketpurchase.domain.Customer.CustomerId;
import org.dsinczak.ticketpurchase.domain.Flight.FlightId;
import org.dsinczak.ticketpurchase.domain.Tenant.TenantId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import java.time.DayOfWeek;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Value
@RestController
public class TickerPurchaseController {

    private TicketPurchaseService ticketPurchaseService;

    /**
     * Here is a strong assumption that this is just a (micro)service and user is already
     * authenticated some way. That is why tenantId and customerId are expected as headers.
     * Also I assumed the same customer can authenticate in context of different tenants,
     * this might also not be the rule and in real life depends on the way business models
     * customers in multi-tenant environment.
     * So... there are other ways but i just choose the one that does not complicate the API.
     */
    @PostMapping("/flight/{flightId}/{day}/booking")
    public ResponseEntity<Object> buyTicket(@PathParam("flightId") FlightId flightId,
                                            @PathParam("day") DayOfWeek dayOfWeek,
                                            @RequestHeader("tenantId") TenantId tenantId,
                                            @RequestHeader("customerId") CustomerId customerId) {
        return Match(ticketPurchaseService.buyTicket(customerId, tenantId, flightId, dayOfWeek)).of(
                Case($Left($()), error -> new ResponseEntity<>("Bad request: " + error.message(), BAD_REQUEST)),
                Case($Right($()), booking -> new ResponseEntity<>(booking, OK))
        );
    }
}
