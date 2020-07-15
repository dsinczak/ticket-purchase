# Ticket purchase assigment
Ticket purchase functionality that is a result of job interview assigment

# Requirements
Please implement the functionality that adds a discount to flight ticket

## Basic concepts:
* Tenant - the system enables use by many clients at once. Such a customer is called a tenant. Each tenant belongs to one of the groups A or B.
Warning! There is a difference in functionality between the A and B type tenant.
* Flight - contains the ID contains the route from, to, hour and days of the week of departure.
* Flight price - the price of a flight, which can be different for different dates and times

## Feature discount calculation
The system is to enable the purchase of a flight, which for simplicity consists of that
the buyer gives the flight id and provides the tenant (in other words
use case is buyFlight(FlightId flightId, TenantId tenantId).
As a result of the flight purchase, the Booking object appears, which contains
the flight price and depending on the tenant may include the applied discount log (see below)
You can add a discount of 5 euros to the price of the flight. Discounts
operate by criteria and accumulate, but the price of the flight cannot be
less than 20 euros. Product manager would like to be able to easy
adding new discount criteria because they expect a lot of them in the future.

At first, however, these are the following criteria:
* departure date falls on the buyer's birthday
* this is a flight to Africa, departing on Thursday

Example:
The entry price is 30 euros. The buyer has a birthday on the day of departure and flies in
Thursday to Africa. Both criteria were used, so the final price is 20 euros

The flight price is 21 euros and the buyer has a birthday today. Cannot be used
no criterion because the price would be below 20 euros.

### USE CASE specific to group A tenant
* The system should record what discount criteria have been applied to
every purchase

### USE CASE specific for group B tenant
* The system is NOT allowed to save the criteria used for the purchase
