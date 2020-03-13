package com.retailapp.billing.services;

import com.retailapp.billing.beans.Shopper;

public interface InvoiceService {
    void generate(Shopper shopper);
    void print(Shopper shopper);
}
