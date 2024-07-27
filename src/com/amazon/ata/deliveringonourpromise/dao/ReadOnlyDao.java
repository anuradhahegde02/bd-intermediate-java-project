package com.amazon.ata.deliveringonourpromise.dao;

/**
 * DAO interface to abstract calls.
 * @param <I> I is a placeholder for the type O
 * @param <O> O represents order
 */
public interface ReadOnlyDao<I, O> {

    /**
     * Get object method to be implemented.
     * @param orderId Order Id
     * @return Object abstracted object
     */
    O get(I orderId);
}
