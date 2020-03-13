package com.retailapp.billing;

import com.retailapp.billing.beans.UserDetails;
import com.retailapp.billing.constants.UserTypes;
import com.retailapp.billing.daos.impls.MyStoreDao;
import com.retailapp.billing.exceptions.InventoryShortageException;
import com.retailapp.billing.services.InvoiceService;
import com.retailapp.billing.services.StoreDBService;
import com.retailapp.billing.services.impls.MyCartService;
import com.retailapp.billing.services.impls.MyInvoiceService;
import com.retailapp.billing.services.impls.MyStoreDBService;
import com.retailapp.billing.strategies.impls.MyCartLoadingStrategy;
import com.retailapp.billing.strategies.impls.MyInvoiceGenerator;
import com.retailapp.billing.strategies.impls.Store;
import org.junit.Before;
import org.junit.Test;


import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;


public class ShoppingApplicationTest {

    private StoreDBService myStoreDBService;
    private MyCartService myCartService;
    private InvoiceService myInvoiceService;
    private DecimalFormat df;

    @Before
    public void setUp() throws Exception {
        //Services
        myStoreDBService = new MyStoreDBService(new MyStoreDao(Store.getStore()));
        myCartService = new MyCartService(myStoreDBService, new MyCartLoadingStrategy());
        myInvoiceService = new MyInvoiceService(new MyInvoiceGenerator());
        df = new DecimalFormat("#.####");
    }

    @Test
    public void UserIsAffiliate() throws InventoryShortageException {
       ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
               myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Kamal Soni", UserTypes.AFFILIATE, localDateTime, "+97155526972", "kamalsoni.tech@gmail.com");
        assertEquals("Bill Amount must me 73.5336 for this User",df.format(73.5336d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsEmployee() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Kamal Soni", UserTypes.EMPLOYEE, localDateTime, "+97155526972", "kamalsoni.tech@gmail.com");
        assertEquals("Bill Amount must me 57.1928 for this User",df.format(57.1928d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsRecentCustomer() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Kamal Soni", UserTypes.CUSTOMER, localDateTime, "+97155526972", "kamalsoni.tech@gmail.com");
        assertEquals("Bill Amount must me 81.7040 for this User",df.format(81.7040d), df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsOldCustomer() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Kamal Soni", UserTypes.CUSTOMER, localDateTime, "+97155526972", "kamalsoni.tech@gmail.com");
        assertEquals("Bill Amount must me 77.6188 for this User",df.format(77.6188d), df.format(shoppingApplication.shop(userDetails)));
    }
}
