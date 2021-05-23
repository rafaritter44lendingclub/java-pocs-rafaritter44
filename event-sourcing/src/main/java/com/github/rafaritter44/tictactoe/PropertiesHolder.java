package com.github.rafaritter44.tictactoe;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {

  private static final Properties INSTANCE;

  static {
    INSTANCE = new Properties();
    var classLoader = Thread.currentThread().getContextClassLoader();
    try (var inputStream = classLoader.getResourceAsStream("kafka.properties")) {
      INSTANCE.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Error loading properties", e);
    }
  }

  private PropertiesHolder() {}

  public static Properties get() {
    return INSTANCE;
  }
}
