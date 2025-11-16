package com.dapr.customer.config;

import com.dapr.common.DaprConfig;
import com.dapr.customer.model.entity.Customer;
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
    log.info("🚀 Generating sample customer data...");

    IntStream.range(1, 10)
        .forEach(
            i -> {
              var firstName = faker.name().firstName();
              var lastName = faker.name().lastName();
              var email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@dapr.com";
              var customer =
                  Customer.builder()
                      .id(java.util.UUID.randomUUID())
                      .firstName(firstName)
                      .lastName(lastName)
                      .email(email)
                      .address(faker.address().fullAddress())
                      .build();

              daprClient.saveState(
                  DaprConfig.STATE_STORE_NAME, String.valueOf(customer.getId()), customer);
            });
  }
}
