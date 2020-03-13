package com.retailapp.billing.strategies;

import com.retailapp.billing.beans.Shopper;

public interface InvoicingStrategy {
    void generate(Shopper shopper);
}
