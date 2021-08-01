package com.github.rafaritter44.java_pocs.spring.product.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductEventRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ProductEventRowMapper rowMapper;
  private final ObjectMapper objectMapper;

  public ProductEventRepository(
      JdbcTemplate jdbcTemplate, ProductEventRowMapper rowMapper, ObjectMapper objectMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.rowMapper = rowMapper;
    this.objectMapper = objectMapper;
  }

  public boolean save(ProductEvent event) {
    String sourceJson;
    try {
      sourceJson = objectMapper.writeValueAsString(event.getSource());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    String eventType = event.getType().toString();
    Timestamp eventOccurredOn = Timestamp.from(event.getOccurredOn());
    String sql =
        "INSERT INTO product_event (type, source, occurred_on) VALUES (?, to_jsonb(?::jsonb), ?)";
    int rowsAffected = jdbcTemplate.update(sql, eventType, sourceJson, eventOccurredOn);
    return rowsAffected == 1;
  }

  public Iterable<ProductEvent> findAll() {
    String sql = "SELECT id, type, source, occurred_on FROM product_event";
    return jdbcTemplate.query(sql, rowMapper);
  }
}
