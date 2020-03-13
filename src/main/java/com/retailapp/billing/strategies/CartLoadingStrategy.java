package com.retailapp.billing.strategies;

import com.retailapp.billing.beans.Product;
import com.retailapp.billing.exceptions.InventoryShortageException;

import java.util.Set;

public interface CartLoadingStrategy {
    Set<Product> loadNEachFromInventory(Set<Product> inventory, int loadQuantity) throws InventoryShortageException;
}
