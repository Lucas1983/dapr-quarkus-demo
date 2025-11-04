package com.dapr.customer.business.repository;

import com.dapr.common.DaprConfig;
import com.dapr.customer.model.entity.Customer;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class CustomerRepository {
  @Inject SyncDaprClient dapr;

  public List<Customer> getCustomers() {

    return dapr.getBulkState(DaprConfig.STATE_STORE_NAME, List.of(), Customer.class).stream()
        .map(io.dapr.client.domain.State::getValue)
        .toList();
  }

  public Customer getCustomerById(String id) {

    return dapr.getState(DaprConfig.STATE_STORE_NAME, id, Customer.class).getValue();
  }

  public void saveCustomer(Customer Customer) {
    dapr.saveState(DaprConfig.STATE_STORE_NAME, String.valueOf(Customer.getId()), Customer);
  }

  public void deleteCustomer(String id) {
    dapr.deleteState(DaprConfig.STATE_STORE_NAME, id);
  }
}
