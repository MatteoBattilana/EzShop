package it.polito.ezshop.data;


import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class EZShopTest {
    EZShop ezShop = new EZShop();

    @After
    public void teardown() {
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
    }

    @Test
    public void returnTrans() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        Integer productType = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(productType, "1-1-2");
        ezShop.updateQuantity(productType, 10);

        // Sale
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        double resto = ezShop.receiveCashPayment(saleId, 100);
        assertEquals(100.0-5.0, resto, 0.1);


        //Start return transaction
        Integer returnId = ezShop.startReturnTransaction(saleId);
        ezShop.returnProduct(returnId, "1234567890128", 2);
        ezShop.endReturnTransaction(returnId, true);
        double ritorno = ezShop.returnCashPayment(returnId);
        assertEquals(2.0, ritorno, 0.1);
        assertEquals(7, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());


        //Start return transaction
        Integer returnId2 = ezShop.startReturnTransaction(saleId);
        ezShop.returnProduct(returnId2, "1234567890128", 2);
        ezShop.endReturnTransaction(returnId2, true);
        double ritorno2 = ezShop.returnCashPayment(returnId2);
        assertEquals(2.0, ritorno2, 0.1);
        assertEquals(9, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());

        //Start return transaction
        Integer returnId3 = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId3.intValue());
        assertTrue(ezShop.returnProduct(returnId3, "1234567890128", 1));
        assertTrue(ezShop.endReturnTransaction(returnId3, false));




        // Sale
        Integer saleId2 = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId2, "1234567890128", 2);
        ezShop.endSaleTransaction(saleId2);
        assertEquals(98.0,ezShop.receiveCashPayment(saleId2, 100),0.1);

        // Return 2
        Integer returnId21 = ezShop.startReturnTransaction(saleId2);
        assertNotEquals(-1, returnId21.intValue());
        assertTrue(ezShop.returnProduct(returnId21, "1234567890128", 2));
        assertTrue(ezShop.endReturnTransaction(returnId21, false));


        Integer returnId22 = ezShop.startReturnTransaction(saleId2);
        assertNotEquals(-1, returnId22.intValue());
        assertFalse(ezShop.returnProduct(returnId22, "1234567890128", 3));

        int initialQ = ezShop.getProductTypeByBarCode("1234567890128").getQuantity();

        Integer returnId23 = ezShop.startReturnTransaction(saleId2);
        assertNotEquals(-1, returnId23.intValue());
        assertTrue(ezShop.returnProduct(returnId23, "1234567890128", 2));
        assertEquals(initialQ, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertTrue(ezShop.endReturnTransaction(returnId23, false));

        assertEquals(initialQ, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());

    }

        @Test (expected = InvalidUsernameException.class)
    public void loginUsernameNull() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login(null, "password");
    }

    @Test (expected = InvalidUsernameException.class)
    public void loginUsernameEmpty() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("", "password");
    }

    @Test (expected = InvalidPasswordException.class)
    public void loginPasswordNull() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("username", null);
    }

    @Test (expected = InvalidPasswordException.class)
    public void loginPasswordEmpty() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("username", "");
    }

    @Test
    public void loginWrong() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.createUser("username", "password", "Cashier");
        User login = ezShop.login("username", "wrongPassword");
        assertNull(login);
    }

    @Test
    public void login() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.createUser("username", "password", "Cashier");
        User login = ezShop.login("username", "password");
        assertNotNull("The user has not been found", login);
    }

    @Test
    public void doubleUsername() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.createUser("username", "password", "Cashier");
        Integer userId = ezShop.createUser("username", "password", "Cashier");
        assertEquals(-1, userId.intValue());
    }

    @Test
    public void deleteUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.createUser("username", "password", "Administrator");
        Integer userId = ezShop.createUser("username2", "password", "Cashier");
        ezShop.login("username", "password");
        assertNotNull(ezShop.getUser(userId));
        assertTrue(ezShop.deleteUser(userId));

        assertNull(ezShop.getUser(userId));
    }
}
