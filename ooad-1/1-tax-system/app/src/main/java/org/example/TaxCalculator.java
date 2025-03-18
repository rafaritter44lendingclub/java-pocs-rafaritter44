package org.example;

import java.math.BigDecimal;
import java.util.Map;

public class TaxCalculator {
    private final Map<TaxCriteria, BigDecimal> taxRates;

    public TaxCalculator(Map<TaxCriteria, BigDecimal> taxRates) {
        this.taxRates = taxRates;
    }

    public BigDecimal calculateTax(Product product, State state, int year) {
        BigDecimal taxRate = taxRates.getOrDefault(new TaxCriteria(state, year), BigDecimal.ZERO);
        return product.getPrice().multiply(taxRate);
    }
}
