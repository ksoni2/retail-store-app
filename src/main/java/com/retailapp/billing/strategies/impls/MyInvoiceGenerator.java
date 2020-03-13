package com.retailapp.billing.strategies.impls;

import com.retailapp.billing.beans.Invoice;
import com.retailapp.billing.beans.Product;
import com.retailapp.billing.beans.Shopper;
import com.retailapp.billing.beans.UserDetails;
import com.retailapp.billing.constants.InvoiceDiscounts;
import com.retailapp.billing.constants.ProductTypes;
import com.retailapp.billing.constants.UserTypes;
import com.retailapp.billing.strategies.InvoicingStrategy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class MyInvoiceGenerator implements InvoicingStrategy {
    @Override
    public void generate(Shopper shopper) {
        UserDetails userDetails = shopper.getUserDetails();
        double userDiscountPercentage = getUserDiscount(userDetails.getUserType(), userDetails.getUserSince());
        double totalDiscountApplied = 0.0d;
        double totalBill = 0.0d;

        for (Product p : shopper.getShoppingCart().getProductsInCart().getProducts().values()) {
            totalBill += getDiscountedProductPrice(p);
            totalDiscountApplied += getDiscountOnProductPrice(p);
        }

        totalDiscountApplied += totalBill * userDiscountPercentage;
        totalBill *= (1 - userDiscountPercentage);

        shopper.setInvoice(new Invoice(UUID.randomUUID(), LocalDateTime.now(), totalBill, totalDiscountApplied));
    }

    private double getDiscountedProductPrice(Product product) {
        return product.getQuantity() * product.getUnitPrice() - getDiscountOnProductPrice(product);
    }

    private double getDiscountOnProductPrice(Product product) {
        return product.getQuantity() * product.getUnitPrice() *
                ((product.getType().equals(ProductTypes.GROCERY)) ? 0 : InvoiceDiscounts.NOT_GROCERY.getDiscount());
    }

    private double getUserDiscount(UserTypes userType, LocalDateTime userSince) {
        double userDiscountPercentage = 0.0d;
        switch (userType) {
            case AFFILIATE:
                userDiscountPercentage = InvoiceDiscounts.AFFILIATE.getDiscount();
                break;
            case CUSTOMER:
                if (ChronoUnit.YEARS.between(userSince, LocalDateTime.now()) > 2) {
                    userDiscountPercentage = InvoiceDiscounts.CUSTOMER.getDiscount();
                }
                break;
            case EMPLOYEE:
                userDiscountPercentage = InvoiceDiscounts.EMPLOYEE.getDiscount();
                break;
        }
        return userDiscountPercentage;
    }
}
