package org.dsinczak.ticketpurchase.domain;

import io.vavr.control.Option;

public interface FlightRepository {

    Option<Flight> find(Flight.FlightId flightId);

}
