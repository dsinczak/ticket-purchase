package org.dsinczak.ticketpurchase.domain;

/**
 * This is very trivial representation, real solution of error handling
 * is rather not enum based and takes more into consideration (like message,
 * parameters, i18n). But such mechanism was not a clue of this assignment so
 * i just choose simplest solution there is.
 */
public enum DomainError {
    FLIGHT_NOT_FOUND("Flight with given ID is not found"),
    TENANT_NOT_FOUND("Tenant with given ID is not found"),
    CUSTOMER_NOT_FOUND("Tenant with given ID is not found"),
    UNKNOWN_TENANT_TYPE("Tenant with given ID has unknown type"),
    FLIGHT_DAY_NOT_SUPPORTED("Flight with given ID does not operate on requested date");

    private String message;

    public String message() {
        return message;
    }

    DomainError(String message) {
        this.message = message;
    }
}
