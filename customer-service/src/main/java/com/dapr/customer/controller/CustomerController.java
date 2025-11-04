package com.dapr.customer.controller;

import com.dapr.customer.business.service.CustomerService;
import com.dapr.customer.model.entity.Customer;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;

@Path("/customer")
public class CustomerController {

  @Inject CustomerService customerService;

  @GET
  @Path("/")
  public List<Customer> getCustomers() {
    return customerService.getCustomer();
  }

  @GET
  @Path("/{id}")
  public Customer getCustomerById(@PathParam("id") String id) {
    return customerService.getCustomerById(id);
  }

  @POST
  @Path("/")
  public void createCustomer(Customer product) {
    customerService.createCustomer(product);
  }

  @PUT
  @Path("/")
  public void updateCustomer(Customer product) {
    customerService.updateCustomer(product);
  }

  @DELETE
  @Path("/{id}")
  public void deleteCustomerById(@PathParam("id") String id) {
    customerService.deleteCustomerById(id);
  }
}
