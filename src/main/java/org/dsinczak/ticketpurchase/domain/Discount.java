package org.dsinczak.ticketpurchase.domain;

import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class Discount {
    Money value;
    String name;
}
