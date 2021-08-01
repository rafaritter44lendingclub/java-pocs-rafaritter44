package com.github.rafaritter44.java_pocs.spring.product.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rafaritter44.java_pocs.spring.product.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductEventRowMapper implements RowMapper<ProductEvent> {

  private final ObjectMapper objectMapper;

  public ProductEventRowMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ProductEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
    Product eventSource;
    try {
      eventSource = objectMapper.readValue(rs.getString("source"), Product.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Long eventId = rs.getLong("id");
    ProductEvent.Type eventType = ProductEvent.Type.valueOf(rs.getString("type"));
    Instant eventOccurredOn = rs.getTimestamp("occurred_on").toInstant();
    return new ProductEvent(eventId, eventType, eventSource, eventOccurredOn);
  }
}
