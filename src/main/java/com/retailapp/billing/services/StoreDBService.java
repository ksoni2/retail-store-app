package com.retailapp.billing.services;

import com.retailapp.billing.beans.Product;

import java.util.Set;
import java.util.UUID;

public interface StoreDBService {
    Set<Product> getInventory();

    boolean isTransactionAllowed(UUID pid, int quantity);

    void updateInventory(Set<Product> cartProducts);
}
