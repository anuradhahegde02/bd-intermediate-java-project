package com.amazon.ata.deliveringonourpromise.activity;

import com.amazon.ata.deliveringonourpromise.comparators.PromiseAsinComparator;
import com.amazon.ata.deliveringonourpromise.dao.ReadOnlyDao;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.deliveringonourpromise.types.PromiseHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity class, handling the GetPromiseHistoryByOrderId API.
 */
public class GetPromiseHistoryByOrderIdActivity {
    private ReadOnlyDao<String, Order> orderDao;
    private ReadOnlyDao<String, List<Promise>> promiseDao;

    /**
     * Instantiates an activity for handling the API, accepting the relevant DAOs to
     * perform its work.
     *
     * @param orderDao   data access object fo retrieving Orders by order ID
     * @param promiseDao data access object for retrieving Promises by order item ID
     */
    public GetPromiseHistoryByOrderIdActivity(ReadOnlyDao<String, Order> orderDao,
                                              ReadOnlyDao<String, List<Promise>> promiseDao) {
        this.orderDao = orderDao;
        this.promiseDao = promiseDao;
    }

    /**
     * Returns the PromiseHistory for the given order ID, if the order exists. If the order does
     * not exist a PromiseHistory with a null order and no promises will be returned.
     *
     * @param orderId The order ID to fetch PromiseHistory for
     * @return PromiseHistory containing the order and promise history for that order
     */
    public PromiseHistory getPromiseHistoryByOrderId(String orderId) {
        if (null == orderId) {
            throw new IllegalArgumentException("order ID cannot be null");
        }
        //FixMe: when order is null handle it before throwing exception
        Order order = orderDao.get(orderId);
        PromiseHistory history = new PromiseHistory(order);
        if (null != order) {
            List<OrderItem> customerOrderItems = order.getCustomerOrderItemList();
            List<Promise> listofPromises = new ArrayList<>();
            if (customerOrderItems != null && !customerOrderItems.isEmpty()) {
                for (OrderItem item : customerOrderItems) {
                    if (item != null) {
                        List<Promise> promises = promiseDao.get(item.getCustomerOrderItemId());

                        for (Promise promise : promises) {
                            promise.setConfidence(item.isConfidenceTracked(), item.getConfidence());
                            listofPromises.add(promise);
                        }

                    }

                }

            }

            Collections.sort(listofPromises, new PromiseAsinComparator());
            history.addAllPromise(listofPromises);


        }
        return history;
    }
}
