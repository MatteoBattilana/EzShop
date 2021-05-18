package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
    }

    @Test
    public void testExecuteStartUpMultipleTimes() {
        databaseConnection = new DatabaseConnection();
        databaseConnection = new DatabaseConnection();
    }

    @Test
    public void testExecuteStartUpInvalidFile() {
        File f = new File("src/main/java/it/polito/ezshop/utils/schema.sql");
        if (f.exists() && f.isFile()) {
            f.renameTo(new File("src/main/java/it/polito/ezshop/utils/schema_bak.sql"));
        }
        databaseConnection = new DatabaseConnection();
        File fileBak = new File("src/main/java/it/polito/ezshop/utils/schema_bak.sql");
        if (fileBak.exists() && fileBak.isFile()) {
            fileBak.renameTo(f);
        }
    }

    @Test
    public void testWrongSchemaSQL() throws IOException {
        File f = new File("src/main/java/it/polito/ezshop/utils/schema.sql");
        if (f.exists() && f.isFile()) {
            f.renameTo(new File("src/main/java/it/polito/ezshop/utils/schema_bak.sql"));
        }
        FileWriter myWriter = new FileWriter("src/main/java/it/polito/ezshop/utils/schema.sql");
        myWriter.write("NOT SQL statements");
        myWriter.close();

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
    public void createSaleTransaction() {
    }

    @Test
    public void getAllSaleTransaction() {
    }

    @Test
    public void saveSaleTransaction() {
    }

    @Test
    public void deleteSaleTransaction() {
    }

    @Test
    public void updateSaleTransaction() {
    }

    @Test
    public void saveBalanceOperation() {
    }

    @Test
    public void getAllBalanceOperations() {
    }

    @Test
    public void deleteBalanceOperation() {
    }

    @Test
    public void saveReturnTransaction() {
    }

    @Test
    public void setStatusReturnTransaction() {
    }

    @Test
    public void updateOrder() {
    }

    @Test
    public void getAllOrders() {
    }

    @Test
    public void deleteOrder() {
    }

    @Test
    public void createCustomer() {
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void getAllCustomers() {
    }

    @Test
    public void getAllCustomerCards() {
    }

    @Test
    public void deleteCustomer() {
    }

    @Test
    public void createCustomerCard() {
    }

    @Test
    public void updateCustomerCard() {
    }

    @Test
    public void deleteReturnTransaction() {
    }

    @Test
    public void deleteAllTransactionProducts() {
    }

    @Test
    public void testBalance() {
        assertEquals(0.0, databaseConnection.getBalance(), 0.1);
        assertTrue(databaseConnection.updateBalance(10.0));
        assertEquals(10.0, databaseConnection.getBalance(), 0.1);
    }
}