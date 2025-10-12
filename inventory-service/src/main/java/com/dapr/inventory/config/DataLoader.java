package com.dapr.inventory.config;

import com.dapr.common.DaprConfig;
import com.dapr.inventory.model.entity.Product;
import io.quarkiverse.dapr.core.SyncDaprClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Locale;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

@Slf4j
@ApplicationScoped
public class DataLoader {

  private final Faker faker = new Faker(Locale.ENGLISH);
  @Inject SyncDaprClient daprClient;

  @Transactional
  void onStart(@Observes StartupEvent event) {
    log.info("🚀 Generating sample product data...");

    IntStream.range(1, 10)
        .forEach(
            i -> {
              var productName = faker.commerce().productName();
              var product =
                  Product.builder()
                      .id(java.util.UUID.randomUUID())
                      .name(productName)
                      .description(productName)
                      .quantity(100)
                      .build();

              daprClient.saveState(
                  DaprConfig.STATE_STORE_NAME, String.valueOf(product.getId()), product);
            });
  }
}
