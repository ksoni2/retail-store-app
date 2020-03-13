package com.retailapp.billing;

import com.retailapp.billing.beans.Shopper;
import com.retailapp.billing.beans.ShoppingCart;
import com.retailapp.billing.beans.UserDetails;
import com.retailapp.billing.constants.ApplicationConstants;
import com.retailapp.billing.exceptions.InventoryShortageException;
import com.retailapp.billing.services.InvoiceService;
import com.retailapp.billing.services.StoreDBService;
import com.retailapp.billing.services.impls.MyCartService;

public class ShoppingApplication {

    private final StoreDBService myStoreDBService;
    private final MyCartService myCartService;
    private final InvoiceService myInvoiceService;

    public ShoppingApplication(StoreDBService myStoreDBService,
                               MyCartService myCartService,
                               InvoiceService myInvoiceService) {
        this.myStoreDBService = myStoreDBService;
        this.myCartService = myCartService;
        this.myInvoiceService = myInvoiceService;
    }

    public double shop(UserDetails userDetails) throws InventoryShortageException {

        ShoppingCart shoppingCart = myCartService.getNewShoppingCart();
        Shopper shopper = new Shopper(userDetails, shoppingCart);

        //Shopping Business Logic
        myCartService.loadNEachFromInventory(
                (int) ApplicationConstants.CART_QUANTITY.getApplicationConstant(),
                shoppingCart);

        myStoreDBService.updateInventory(myCartService.getAllProducts(shoppingCart));
        myInvoiceService.generate(shopper);
        myInvoiceService.print(shopper);
        return shopper.getInvoice().getAmount();
    }


}
