package com.amazon.ata.deliveringonourpromise;

import com.amazon.ata.deliveringonourpromise.types.Promise;

/**
 * Client interface to abstract calls.
 *
 */
public interface ReadOnlyClient {
    /**
     * Get object method to be implemented.
     *
     * @param customerOrderItemId Order Id
     * @return Promises from service client
     */
    Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId);
}
