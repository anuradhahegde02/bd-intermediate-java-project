package com.amazon.ata.deliveringonourpromise.types;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderTest {


    /**
     * Helper method which creates customer order
     *
     * @return Order with all the order details
     */
    private Order createNewOrder() {
        Order.Builder orderBuilder = new Order.Builder();
        return orderBuilder.build();
    }
}
