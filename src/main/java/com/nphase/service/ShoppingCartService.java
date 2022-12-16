package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.entity.Type;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return totalPriceByProducts(shoppingCart.getProducts());
    }

    public BigDecimal discountedPriceByProductQuantity(ShoppingCart shoppingCart, int discountPercent, int quantityForDiscount) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> {
                            var defaultPrice = calculateTotalPrice(product);

                            return product.getQuantity() >= quantityForDiscount
                                    ? applyDiscount(defaultPrice, discountPercent)
                                    : defaultPrice;
                        }
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal discountedPriceByProductType(ShoppingCart shoppingCart, int discountPercent, int quantityForDiscount) {
        if (quantityForDiscount <= 0)
            throw new IllegalArgumentException("To apply discount you should enter correct number of items");
        final Map<Type, List<Product>> productsByType = shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getType, Collectors.toList()));

        return productsByType
                .values()
                .stream()
                .map(products -> {
                    int productCount = products.stream().mapToInt(Product::getQuantity).sum();

                    BigDecimal defaultPrice = totalPriceByProducts(products);

                    return productCount >= quantityForDiscount
                            ? applyDiscount(defaultPrice, discountPercent)
                            : defaultPrice;

                })
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    private BigDecimal applyDiscount(BigDecimal defaultPrice, int percent) {
        if (percent <= 0) return defaultPrice;
        var discount = defaultPrice.multiply(BigDecimal.valueOf(percent / 100.0));
        return defaultPrice.subtract(discount);
    }

    private BigDecimal totalPriceByProducts(List<Product> products) {
        return products.stream()
                .map(this::calculateTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateTotalPrice(Product product) {
        return product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
    }

}
