package com.retailapp.billing.strategies;

import com.retailapp.billing.beans.Products;

public interface StoreDBStrategy {
    ThreadLocal<Products> getProductInventory();
}
