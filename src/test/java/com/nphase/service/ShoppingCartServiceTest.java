package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, Type.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1,Type.DRINKS)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void discountedPriceByProductQuantityTest() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(2.0), 3,Type.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.0), 6,Type.DRINKS),
                new Product("Beer", BigDecimal.valueOf(2.5), 2,Type.DRINKS)
        ));

        BigDecimal result = service.discountedPriceByProductQuantity(cart, 10,3);

        Assertions.assertEquals(BigDecimal.valueOf(2660, 2), result);
    }

    @Test
    public void discountedPriceByProductType() {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(2.0), 3,Type.DRINKS),
                new Product("Coffee", BigDecimal.valueOf(3.0), 1,Type.DRINKS),
                new Product("Cheese", BigDecimal.valueOf(2.5), 2, Type.FOOD)
        ));

        BigDecimal result = service.discountedPriceByProductType(cart, 10, 3);

        Assertions.assertEquals(BigDecimal.valueOf(1310, 2), result);
    }

}
