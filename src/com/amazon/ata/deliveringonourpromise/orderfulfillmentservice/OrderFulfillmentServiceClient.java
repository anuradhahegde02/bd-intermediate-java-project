package com.amazon.ata.deliveringonourpromise.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.ReadOnlyClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;


/**
 * Client for accessing the DeliveryFulfillmentService to retrieve Promises.
 */
public class OrderFulfillmentServiceClient implements ReadOnlyClient {
    private OrderFulfillmentService ofService;

    /**
     * * Create new client that calls DFS with the given service object.
     *
     * @param ofService The DeliveryFulfillmentService that this client will call.
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService ofService) {
        this.ofService = ofService;
    }

    /**
     * Fetches the Promise for the given order item ID.
     *
     * @param customerOrderItemId String representing the order item ID to fetch the order for.
     * @return the Promise for the given order item ID.
     */
    public Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId) {
        OrderPromise orderPromise = ofService.getOrderPromise(customerOrderItemId);

        if (null == orderPromise) {
            return null;
        }

        return Promise.builder()
                .withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withIsActive(orderPromise.isActive())
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .withAsin(orderPromise.getAsin())
                .build();
    }
}
