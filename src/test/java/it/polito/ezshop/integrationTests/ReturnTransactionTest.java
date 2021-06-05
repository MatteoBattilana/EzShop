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
        HashMap<String, Product> stringProductHashMap = new HashMap<>();
        for(int i = 0; i < 10; i++){
            stringProductHashMap.put("000000000"+i, new Product("000000000"+i));
        }
        TransactionProduct trProduct = new TransactionProduct(apple, 0.1, 10, 2.99, stringProductHashMap);
        for(int i = 0 ; i < 10; i++ ){
            assertTrue(returnTransaction.addProduct(trProduct, stringProductHashMap.get("000000000"+i)));
        }
        assertFalse(returnTransaction.addProduct(trProduct, stringProductHashMap.get("0000000011")));

    }
    @Test
    public void testGetMoney() {
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        HashMap<String, Product> p1H = new HashMap<>();
        HashMap<String, Product> p2H = new HashMap<>();

        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99, p1H);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50, p2H);
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000000" + i);
            p1H.put("000000000"+i, product1);
            trProduct.addProduct(product1);
        }
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000001" + i);
            p2H.put("000000000"+i, new Product("000000001"+i));
            trProduct2.addProduct(product1);
        }
        for(int i = 0; i < 2; i++){
            returnTransaction.addProduct(trProduct, p1H.get("000000000"+i));
        }
        for(int i = 0; i < 3; i++){
            returnTransaction.addProduct(trProduct2, p2H.get("000000001"+i));
        }
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
        HashMap<String, Product> p1H = new HashMap<>();
        HashMap<String, Product> p2H = new HashMap<>();

        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99, p1H);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50, p2H);
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000000" + i);
            p1H.put("000000000"+i, product1);
            trProduct.addProduct(product1);
        }
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000001" + i);
            p2H.put("000000000"+i, new Product("000000001"+i));
            trProduct2.addProduct(product1);
        }
        for(int i = 0; i < 2; i++){
            returnTransaction.addProduct(trProduct, p1H.get("000000000"+i));
        }
        for(int i = 0; i < 3; i++){
            returnTransaction.addProduct(trProduct2, p2H.get("000000001"+i));
        }
        assertEquals( Math.abs(returnTransaction.getMoney()),returnTransaction.computeTotal(),0.01);

    }
    @Test
    public void testGetReturns() {
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        HashMap<String, Product> p1H = new HashMap<>();
        HashMap<String, Product> p2H = new HashMap<>();

        TransactionProduct trProduct = new TransactionProduct(product2, 0.1, 10, 2.99, p1H);
        TransactionProduct trProduct2 = new TransactionProduct(product, 0.0, 5, 2.50, p2H);
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000000" + i);
            p1H.put("000000000"+i, product1);
            trProduct.addProduct(product1);
        }
        for(int i = 0; i < 10; i++){
            Product product1 = new Product("000000001" + i);
            p2H.put("000000000"+i, new Product("000000001"+i));
            trProduct2.addProduct(product1);
        }
        for(int i = 0; i < 2; i++){
            returnTransaction.addProduct(trProduct, p1H.get("000000000"+i));
        }
        for(int i = 0; i < 3; i++){
            returnTransaction.addProduct(trProduct2, p2H.get("000000001"+i));
        }
        Map<TransactionProduct, Integer> returns= new HashMap<>();
        returns.put(trProduct,2);
        returns.put(trProduct2,3);
        assertEquals( 2,returnTransaction.getReturns().size());

    }




}