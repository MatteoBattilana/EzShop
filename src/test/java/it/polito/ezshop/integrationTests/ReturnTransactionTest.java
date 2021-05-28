package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ReturnTransactionTest {
    @Test
    public void testAddProduct() {

        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        TransactionProduct trProduct = new TransactionProduct(apple, 0.1, 10, 2.99);
        assertTrue(returnTransaction.addProduct(trProduct, 8));
        assertTrue(returnTransaction.addProduct(trProduct, 2));
        assertFalse(returnTransaction.addProduct(trProduct, 2));

    }
    @Test
    public void testGetMoney() {
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50);
        returnTransaction.addProduct(trProduct,2);
        returnTransaction.addProduct(trProduct2,3);
        Double price =((trProduct.getPricePerUnit() - trProduct.getPricePerUnit() *trProduct.getDiscountRate()  ) * 2) ;
        Double price2= ((trProduct2.getPricePerUnit() - trProduct2.getPricePerUnit() * trProduct2.getDiscountRate()) * 3);
        Double sum= -(price - price * 0.1)-(price2 - price2 * 0.1);
        assertEquals(sum,returnTransaction.getMoney(),0.01);

    }
    @Test
    public void testComputeTotal() {
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50);
        returnTransaction.addProduct(trProduct,2);
        returnTransaction.addProduct(trProduct2,3);
        assertEquals( Math.abs(returnTransaction.getMoney()),returnTransaction.computeTotal(),0.01);

    }
    @Test
    public void testGetReturns() {
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50);
        returnTransaction.addProduct(trProduct,2);
        returnTransaction.addProduct(trProduct2,3);
        Map<TransactionProduct, Integer> returns= new HashMap<>();
        returns.put(trProduct,2);
        returns.put(trProduct2,3);
        assertEquals( returns,returnTransaction.getReturns());

    }





}