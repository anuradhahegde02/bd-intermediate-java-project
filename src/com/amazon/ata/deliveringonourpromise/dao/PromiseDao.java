package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.ReadOnlyClient;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.orderfulfillmentservice.OrderFulfillmentServiceClient;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;
import com.amazon.ata.ordermanipulationauthority.OrderShipment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Promises.
 */
public class PromiseDao implements ReadOnlyDao<String, List<Promise>> {
    private DeliveryPromiseServiceClient dpsClient;
    private OrderManipulationAuthorityClient omaClient;
    private OrderFulfillmentServiceClient ofsClient;


    /**
     * PromiseDao constructor, accepting service clients for DPS and OMA.
     *
     * @param dpsClient DeliveryPromiseServiceClient for DAO to access DPS
     * @param omaClient OrderManipulationAuthorityClient for DAO to access OMA
     * @param ofsClient OrderFulfillmentAuthorityClient for DAO to access OFS
     */
    public PromiseDao(DeliveryPromiseServiceClient dpsClient, OrderManipulationAuthorityClient omaClient, OrderFulfillmentServiceClient ofsClient) {
        this.dpsClient = dpsClient;
        this.omaClient = omaClient;
        this.ofsClient = ofsClient;
    }

    /**
     * PromiseDao constructor, accepting list of  service clients for DPS ,OMA and OFS
     *
     * @param omaClient OrderManipulationAuthorityClient for DAO to access OMA
     * @param daos      list of  service clients orderly
     *                  daos[0]= DPS
     *                  daos[1]=OFS
     */
    public PromiseDao(OrderManipulationAuthorityClient omaClient, List<ReadOnlyClient> daos) {
        this.omaClient = omaClient;
        this.dpsClient = (DeliveryPromiseServiceClient) daos.get(0);
        this.ofsClient = (OrderFulfillmentServiceClient) daos.get(1);
    }

    /**
     * Returns a list of all Promises associated with the given order item ID.
     *
     * @param customerOrderItemId the order item ID to fetch promise for
     * @return a List of promises for the given order item ID
     */
    @Override
    public List<Promise> get(String customerOrderItemId) {
        // Fetch the delivery date, so we can add to any promises that we find
        ZonedDateTime itemDeliveryDate = getDeliveryDateForOrderItem(customerOrderItemId);
        // fetch Promise from Delivery Promise Service. If exists, add to list of Promises to return.
        // Set delivery date
        List<Promise> promises = new ArrayList<>();
        Promise dpsPromise = dpsClient.getDeliveryPromiseByOrderItemId(customerOrderItemId);
        if (dpsPromise != null) {
            dpsPromise.setDeliveryDate(itemDeliveryDate);
            promises.add(dpsPromise);
            promises.addAll(getFulfillmentDetails(customerOrderItemId));
        }


        return promises;
    }


    /**
     * Returns Promises from OFS
     * @param orderId ID associated with order
     * @return promises associated with the order from OFS
     */
    public List<Promise> getFulfillmentDetails(String orderId) {

        List<Promise> dfsPromises = new ArrayList<>();

        // fetch Promise from Delivery Promise Service. If exists, add to list of Promises to return.
        // Set delivery date
        Promise dpsPromise = ofsClient.getDeliveryPromiseByOrderItemId(orderId);
        if (dpsPromise != null) {
            dfsPromises.add(dpsPromise);
        }
        return dfsPromises;
    }


    /*
     * Fetches the delivery date of the shipment containing the order item specified by the given order item ID,
     * if there is one.
     *
     * If the order item ID doesn't correspond to a valid order item, or if the shipment hasn't been delivered
     * yet, return null.
     */
    private ZonedDateTime getDeliveryDateForOrderItem(String customerOrderItemId) {
        OrderResultItem orderResultItem = omaClient.getCustomerOrderItemByOrderItemId(customerOrderItemId);

        if (null == orderResultItem) {
            return null;
        }

        OrderResult orderResult = omaClient.getCustomerOrderByOrderId(orderResultItem.getOrderId());

        for (OrderShipment shipment : orderResult.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                if (shipmentItem.getCustomerOrderItemId().equals(customerOrderItemId)) {
                    return shipment.getDeliveryDate();
                }
            }
        }

        // didn't find a delivery date!
        return null;
    }
}
