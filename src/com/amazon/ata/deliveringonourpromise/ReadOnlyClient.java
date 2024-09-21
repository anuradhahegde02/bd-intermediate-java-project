package com.amazon.ata.deliveringonourpromise;

import com.amazon.ata.deliveringonourpromise.types.Promise;

public interface ReadOnlyClient {
    Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId);
}
