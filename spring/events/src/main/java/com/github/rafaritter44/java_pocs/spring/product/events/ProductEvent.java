package com.github.rafaritter44.java_pocs.spring.product.events;

import com.github.rafaritter44.java_pocs.spring.product.Product;
import java.time.Instant;

public class ProductEvent {

  public static enum Type {
    PRODUCT_REGISTERED,
    PRODUCT_UNIT_ADDED,
    PRODUCT_REMOVED;
  }

  private final Long id;
  private final Type type;
  private final Product source;
  private final Instant occurredOn;

  public static ProductEvent of(Type type, Product source) {
    return new ProductEvent(null, type, source, Instant.now());
  }

  public ProductEvent(Long id, Type type, Product source, Instant occurredOn) {
    this.id = id;
    this.type = type;
    this.source = source;
    this.occurredOn = occurredOn;
  }

  public Long getId() {
    return id;
  }

  public Type getType() {
    return type;
  }

  public Product getSource() {
    return source;
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }
}
