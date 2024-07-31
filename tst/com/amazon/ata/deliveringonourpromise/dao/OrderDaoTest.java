package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.asserts.Assertion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {

    private OrderDao dao;
    private OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    String validOrderID;


    @BeforeEach
    private void setup() {
        dao = new OrderDao(omaClient);
    }

    @Test
    public void get_forKnownOrderId_returnsOrder() {
        // Happy case, verifying that the OrderDao can return an order.
        //Given
        validOrderID = "900-3746401-0000002";
        //When
        Order order = dao.get(validOrderID);
        //Then
        assertNotNull(order, "Order is not expected to be null for valid orderId= " + validOrderID + " with valid order details");
        assertEquals(validOrderID, order.getOrderId());
    }

    @Test
    public void get_orderIdWithNoDetails_returnsNull() {
        //Given
        String orderIdWithNoDetails = "111-749023-7630574";
        //When
        Order order = dao.get(orderIdWithNoDetails);
        //Then
        assertNull(order, "Order Details must be null for the orderId= " + orderIdWithNoDetails);
    }

    @Test
    public void get_orderIdNull_returnsNull() {
        //Given
        String orderId = null;
        //When
        Order order = dao.get(orderId);
        //Then
        assertNull(order, "Order Details must be null for the orderId = " + orderId);
    }

    @Test
    public void get_nonexistentOrderId_returnsNull() {
        // GIVEN - invalid/nonexistent order  ID
        String orderId = "342";
        // WHEN
        Order order = dao.get(orderId);

        // THEN
        assertNull(order, "Order Details must be null for the invalid/nonexistent orderId= " + orderId);
    }
}
