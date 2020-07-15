package org.dsinczak.ticketpurchase.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class Tenant {

    TenantId tenantId;
    Group type;

    public enum Group { A, B }

    @Value
    public static class TenantId {
        UUID uuid;

        public static TenantId random(){
            return new TenantId(UUID.randomUUID());
        }
    }
}
