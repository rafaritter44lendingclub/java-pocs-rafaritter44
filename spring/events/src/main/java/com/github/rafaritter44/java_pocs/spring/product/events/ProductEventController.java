package com.github.rafaritter44.java_pocs.spring.product.events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("events")
public class ProductEventController {

  private final ProductEventRepository repository;

  public ProductEventController(ProductEventRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Iterable<ProductEvent> listAll() {
    return repository.findAll();
  }
}
