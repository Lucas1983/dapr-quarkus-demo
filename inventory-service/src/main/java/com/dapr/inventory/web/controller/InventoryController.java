package com.dapr.inventory.web.controller;

import com.dapr.inventory.business.service.InventoryService;
import com.dapr.inventory.model.entity.Product;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;

@Path("/inventory")
public class InventoryController {

  @Inject InventoryService inventoryService;

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
  public void createProduct(Product product) {
    inventoryService.createProduct(product);
  }

  @PUT
  @Path("/")
  public void updateProduct(Product product) {
    inventoryService.updateProduct(product);
  }

  @DELETE
  @Path("/{id}")
  public void deleteProductById(@PathParam("id") String id) {
    inventoryService.deleteProductById(id);
  }
}
