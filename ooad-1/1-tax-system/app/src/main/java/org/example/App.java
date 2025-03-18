package org.example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class App {

    private static final TaxCalculator taxCalculator;

    static {
        Map<TaxCriteria, BigDecimal> taxRates = new HashMap<>();
        taxRates.put(new TaxCriteria(State.SC, 2019), new BigDecimal("0.05"));
        taxRates.put(new TaxCriteria(State.BA, 2023), new BigDecimal("0.25"));

        taxCalculator = new TaxCalculator(taxRates);
    }

    public static void main(String[] args) {
        Product laptop = new Product("Laptop", new BigDecimal("5000"));
        Product phone = new Product("Phone", new BigDecimal("1000"));
        Product book = new Product("Book", new BigDecimal("50"));

        printTaxRate(laptop, State.SC, 2019); // Product: Laptop - State: SC - Year: 2019 - Tax rate: 250.00
        printTaxRate(laptop, State.BA, 2023); // Product: Laptop - State: BA - Year: 2023 - Tax rate: 1250.00
        printTaxRate(phone, State.SC, 2019); // Product: Phone - State: SC - Year: 2019 - Tax rate: 50.00
        printTaxRate(phone, State.BA, 2023); // Product: Phone - State: BA - Year: 2023 - Tax rate: 250.00
        printTaxRate(book, State.SC, 2019); // Product: Book - State: SC - Year: 2019 - Tax rate: 2.50
        printTaxRate(book, State.BA, 2023); // Product: Book - State: BA - Year: 2023 - Tax rate: 12.50
    }

    private static void printTaxRate(Product product, State state, int year) {
        BigDecimal taxRate = taxCalculator.calculateTax(product, state, year);
        System.out.print("Product: " + product.getName());
        System.out.print(" - State: " + state);
        System.out.print(" - Year: " + year);
        System.out.println(" - Tax rate: " + taxRate);
    }
}
