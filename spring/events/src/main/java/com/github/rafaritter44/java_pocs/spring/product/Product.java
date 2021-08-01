package com.github.rafaritter44.java_pocs.spring.product;

import com.github.rafaritter44.java_pocs.spring.product.events.ProductEvent;
import com.github.rafaritter44.java_pocs.spring.product.events.ProductEvent.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;

public class Product extends AbstractAggregateRoot<Product> {

  @Id private final Long id;
  private final String description;
  private int quantity;

  public static Product of(String description) {
    return new Product(null, description, 1);
  }

  public Product(Long id, String description, int quantity) {
    this.id = id;
    this.description = description;
    this.quantity = quantity;
  }

  public void addUnitToStock() {
    quantity += 1;
    ProductEvent productUnitAddedEvent = ProductEvent.of(Type.PRODUCT_UNIT_ADDED, this);
    registerEvent(productUnitAddedEvent);
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public int getQuantity() {
    return quantity;
  }
}
