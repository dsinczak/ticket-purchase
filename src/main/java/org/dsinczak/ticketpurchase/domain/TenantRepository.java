package org.dsinczak.ticketpurchase.domain;

import io.vavr.control.Option;
import org.dsinczak.ticketpurchase.domain.Tenant.TenantId;

public interface TenantRepository {

    Option<Tenant> find(TenantId tenantId);
}
