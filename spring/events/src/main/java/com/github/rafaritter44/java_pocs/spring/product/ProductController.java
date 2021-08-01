package com.github.rafaritter44.java_pocs.spring.product;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping("{description}")
  public Product register(@PathVariable("description") String productDescription) {
    return service.register(productDescription);
  }

  @PostMapping("{id}/add")
  public boolean addUnitToStock(@PathVariable("id") Long productId) {
    return service.addUnitToStock(productId);
  }

  @GetMapping
  public Iterable<Product> listAll() {
    return service.listAll();
  }

  @DeleteMapping("{id}")
  public boolean removeFromStock(@PathVariable("id") Long productId) {
    return service.removeFromStock(productId);
  }
}
