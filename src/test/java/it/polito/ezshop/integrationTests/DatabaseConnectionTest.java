package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class DatabaseConnectionTest {
    DatabaseConnection databaseConnection;

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
    public void testExecuteStartUpMultipleTimes() {
        databaseConnection.closeConnection();
        databaseConnection = new DatabaseConnection();

        databaseConnection.closeConnection();
        databaseConnection = new DatabaseConnection();
    }

    @Test
    public void testExecuteStartUpInvalidFile() {
        File f = new File("src/main/java/it/polito/ezshop/utils/schema.sql");
        if (f.exists() && f.isFile()) {
            f.renameTo(new File("src/main/java/it/polito/ezshop/utils/schema_bak.sql"));
        }

        databaseConnection.closeConnection();
        databaseConnection = new DatabaseConnection();
        File fileBak = new File("src/main/java/it/polito/ezshop/utils/schema_bak.sql");
        if (fileBak.exists() && fileBak.isFile()) {
            fileBak.renameTo(f);
        }
    }

    @Test
    public void testCreateUser() {
        UserImpl user = new UserImpl(1, "matteo", "password", "Cashier");
        assertTrue(databaseConnection.createUser(user));

        Map<Integer, User> allUsers = databaseConnection.getAllUsers();
        assertEquals(1, allUsers.size());
        User gotUser = allUsers.get(1);
        assertNotNull(gotUser);
        assertEquals("matteo", gotUser.getUsername());
        assertEquals(1, gotUser.getId().intValue());
        assertEquals("password", gotUser.getPassword());
        assertEquals("Cashier", gotUser.getRole());

        assertFalse(databaseConnection.createUser(null));
    }

    @Test
    public void testSetUserRole() {
        UserImpl user = new UserImpl(1, "matteo", "password", "Cashier");
        assertTrue(databaseConnection.createUser(user));
        assertTrue(databaseConnection.setUserRole(user, "ShopManager"));
        Map<Integer, User> allUsers = databaseConnection.getAllUsers();
        User gotUser = allUsers.get(1);
        assertEquals("ShopManager", gotUser.getRole());

        assertFalse(databaseConnection.setUserRole(null, "Administrator"));
    }

    @Test
    public void testSetWrongRole() {
        UserImpl user = new UserImpl(1, "matteo", "password", "Cashier");
        assertTrue(databaseConnection.createUser(user));

        // Wrong
        assertFalse(databaseConnection.setUserRole(user, "WrongRole"));
        Map<Integer, User> allUsers = databaseConnection.getAllUsers();
        User gotUser = allUsers.get(1);
        assertEquals("Cashier", gotUser.getRole());

        // Null
        assertFalse(databaseConnection.setUserRole(user, null));
        allUsers = databaseConnection.getAllUsers();
        gotUser = allUsers.get(1);
        assertEquals("Cashier", gotUser.getRole());
    }

    @Test
    public void testDeleteUser() {
        UserImpl user = new UserImpl(1, "matteo", "password", "Cashier");
        assertTrue(databaseConnection.createUser(user));
        UserImpl user2 = new UserImpl(2, "luca", "password", "Cashier");
        assertFalse(databaseConnection.deleteUser(user2));

        assertTrue(databaseConnection.deleteUser(user));
        Map<Integer, User> allUsers = databaseConnection.getAllUsers();
        assertEquals(0, allUsers.size());

        assertFalse(databaseConnection.deleteUser(null));
    }

    @Test
    public void testGetAllUsers() {
        for (int i = 0; i < 10; i++) {
            assertTrue(databaseConnection.createUser(
                    new UserImpl(i, "username" + i, "password", "ShopManager")
                    )
            );
        }

        Map<Integer, User> allUsers = databaseConnection.getAllUsers();
        for (int i = 0; i < 10; i++) {
            User user = allUsers.get(i);
            assertNotNull(user);
            assertEquals(i, user.getId().intValue());
        }
    }

    @Test
    public void testCreateOrder() {
        OrderImpl order = new OrderImpl(1, "1234567890128", 1.0, 10, "UNPAID", "ISSUED");
        assertTrue(databaseConnection.createOrder(order));

        Map<Integer, OrderImpl> allOrders = databaseConnection.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals(1, allOrders.get(1).getBalanceId());
        assertEquals(1.0, allOrders.get(1).getPricePerUnit(), 0.1);
        assertEquals(10, allOrders.get(1).getQuantity());
        assertEquals("ISSUED", allOrders.get(1).getOrderStatus());
        assertEquals("UNPAID", allOrders.get(1).getStatus());
        assertEquals("1234567890128", allOrders.get(1).getProductCode());

        assertFalse(databaseConnection.createOrder(null));
    }

    @Test
    public void testGetAllProducts() {
        ProductTypeImpl p1 = new ProductTypeImpl(10, "1-A-2", "note", "apple", "1234567890128", 3.99, 1);
        ProductTypeImpl p2 = new ProductTypeImpl(5, "1-A-3", "note", "banana", "01234567890128", 2.99, 2);
        assertTrue(databaseConnection.createProductType(p1));
        assertTrue(databaseConnection.createProductType(p2));

        Map<Integer, ProductTypeImpl> allProducts = databaseConnection.getAllProducts();
        assertEquals(2, allProducts.size());
        assertEquals("1234567890128", allProducts.get(1).getBarCode());
        assertEquals("01234567890128", allProducts.get(2).getBarCode());
        assertEquals(1, allProducts.get(1).getId().intValue());
        assertEquals(2, allProducts.get(2).getId().intValue());
    }

    @Test
    public void testCreateProductType() {
        ProductTypeImpl p1 = new ProductTypeImpl(10, "1-A-2", "note", "apple", "1234567890128", 3.99, 1);
        assertTrue(databaseConnection.createProductType(p1));

        Map<Integer, ProductTypeImpl> allProducts = databaseConnection.getAllProducts();
        assertEquals(1, allProducts.size());
        assertEquals("1234567890128", allProducts.get(1).getBarCode());
        assertEquals(10, allProducts.get(1).getQuantity().intValue());
        assertEquals("1-A-2", allProducts.get(1).getLocation());
        assertEquals("note", allProducts.get(1).getNote());
        assertEquals("apple", allProducts.get(1).getProductDescription());
        assertEquals(3.99, allProducts.get(1).getPricePerUnit(), 0.1);
        assertEquals(1, allProducts.get(1).getId().intValue());


        assertFalse(databaseConnection.createProductType(null));
    }

    @Test
    public void testUpdateProductType() {
        ProductTypeImpl p1 = new ProductTypeImpl(10, "1-A-2", "note", "apple", "1234567890128", 3.99, 1);
        assertTrue(databaseConnection.createProductType(p1));
        assertTrue(databaseConnection.updateProductType(p1));

        p1.setPosition("1-A-3");
        p1.setQuantity(12);
        p1.setNote("note2");
        p1.setProductDescription("apple-bis");
        p1.setBarCode("01234567890128");
        p1.setPricePerUnit(1.99);
        assertTrue(databaseConnection.updateProductType(p1));

        Map<Integer, ProductTypeImpl> allProducts = databaseConnection.getAllProducts();
        assertEquals(1, allProducts.size());
        assertEquals("01234567890128", allProducts.get(1).getBarCode());
        assertEquals(12, allProducts.get(1).getQuantity().intValue());
        assertEquals("1-A-3", allProducts.get(1).getLocation());
        assertEquals("note2", allProducts.get(1).getNote());
        assertEquals("apple-bis", allProducts.get(1).getProductDescription());
        assertEquals(1.99, allProducts.get(1).getPricePerUnit(), 0.1);
        assertEquals(1, allProducts.get(1).getId().intValue());

        assertFalse(databaseConnection.updateProductType(null));
    }

    @Test
    public void testDeleteProductType() {
        ProductTypeImpl p1 = new ProductTypeImpl(10, "1-A-2", "note", "apple", "1234567890128", 3.99, 1);
        assertTrue(databaseConnection.createProductType(p1));

        assertTrue(databaseConnection.deleteProductType(p1));
        assertFalse(databaseConnection.deleteProductType(p1));
        assertFalse(databaseConnection.deleteProductType(null));
    }

    @Test
    public void testSaveSaleTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);

        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        saleTransaction.setTransactionStatus("OPENED");
        saleTransaction.setDiscountRate(.2);
        saleTransaction.addProductToSale(apple, new Product("00000000001"));
        saleTransaction.addProductToSale(apple, new Product("00000000002"));

        assertTrue(databaseConnection.saveSaleTransaction(saleTransaction));
        Map<Integer, SaleTransactionImpl> allSaleTransaction = databaseConnection.getAllSaleTransaction(allProducts);

        assertEquals(1, allSaleTransaction.size());
        SaleTransactionImpl dbSaleTransaction = allSaleTransaction.get(1);
        assertNotNull(dbSaleTransaction);
        assertEquals(saleTransaction.getBalanceId(), dbSaleTransaction.getBalanceId());
        assertEquals("OPENED", dbSaleTransaction.getTransactionStatus());
        assertEquals(.2, dbSaleTransaction.getDiscountRate(), 0.1);
        assertEquals(1, dbSaleTransaction.getEntries().size());
        for(Product p: dbSaleTransaction.getTicketEntries().get(0).getProducts()){
            if(!p.getRFID().equals("00000000001") && !p.getRFID().equals("00000000002"))
                fail();
        }
        List<TicketEntry> entries = dbSaleTransaction.getEntries();
        assertEquals(1, entries.size());
        assertEquals(2, entries.get(0).getAmount());
        assertEquals(apple.getBarCode(), entries.get(0).getBarCode());
        assertEquals(apple.getPricePerUnit(), entries.get(0).getPricePerUnit(), 0.1);
        assertEquals(apple.getProductDescription(), entries.get(0).getProductDescription());
        assertEquals(apple.getBarCode(), entries.get(0).getBarCode());

        assertFalse(databaseConnection.saveSaleTransaction(null));
    }

    @Test
    public void testGetAllSaleTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);

        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        SaleTransactionImpl saleTransaction2 = new SaleTransactionImpl(databaseConnection, 2);
        saleTransaction.addProductToSale(apple, new Product("00000000001"));
        saleTransaction.addProductToSale(apple, new Product("00000000002"));
        saleTransaction.addProductToSale(apple, new Product("00000000003"));
        saleTransaction.addProductToSale(apple, new Product("00000000004"));
        saleTransaction.addProductToSale(apple, new Product("00000000005"));

        assertTrue(databaseConnection.saveSaleTransaction(saleTransaction));
        assertTrue(databaseConnection.saveSaleTransaction(saleTransaction2));
        Map<Integer, SaleTransactionImpl> allSaleTransaction = databaseConnection.getAllSaleTransaction(allProducts);

        assertEquals(2, allSaleTransaction.size());
        assertEquals(saleTransaction.getBalanceId(), allSaleTransaction.get(1).getBalanceId());
        assertEquals(saleTransaction2.getBalanceId(), allSaleTransaction.get(2).getBalanceId());
    }

    @Test
    public void testDeleteSaleTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);
        databaseConnection.createProductType(apple);

        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        saleTransaction.addProductToSale(apple, new Product("00000000001"));
        saleTransaction.addProductToSale(apple, new Product("00000000002"));
        saleTransaction.addProductToSale(apple, new Product("00000000003"));
        saleTransaction.addProductToSale(apple, new Product("00000000004"));
        saleTransaction.addProductToSale(apple, new Product("00000000005"));
        databaseConnection.saveSaleTransaction(saleTransaction);

        assertEquals(1, databaseConnection.getAllBySaleId(saleTransaction.getBalanceId(), allProducts).size());
        assertTrue(databaseConnection.deleteSaleTransaction(saleTransaction));
        assertEquals(10, apple.getQuantity().intValue());
        assertEquals(0, databaseConnection.getAllBySaleId(saleTransaction.getBalanceId(), allProducts).size());
        assertTrue(databaseConnection.deleteSaleTransaction(saleTransaction));
        assertFalse(databaseConnection.deleteSaleTransaction(null));
    }

    @Test
    public void testUpdateSaleTransactionWithoutProducts() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);

        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        databaseConnection.saveSaleTransaction(saleTransaction);
        saleTransaction.setDiscountRate(.1);
        saleTransaction.addProductToSale(apple, new Product("00000000001"));
        saleTransaction.addProductToSale(apple, new Product("00000000002"));
        saleTransaction.addProductToSale(apple, new Product("00000000003"));
        saleTransaction.addProductToSale(apple, new Product("00000000004"));
        saleTransaction.addProductToSale(apple, new Product("00000000005"));
        assertTrue(databaseConnection.updateSaleTransaction(saleTransaction));

        Map<Integer, SaleTransactionImpl> allSaleTransaction = databaseConnection.getAllSaleTransaction(allProducts);
        List<TransactionProduct> ticketEntries = allSaleTransaction.get(1).getTicketEntries();
        assertEquals(1, ticketEntries.size());
        assertEquals(apple, ticketEntries.get(0).getProductType());
    }

    @Test
    public void testUpdateSaleTransaction() {
        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        databaseConnection.saveSaleTransaction(saleTransaction);
        saleTransaction.setDiscountRate(.1);
        saleTransaction.setTransactionStatus("CLOSED");
        assertTrue(databaseConnection.updateSaleTransaction(saleTransaction));

        Map<Integer, SaleTransactionImpl> allSaleTransaction = databaseConnection.getAllSaleTransaction(Collections.emptyMap());
        assertEquals("CLOSED", allSaleTransaction.get(1).getTransactionStatus());
        assertEquals(.1, allSaleTransaction.get(1).getDiscountRate(), 0.1);

        assertFalse(databaseConnection.updateSaleTransaction(null));
    }

    @Test
    public void testSaveBalanceOperation() {
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 100.0, "SALE", "PAID");
        assertTrue(databaseConnection.saveBalanceOperation(balanceOperation));

        List<BalanceOperationImpl> allBalanceOperations = databaseConnection.getAllBalanceOperations();
        assertEquals(1, allBalanceOperations.size());
        assertEquals("SALE", allBalanceOperations.get(0).getType());
        assertEquals("PAID", allBalanceOperations.get(0).getStatus());
        assertEquals(1, allBalanceOperations.get(0).getBalanceId());
        assertEquals(100.0, allBalanceOperations.get(0).getMoney(), 0.1);

        assertFalse(databaseConnection.saveBalanceOperation(null));
    }

    @Test
    public void testGetAllBalanceOperations() {
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 100.0, "SALE", "PAID");
        BalanceOperationImpl balanceOperation2 = new BalanceOperationImpl(2, LocalDate.now(), 120.0, "ORDER", "PAID");
        databaseConnection.saveBalanceOperation(balanceOperation);
        databaseConnection.saveBalanceOperation(balanceOperation2);
        List<BalanceOperationImpl> allBalanceOperations = databaseConnection.getAllBalanceOperations();
        assertEquals(2, allBalanceOperations.size());
        if(allBalanceOperations.get(0).getBalanceId() != 1 && allBalanceOperations.get(0).getBalanceId() != 2)
            fail();
        if(allBalanceOperations.get(1).getBalanceId() != 1 && allBalanceOperations.get(1).getBalanceId() != 2)
            fail();
    }

    @Test
    public void testGetAllReturnTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);

        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);

        ReturnTransaction r1 = new ReturnTransaction(1, 0.1);
        r1.addProduct(new TransactionProduct(apple, 0.1, 2, 1.99, prods), product1);
        databaseConnection.saveReturnTransaction(r1, 3);

        Map<Integer, ReturnTransaction> allReturnTransaction = databaseConnection.getAllReturnTransaction(3, allProducts, 0.1, Collections.emptyMap());
        assertEquals(1, allReturnTransaction.size());
        assertEquals(1, allReturnTransaction.get(r1.getBalanceId()).getReturns().size());
        Map<TransactionProduct, Map<String, Product>> returns = allReturnTransaction.get(r1.getBalanceId()).getReturns();
        for (Map<String, Product> asd : returns.values()){
            if(!asd.containsKey("0000000001"))
                fail();
        }
    }

    @Test
    public void testDeleteBalanceOperation() {
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 100.0, "SALE", "PAID");
        databaseConnection.saveBalanceOperation(balanceOperation);

        assertTrue(databaseConnection.deleteBalanceOperation(balanceOperation));
        assertFalse(databaseConnection.deleteBalanceOperation(balanceOperation));
        assertFalse(databaseConnection.deleteBalanceOperation(null));
    }

    @Test
    public void saveReturnTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);


        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);

        returnTransaction.addProduct(new TransactionProduct(apple, 0.1, 10, 2.99, prods),  product1);

        assertTrue(databaseConnection.saveReturnTransaction(returnTransaction, 2));
        assertFalse(databaseConnection.saveReturnTransaction(null, 1));
    }

    @Test
    public void setStatusReturnTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);


        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);

        returnTransaction.addProduct(new TransactionProduct(apple, 0.1, 10, 2.99, prods), product1);

        assertTrue(databaseConnection.saveReturnTransaction(returnTransaction, 2));

        returnTransaction.setStatus("PAID");
        assertTrue(databaseConnection.setStatusReturnTransaction(returnTransaction));

        assertFalse(databaseConnection.setStatusReturnTransaction(null));
    }

    @Test
    public void testUpdateOrder() {
        OrderImpl order1 = new OrderImpl(1, "1234567890128", 1.0, 10, "UNPAID", "ISSUED");
        databaseConnection.createOrder(order1);
        order1.setStatus("PAID");
        order1.setOrderStatus("COMPLETED");
        order1.setQuantity(1);
        order1.setPricePerUnit(10.0);
        order1.setProductCode("01234567890128");
        assertTrue(databaseConnection.updateOrder(order1));

        Map<Integer, OrderImpl> allOrders = databaseConnection.getAllOrders();
        assertEquals("PAID", allOrders.get(1).getStatus());
        assertEquals("COMPLETED", allOrders.get(1).getOrderStatus());
        assertEquals("01234567890128", allOrders.get(1).getProductCode());
        assertEquals(1, allOrders.get(1).getQuantity());
        assertEquals(10.0, allOrders.get(1).getPricePerUnit(),0.1);

        assertFalse(databaseConnection.updateOrder(null));
    }

    @Test
    public void testGetAllOrders() {
        OrderImpl order1 = new OrderImpl(1, "1234567890128", 1.0, 10, "UNPAID", "ISSUED");
        OrderImpl order2 = new OrderImpl(2, "1234567890128", 1.0, 10, "PAID", "PAYED");
        OrderImpl order3 = new OrderImpl(3, "1234567890128", 1.0, 10, "PAID", "COMPLETED");
        databaseConnection.createOrder(order1);
        databaseConnection.createOrder(order2);
        databaseConnection.createOrder(order3);

        Map<Integer, OrderImpl> allOrders = databaseConnection.getAllOrders();
        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(1).getBalanceId());
        assertEquals(2, allOrders.get(2).getBalanceId());
        assertEquals(3, allOrders.get(3).getBalanceId());
    }

    @Test
    public void testDeleteOrder() {
        OrderImpl order = new OrderImpl(1, "1234567890128", 1.0, 10, "UNPAID", "ISSUED");
        databaseConnection.createOrder(order);
        assertTrue(databaseConnection.deleteOrder(order));
        assertFalse(databaseConnection.deleteOrder(null));
    }

    @Test
    public void testCreateCustomer() {
        CustomerImpl c1 = new CustomerImpl(1, "name");
        assertTrue(databaseConnection.createCustomer(c1));
        Map<Integer, CustomerImpl> allCustomers = databaseConnection.getAllCustomers(Collections.emptyMap());
        assertEquals(1, allCustomers.size());
        assertEquals(1, allCustomers.get(1).getId().intValue());
        assertNull(allCustomers.get(1).getCustomerCard());
        assertNull(allCustomers.get(1).getPoints());
        assertEquals("name", allCustomers.get(1).getCustomerName());

        assertFalse(databaseConnection.createCustomer(null));
    }

    @Test
    public void testUpdateCustomer() {
        CustomerImpl c1 = new CustomerImpl(1, "name");
        assertTrue(databaseConnection.createCustomer(c1));
        c1.setCustomerName("matteo");

        assertTrue(databaseConnection.updateCustomer(c1));
        Map<Integer, CustomerImpl> allCustomers = databaseConnection.getAllCustomers(Collections.emptyMap());
        assertEquals("matteo", allCustomers.get(1).getCustomerName());

        assertFalse(databaseConnection.updateCustomer(null));
    }

    @Test
    public void testGetAllCustomers() {
        CustomerImpl c1 = new CustomerImpl(1, "a");
        CustomerImpl c2 = new CustomerImpl(2, "b");
        CustomerImpl c3 = new CustomerImpl(3, "c");
        assertTrue(databaseConnection.createCustomer(c1));
        assertTrue(databaseConnection.createCustomer(c2));
        assertTrue(databaseConnection.createCustomer(c3));

        Map<Integer, CustomerImpl> allCustomers = databaseConnection.getAllCustomers(Collections.emptyMap());
        assertEquals(3, allCustomers.size());
        assertEquals("a", allCustomers.get(1).getCustomerName());
        assertEquals("b", allCustomers.get(2).getCustomerName());
        assertEquals("c", allCustomers.get(3).getCustomerName());
    }

    @Test
    public void testGetAllCustomerCards() {
        CustomerCardImpl c1 = new CustomerCardImpl("0000000001", 10);
        CustomerCardImpl c2 = new CustomerCardImpl("0000000002", 100);
        CustomerCardImpl c3 = new CustomerCardImpl("0000000003", 1000);
        assertTrue(databaseConnection.createCustomerCard(c1));
        assertTrue(databaseConnection.createCustomerCard(c2));
        assertTrue(databaseConnection.createCustomerCard(c3));

        Map<String, CustomerCardImpl> allCustomerCards = databaseConnection.getAllCustomerCards();
        assertEquals(3, allCustomerCards.size());
        assertEquals(10, allCustomerCards.get("0000000001").getPoints().intValue());
        assertEquals(100, allCustomerCards.get("0000000002").getPoints().intValue());
        assertEquals(1000, allCustomerCards.get("0000000003").getPoints().intValue());
    }

    @Test
    public void testDeleteCustomer() {
        CustomerImpl customer = new CustomerImpl(1, "matteo");
        databaseConnection.createCustomer(customer);

        assertTrue(databaseConnection.deleteCustomer(customer));
        assertFalse(databaseConnection.deleteCustomer(customer));
        assertFalse(databaseConnection.deleteCustomer(null));

        assertEquals(0, databaseConnection.getAllCustomers(Collections.emptyMap()).size());
    }

    @Test
    public void testCreateCustomerCard() {
        CustomerCardImpl c1 = new CustomerCardImpl("0000000001", 10);
        assertTrue(databaseConnection.createCustomerCard(c1));

        assertFalse(databaseConnection.createCustomerCard(null));
    }

    @Test
    public void testUpdateCustomerCard() {
        CustomerCardImpl c1 = new CustomerCardImpl("0000000001", 10);
        assertTrue(databaseConnection.createCustomerCard(c1));
        c1.setPoints(100);
        assertTrue(databaseConnection.updateCustomerCard(c1));
        Map<String, CustomerCardImpl> allCustomerCards = databaseConnection.getAllCustomerCards();
        assertEquals(1, allCustomerCards.size());
        assertEquals(100, allCustomerCards.get("0000000001").getPoints().intValue());


        assertFalse(databaseConnection.updateCustomerCard(null));
    }

    @Test
    public void deleteReturnTransaction() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        ReturnTransaction returnTransaction = new ReturnTransaction(1, 0.1);
        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);

        returnTransaction.addProduct(new TransactionProduct(apple, 0.1, 10, 2.99, prods),  product1);

        assertTrue(databaseConnection.saveReturnTransaction(returnTransaction, 2));
        assertTrue(databaseConnection.deleteReturnTransaction(returnTransaction));
        assertFalse(databaseConnection.deleteReturnTransaction(returnTransaction));
        assertFalse(databaseConnection.deleteReturnTransaction(null));
    }

    @Test
    public void deleteAllTransactionProducts() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        Map<Integer, ProductTypeImpl> allProducts = new HashMap<>();
        allProducts.put(apple.getId(), apple);

        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);

        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);
        saleTransaction.addProductToSale(apple, product1);
        saleTransaction.addProductToSale(apple, product2);

        assertTrue(databaseConnection.saveSaleTransaction(saleTransaction));
        assertTrue(databaseConnection.deleteAllTransactionProducts(saleTransaction.getBalanceId()));

        Map<Integer, SaleTransactionImpl> allSaleTransaction = databaseConnection.getAllSaleTransaction(allProducts);
        assertEquals(1, allSaleTransaction.size());
        assertEquals(0, allSaleTransaction.get(1).getEntries().size());

        assertFalse(databaseConnection.deleteAllTransactionProducts(-1));
    }

    @Test
    public void testBalance() {
        assertEquals(0.0, databaseConnection.getBalance(), 0.1);
        assertTrue(databaseConnection.updateBalance(10.0));
        assertEquals(10.0, databaseConnection.getBalance(), 0.1);

        assertFalse(databaseConnection.updateBalance(-10.0));
        assertEquals(10.0, databaseConnection.getBalance(), 0.1);
    }

    @Test
    public void testAddProductToSale() {
        ProductTypeImpl apple = new ProductTypeImpl(10, "1-A-11", "", "apple", "012345678901280", 1.99, 1);
        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(databaseConnection, 1);
        HashMap<String, Product> prods = new HashMap<>();
        Product product1 = new Product("0000000001");
        prods.put("0000000001", product1);
        Product product2 = new Product("0000000002");
        prods.put("0000000002", product2);

        TransactionProduct transactionProduct = new TransactionProduct(apple, 0.1, 1, 2.99, prods);
        assertTrue(databaseConnection.addProductToSale(saleTransaction, transactionProduct));

        assertFalse(databaseConnection.addProductToSale(null, transactionProduct));
        assertFalse(databaseConnection.addProductToSale(saleTransaction, null));
        assertFalse(databaseConnection.addProductToSale(null, null));

    }

    @Test
    public void deleteWrongParameterCustomerCard() {
        assertFalse(databaseConnection.deleteCustomerCard(null));
    }

    @Test
    public void deleteCustomerCard() {
        CustomerCardImpl card = new CustomerCardImpl("0000000001", 1);
        databaseConnection.createCustomerCard(card);
        assertTrue(databaseConnection.deleteCustomerCard(card));
        assertFalse(databaseConnection.deleteCustomerCard(card));
    }

}