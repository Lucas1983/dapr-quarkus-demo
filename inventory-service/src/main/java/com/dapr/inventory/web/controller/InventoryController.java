package com.dapr.inventory.web.controller;

import com.dapr.inventory.business.service.InventoryService;
import com.dapr.inventory.model.Product;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;

@Path("/inventory")
public class InventoryController {

  @Inject
  InventoryService inventoryService;

  @GET
  @Path("/")
  public List<Product> getProducts() {
    return inventoryService.getProducts();
  }

  @GET
  @Path("/{id}")
  public Product getProductById(@PathParam("id") String id) {
    return inventoryService.getProductById(id);
  }

  @POST
  @Path("/")
  public Product createProduct(Product product) {
    return inventoryService.createProduct(product);
  }

  @DELETE
  @Path("/{id}")
  public void deleteProductById(@PathParam("id") String id) {
    inventoryService.deleteProductById(id);
  }

  @PUT
  @Path("/{id}")
  public void updateProduct(@PathParam("id") String id, Product product) {
    inventoryService.updateProduct(id, product);
  }
}
