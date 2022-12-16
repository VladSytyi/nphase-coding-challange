package com.nphase.entity;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final BigDecimal pricePerUnit;
    private final int quantity;
    private final Type type;

    public Product(String name, BigDecimal pricePerUnit, int quantity, Type type) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Type getType() {
        return type;
    }
}
