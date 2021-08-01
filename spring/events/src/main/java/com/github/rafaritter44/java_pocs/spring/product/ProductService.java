package com.github.rafaritter44.java_pocs.spring.product;

import com.github.rafaritter44.java_pocs.spring.product.events.ProductEvent;
import com.github.rafaritter44.java_pocs.spring.product.events.ProductEvent.Type;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductRepository repository;
  private final ApplicationEventPublisher eventPublisher;

  public ProductService(ProductRepository repository, ApplicationEventPublisher eventPublisher) {
    this.repository = repository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public Product register(String productDescription) {
    Product product = repository.save(Product.of(productDescription));
    ProductEvent productRegisteredEvent = ProductEvent.of(Type.PRODUCT_REGISTERED, product);
    eventPublisher.publishEvent(productRegisteredEvent);
    return product;
  }

  @Transactional
  public boolean addUnitToStock(Long productId) {
    Optional<Product> product = repository.findById(productId);
    product.ifPresent(
        p -> {
          p.addUnitToStock();
          repository.save(p);
        });
    return product.isPresent();
  }

  public Iterable<Product> listAll() {
    return repository.findAll();
  }

  @Transactional
  public boolean removeFromStock(Long productId) {
    Optional<Product> product = repository.findById(productId);
    product.ifPresent(
        p -> {
          repository.deleteById(productId);
          ProductEvent productRemovedEvent = ProductEvent.of(Type.PRODUCT_REMOVED, p);
          eventPublisher.publishEvent(productRemovedEvent);
        });
    return product.isPresent();
  }
}
