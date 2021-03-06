package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class SaleTransactionImplTest {
    private DatabaseConnection databaseConnection;

    @Before
    public void setUp() throws Exception {
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
        databaseConnection = new DatabaseConnection();
    }

    @After
    public void tearDown() throws Exception {
        databaseConnection.closeConnection();
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
    }
    
    @Test
    public void testSecondConstructor() {
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        assertEquals(LocalDate.now(), sale.getDate());
        assertEquals("SALE", sale.getType());
        assertEquals(1, sale.getTicketNumber(), 1);
        assertEquals(0.0, sale.getDiscountRate(), 0.1);
        assertEquals("UNPAID", sale.getStatus());
        assertEquals("OPENED", sale.getTransactionStatus());
    }
    @Test
    public void testStartReturnTransaction() {
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ReturnTransaction returnT =sale.startReturnTransaction(2);
        assertNotNull(returnT);
        assertEquals(null,  sale.startReturnTransaction(-1));


    }

    @Test
    public void testGetReturnTransactions() {
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        List<ReturnTransaction> returns = new ArrayList<>();
        ReturnTransaction returnT = sale.startReturnTransaction(2);
        ReturnTransaction returnT2 = sale.startReturnTransaction(3);
        returns.add(returnT);
        returns.add(returnT2);
        assertEquals(returns,  sale.getReturnTransactions());


    }
    @Test
    public void testAddProductToSale() {
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        assertTrue(sale.addProductToSale(product,2));
        assertEquals(3,product.getQuantity(),1);
        assertTrue(sale.addProductToSale(product,3));
        assertFalse(sale.addProductToSale(null,1));
        assertFalse(sale.addProductToSale(product,1));
        sale.setTransactionStatus("CLOSED");
        assertFalse(sale.addProductToSale(product,0));

    }
    @Test
    public void testSetReturnProduct() {
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,2);
        sale.startReturnTransaction(2);
        assertTrue(sale.setReturnProduct(2,product,1));
        assertFalse(sale.setReturnProduct(2,product2,1));
        assertFalse(sale.setReturnProduct(3,product,1));
        assertFalse(sale.setReturnProduct(3,null,1));
        assertFalse(sale.setReturnProduct(-1,product,1));

    }
    @Test
    public void testSetTransactionStatus() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        sale.setTransactionStatus("CLOSED");
        assertEquals("CLOSED",sale.getTransactionStatus());

    }
    @Test
    public void testSetTicketNumber() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        sale.setTicketNumber(4);
        assertEquals(4,sale.getTicketNumber(),1);

    }
    @Test
    public void testSetDiscountRate() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        sale.setDiscountRate(0.45);
        assertEquals(0.45,sale.getDiscountRate(),0.01);

    }
    @Test
    public void testGetMoney() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,2);
        sale.addProductToSale(product2,2);
        assertEquals(sale.getPrice(),sale.getMoney(),0.01);

        }
    @Test
    public void testGetPrice() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,2);
        sale.addProductToSale(product2,2);
        Double price =((product.getPricePerUnit() - product.getPricePerUnit() * 0) * 2) + ((product2.getPricePerUnit() - product2.getPricePerUnit() * 0) * 2);
        assertEquals(price,sale.getPrice(),0.01);

    }
    @Test
    public void testComputeTotal() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,2);
        sale.addProductToSale(product2,2);
        assertEquals(sale.getMoney(),sale.computeTotal(),0.01);

    }
    @Test
    public void testDeleteProductFromSale() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,3);
        assertTrue(sale.deleteProductFromSale(product,1));
        assertEquals(4,product.getQuantity(),1);
        assertTrue(sale.deleteProductFromSale(product,2));
        assertFalse(sale.deleteProductFromSale(null,1));
        assertFalse(sale.deleteProductFromSale(product,10));
        sale.setTransactionStatus("CLOSED");
        assertFalse(sale.deleteProductFromSale(product,1));
        assertFalse(sale.deleteProductFromSale(product2,1));

    }
    @Test
    public void testComputePointsForSale() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        int points =( int) (Math.floor(sale.getMoney()/10.0));
        assertEquals(points,sale.computePointsForSale());

    }
    @Test
    public void testGetReturnTransaction() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ReturnTransaction returnT =sale.startReturnTransaction(2);
        assertEquals(returnT,sale.getReturnTransaction(2));
        assertNull(sale.getReturnTransaction(-1));

    }
    @Test
    public void testApplyDiscountRateToProduct() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        ProductTypeImpl product2 = new ProductTypeImpl(10, "top-shelves", "good", "blue", "010603984300", 0.60, 51);
        sale.addProductToSale(product,3);
        assertTrue(sale.applyDiscountRateToProduct(product,0.20));
        assertFalse(sale.applyDiscountRateToProduct(null,0.20));
        assertFalse(sale.applyDiscountRateToProduct(product,-0.20));
        assertFalse(sale.applyDiscountRateToProduct(product,2));
        sale.setTransactionStatus("CLOSED");
        assertFalse(sale.applyDiscountRateToProduct(product,0.20));
        assertFalse(sale.applyDiscountRateToProduct(product2,-0.20));

    }
    @Test
    public void testDeleteReturnTransaction() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        sale.startReturnTransaction(2);
        assertTrue(sale.deleteReturnTransaction(2));
        assertFalse(sale.deleteReturnTransaction(2));
        assertFalse(sale.deleteReturnTransaction(-1));
        assertFalse(sale.deleteReturnTransaction(4));
        ReturnTransaction returnT =  sale.startReturnTransaction(3);
        returnT.setStatus("CLOSED");
        assertTrue(sale.deleteReturnTransaction(3));
    }
    @Test
    public void testGetReturnTransactionTotal() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ReturnTransaction returnT =  sale.startReturnTransaction(3);
        returnT.setStatus("CLOSED");
        assertEquals(returnT.computeTotal(),sale.getReturnTransactionTotal(3),0.01);
        assertEquals(-1,sale.getReturnTransactionTotal(0),0.01);
        assertEquals(-1,sale.getReturnTransactionTotal(4),0.01);
        returnT.setStatus("OPENED");
        assertEquals(-1,sale.getReturnTransactionTotal(3),0.01);
    }
    @Test
    public void testEndReturnTransaction() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        sale.startReturnTransaction(2);
        assertTrue(sale.endReturnTransaction(2,false));
        assertFalse(sale.endReturnTransaction(0,false));
        sale.startReturnTransaction(3);
        assertTrue(sale.endReturnTransaction(3,true));
        assertFalse(sale.endReturnTransaction(4,true));

    }
    @Test
    public void testSetPaidReturnTransaction() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ReturnTransaction returnT =  sale.startReturnTransaction(3);
        ReturnTransaction returnT2 =  sale.startReturnTransaction(4);
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
       //database connection false
        returnT2.setStatus("CLOSED");
        assertFalse(sale.setPaidReturnTransaction(4));
        assertEquals("CLOSED",returnT2.getStatus());
        //database connection true
        returnT.addProduct(new TransactionProduct(apple, 0.1, 10, 2.99), 10);
        databaseConnection.saveReturnTransaction(returnT, 3);
        returnT.setStatus("CLOSED");
        assertTrue(sale.setPaidReturnTransaction(3));
        assertEquals("PAID",returnT.getStatus());
        //status NO CLOSED
        returnT.setStatus("OPENED");
        assertFalse(sale.setPaidReturnTransaction(3));
        assertEquals("OPENED",returnT.getStatus());
        //wrong parameters
        assertFalse(sale.setPaidReturnTransaction(-1));
        assertFalse(sale.setPaidReturnTransaction(4));

    }
    @Test
    public void testReset() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ProductTypeImpl cherry = new ProductTypeImpl(16, "1-A-12", "", "cherry", "012345678908765", 2.05, 2);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);
        allProducts.put(apple.getId(), cherry);
        databaseConnection.createProductType(apple);
        databaseConnection.createProductType(cherry);
        sale.addProductToSale(apple,8);
        sale.addProductToSale(cherry,10);
        sale.endSaleTransaction();
        databaseConnection.saveSaleTransaction(sale);
        ReturnTransaction returnT = sale.startReturnTransaction(3);
        sale.setReturnProduct(returnT.getBalanceId(), apple, 2);
        sale.endReturnTransaction(returnT.getBalanceId(), true);
        assertEquals(2, databaseConnection.getAllBySaleId(sale.getBalanceId(), allProducts).size());
        assertEquals(1, databaseConnection.getAllReturnTransaction(sale.getBalanceId(), allProducts, 0.1, databaseConnection.getAllBySaleId(sale.getBalanceId(), allProducts)).size());

        sale.reset();
        List<ReturnTransaction> returns = new ArrayList<>();
        List<TransactionProduct> products = new ArrayList<>();
        assertEquals(returns,sale.getReturnTransactions());
        assertEquals(products,sale.getTicketEntries());

        assertEquals(0, databaseConnection.getAllBySaleId(sale.getBalanceId(), allProducts).size());
        assertEquals(0, databaseConnection.getAllReturnTransaction(sale.getBalanceId(), allProducts, 0.1, databaseConnection.getAllBySaleId(sale.getBalanceId(), allProducts)).size());
    }
    @Test
    public void testEndSaleTransaction() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        assertTrue(sale.endSaleTransaction());
        assertEquals("CLOSED",sale.getTransactionStatus());
        assertFalse(sale.endSaleTransaction());

    }
    @Test
    public void testGetSoldQuantity() {
        
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1);
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        sale.addProductToSale(apple,8);
        assertEquals(8,sale.getSoldQuantity(apple));
        assertEquals(0,sale.getSoldQuantity(null));
        ProductTypeImpl cherry = new ProductTypeImpl(16, "1-A-12", "", "cherry", "012345678908765", 2.05, 2);
        assertEquals(0,sale.getSoldQuantity(cherry));
}
    @Test
    public void testGetTicketEntries() {
        
        Map <Integer, ReturnTransaction> returns = new HashMap<>();
        Map<ProductTypeImpl, TransactionProduct> tickets= new HashMap<>();
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ProductTypeImpl cherry = new ProductTypeImpl(16, "1-A-12", "", "cherry", "012345678908765", 2.05, 2);
        tickets.put(apple, new TransactionProduct(apple, 0, 8, apple.getPricePerUnit()));
        tickets.put(apple, new TransactionProduct(cherry, 0, 8, cherry.getPricePerUnit()));
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1,LocalDate.now(),"SALE","UNPAID",tickets,returns,0.0,"OPENED");
        List<TransactionProduct> products= new ArrayList<>(tickets.values());
        assertEquals(products,  sale.getTicketEntries());


    }

    @Test
    public void testDummySetters(){
        
        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);

        saleTransaction.setEntries(Collections.emptyList());
        saleTransaction.setPrice(1);
        saleTransaction.setMoney(1);
    }

    @Test
    public void testGetEntries() {
        
        Map <Integer, ReturnTransaction> returns = new HashMap<>();
        Map<ProductTypeImpl, TransactionProduct> tickets= new HashMap<>();
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ProductTypeImpl cherry = new ProductTypeImpl(16, "1-A-12", "", "cherry", "012345678908765", 2.05, 2);
        tickets.put(apple, new TransactionProduct(apple, 0, 8, apple.getPricePerUnit()));
        tickets.put(apple, new TransactionProduct(cherry, 0, 8, cherry.getPricePerUnit()));
        SaleTransactionImpl sale = new SaleTransactionImpl(databaseConnection, 1,LocalDate.now(),"SALE","UNPAID",tickets,returns,0.0,"OPENED");
        List<TransactionProduct> products= new ArrayList<>(tickets.values());
        assertEquals(products,  sale.getEntries());


    }

}