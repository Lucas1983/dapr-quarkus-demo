package com.dapr.customer.business.service;

import com.dapr.customer.business.repository.CustomerRepository;
import com.dapr.customer.model.entity.Customer;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class CustomerService {

  @Inject CustomerRepository repository;

  @Inject SyncDaprClient dapr;

  public List<Customer> getCustomer() {
    return repository.getCustomers();
  }

  public Customer getCustomerById(String id) {
    return repository.getCustomerById(id);
  }

  public void createCustomer(Customer Customer) {
    repository.saveCustomer(Customer);
  }

  public void deleteCustomerById(String id) {
    repository.deleteCustomer(id);
  }

  public void updateCustomer(Customer Customer) {
    repository.saveCustomer(Customer);
  }
}
