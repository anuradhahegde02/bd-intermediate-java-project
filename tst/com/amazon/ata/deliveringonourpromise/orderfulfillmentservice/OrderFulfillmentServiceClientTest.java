package com.amazon.ata.deliveringonourpromise.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderFulfillmentServiceClientTest {
    private OrderFulfillmentServiceClient client;
    private String orderItemId;

    @BeforeEach
    private void setup() {
        // not mocking: use an actual client calling actual service
        client = App.getOrderFulfillmentServiceClient();
        String orderId = "111-7497023-2960776";
        orderItemId = OrderDatastore.getDatastore()
                .getOrderData(orderId)
                .getCustomerOrderItemList()
                .get(0)
                .getCustomerOrderItemId();
    }

}
