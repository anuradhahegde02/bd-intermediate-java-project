package com.amazon.ata.deliveringonourpromise.comparators;

import com.amazon.ata.deliveringonourpromise.types.Promise;

import java.util.Comparator;

public class PromiseAsinComparator implements Comparator<Promise> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param promise1 the first object to be compared.
     * @param promise2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(Promise promise1, Promise promise2) {
        if (promise1.equals(promise2)) {
            return 0;
        } else {
            if (promise1.getAsin() == promise2.getAsin()) {
                return 0;
            } else {
                return promise1.getAsin().compareTo(promise2.getAsin());
            }

        }

    }
}
