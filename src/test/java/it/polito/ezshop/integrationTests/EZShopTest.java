package it.polito.ezshop.integrationTests;


import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class EZShopTest {
    EZShop ezShop;

    @Before
    public void setup(){
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
        ezShop = new EZShop();
    }

    @Test
    public void addManyProduct() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        for(int i = 0; i<1; i++){
            ezShop.createUser("username"+i, "pass"+i, "Administrator");
        }
    }

    @Test
    public void normalSale() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);

        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        assertEquals(10 - 5, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        assertEquals(10 - 2, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());

        ezShop.endSaleTransaction(saleId);
        double resto = ezShop.receiveCashPayment(saleId, 100);
        assertEquals(100.0-(5*1 + 2*2), resto, 0.1);

        assertEquals(5*1 + 2*2, ezShop.computeBalance(), 0.1);

        List<BalanceOperation> creditsAndDebits = ezShop.getCreditsAndDebits(null, null);
        boolean find = false;
        for (BalanceOperation op: creditsAndDebits)
            if (op.getType().equals("SALE") && op.getMoney() == 9.0) find = true;
        assertTrue(find);
    }

    @Test
    public void rollBackSale() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        double initialBalance = ezShop.computeBalance();
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);

        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.endSaleTransaction(saleId);
        assertTrue(ezShop.deleteSaleTransaction(saleId));

        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        List<BalanceOperation> creditsAndDebits = ezShop.getCreditsAndDebits(null, null);

        assertEquals(initialBalance, ezShop.computeBalance(), 0.1);
        boolean find = false;
        for (BalanceOperation op: creditsAndDebits)
            if (op.getType().equals("SALE") && op.getMoney() == 9.0) find = true;
        assertFalse(find);
    }

    @Test
    public void returnTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);
        double initialBalance = ezShop.computeBalance();

        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId.intValue());
        assertFalse(ezShop.returnProduct(returnId, "1234567890128", 6));
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertFalse(ezShop.returnProduct(returnId, "01234567890128", 3));
        assertTrue(ezShop.returnProduct(returnId, "01234567890128", 2));

        assertTrue(ezShop.endReturnTransaction(returnId, true));
        assertEquals(5+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5-2, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2-2, t.getAmount());
        }

        assertEquals(2*2 + 2*1, ezShop.returnCashPayment(returnId), 0.1);
        assertFalse(ezShop.deleteReturnTransaction(returnId));

        for (BalanceOperation t : ezShop.getCreditsAndDebits(null, null)){
            if(t.getType().equals("RETURN")) assertEquals(- (2*2 + 2*1), t.getMoney(), 0.1);
        }

        assertEquals(initialBalance - (2*2 + 2*1), ezShop.computeBalance(), 0.1);


        // Try to open another return transaction
        Integer return2 = ezShop.startReturnTransaction(saleId);
        assertTrue(return2 > 0 && returnId < return2);
        assertTrue(ezShop.returnProduct(return2, "1234567890128", 2));
        assertTrue(ezShop.endReturnTransaction(return2, true));
        assertEquals(7+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
    }

    @Test
    public void returnTransactionDeleteBeforePay() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        double initialBalance = ezShop.computeBalance();
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);

        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId.intValue());
        assertFalse(ezShop.returnProduct(returnId, "1234567890128", 6));
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertFalse(ezShop.returnProduct(returnId, "01234567890128", 3));
        assertTrue(ezShop.returnProduct(returnId, "01234567890128", 2));

        assertTrue(ezShop.endReturnTransaction(returnId, true));
        assertEquals(5+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5-2, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2-2, t.getAmount());
        }

        assertTrue(ezShop.deleteReturnTransaction(returnId));
        assertEquals(5, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(8, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2, t.getAmount());
        }
    }

    @Test
    public void returnTransactionCommitFalse() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.createUser("username", "password", "Administrator");
        ezShop.login("username", "password");

        double initialBalance = ezShop.computeBalance();
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);

        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId.intValue());
        assertFalse(ezShop.returnProduct(returnId, "1234567890128", 6));
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertFalse(ezShop.returnProduct(returnId, "01234567890128", 3));
        assertTrue(ezShop.returnProduct(returnId, "01234567890128", 2));

        assertTrue(ezShop.endReturnTransaction(returnId, false));
        assertEquals(5, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(8, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2, t.getAmount());
        }
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

