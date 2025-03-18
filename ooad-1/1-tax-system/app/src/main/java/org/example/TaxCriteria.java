package org.example;

import java.util.Objects;

public class TaxCriteria {
    private final State state;
    private final int year;

    public TaxCriteria(State state, int year) {
        this.state = state;
        this.year = year;
    }

    public State getState() {
        return state;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaxCriteria that = (TaxCriteria) o;
        return year == that.year && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, year);
    }
}
