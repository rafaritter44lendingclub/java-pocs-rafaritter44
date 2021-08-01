package com.github.rafaritter44.java_pocs.spring.product.events;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductEventListener {

  private final ProductEventRepository repository;

  public ProductEventListener(ProductEventRepository repository) {
    this.repository = repository;
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void handle(ProductEvent event) {
    repository.save(event);
  }
}
