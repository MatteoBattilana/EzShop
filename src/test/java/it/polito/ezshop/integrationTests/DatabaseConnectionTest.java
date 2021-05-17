package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DatabaseConnection;
import it.polito.ezshop.data.User;
import it.polito.ezshop.data.UserImpl;
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
    public void getAllUsers() {
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
    public void createOrder() {
    }

    @Test
    public void getAllProducts() {
    }

    @Test
    public void updateProductType() {
    }

    @Test
    public void createProductType() {
    }

    @Test
    public void deleteProductType() {
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
    public void updateBalance() {
    }

    @Test
    public void getBalance() {
    }
}