package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EZShopTest {
    private static final String sFILE = "src/main/java/it/polito/ezshop/utils/CreditCards.txt";
    EZShop ezShop;

    long startTime = 0;

    private void loginAs(String role){
        try {
            ezShop.createUser("username" + role, "password", role);
        }
        catch (Exception ignored) {}
        try {
            assertNotNull(ezShop.login("username" + role, "password"));
        } catch (Exception ignored) {
            fail();
        }

    }

    @Before
    public void setup(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(sFILE));
            writer.write("#Do not delete the lines preceded by an \"#\" and do not modify the first three credit cards\n" +
                    "#since they will be used in the acceptance tests.\n" +
                    "#The lines preceded by an \"#\" must be ignored.\n" +
                    "#Here you can add all the credit card numbers you need with their balance. The format MUST be :\n" +
                    "#<creditCardNumber>;<balance>\n" +
                    "4485370086510891;150.00\n" +
                    "5100293991053009;10.00\n" +
                    "4716258050958645;0.00\n");
            writer.close();
        }
        catch (Exception ex) {
            fail();
        }

        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();

        ezShop = new EZShop();
        startTime = System.currentTimeMillis();
    }

    @After
    public void end() {
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + " ms");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(sFILE));
            writer.write("#Do not delete the lines preceded by an \"#\" and do not modify the first three credit cards\n" +
                    "#since they will be used in the acceptance tests.\n" +
                    "#The lines preceded by an \"#\" must be ignored.\n" +
                    "#Here you can add all the credit card numbers you need with their balance. The format MUST be :\n" +
                    "#<creditCardNumber>;<balance>\n" +
                    "4485370086510891;150.00\n" +
                    "5100293991053009;10.00\n" +
                    "4716258050958645;0.00\n");
            writer.close();
        }
        catch (Exception ex) {
            fail();
        }
        ezShop.close();
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
    }

    @Test
    public void testCreateProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("description", "1234567890128", 1.99, "note");
        assertNotNull(productType);
        assertTrue(productType > 0);

        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("1234567890128");
        assertNotNull(productTypeByBarCode);
        assertEquals("description", productTypeByBarCode.getProductDescription());
        assertEquals(1.99, productTypeByBarCode.getPricePerUnit(), 0.1);
        assertEquals("note", productTypeByBarCode.getNote());

        Integer productType2 = ezShop.createProductType("description", "1234567890128", 1.99, "note");
        assertEquals(-1, productType2.intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testNotLoggedCreateProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.createProductType("description", "1234567890128", 1.99, "note");
    }

    @Test
    public void testWrongParametersCreateProduct(){
        loginAs("ShopManager");
        try {
            ezShop.createProductType(null, "1234567890128", 1.99, "note");
            fail();
        } catch (InvalidProductDescriptionException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createProductType("", "1234567890128", 1.99, "note");
            fail();
        } catch (InvalidProductDescriptionException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.createProductType("description", null, 1.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createProductType("description", "", 1.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createProductType("description", "143256787652", 1.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createProductType("description", "1234567890128", -11.0, "note");
            fail();
        } catch (InvalidPricePerUnitException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testUpdateLocation() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer productTypeId = ezShop.createProductType("description", "1234567890128", 1.99, "note");
        Integer productTypeId2 = ezShop.createProductType("description1", "01234567890128", 1.98, "note");
        assertTrue(ezShop.updatePosition(productTypeId, "1-3e-4"));
        assertFalse(ezShop.updatePosition(productTypeId2, "1-3e-4"));

        assertTrue(ezShop.updatePosition(productTypeId, ""));
        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("1234567890128");
        assertNull(null, productTypeByBarCode.getLocation());

        assertTrue(ezShop.updatePosition(productTypeId, null));
        productTypeByBarCode = ezShop.getProductTypeByBarCode("1234567890128");
        assertNull(null, productTypeByBarCode.getLocation());

        assertTrue(ezShop.updatePosition(productTypeId, "1-3e-4"));
        productTypeByBarCode = ezShop.getProductTypeByBarCode("1234567890128");
        assertEquals("1-3e-4", productTypeByBarCode.getLocation());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginUpdateLocation() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer productTypeId = ezShop.createProductType("description", "1234567890128", 1.99, "note");
        loginAs("Cashier");
        ezShop.updatePosition(productTypeId, "1-3e-4");
    }

    @Test
    public void testWrongParametersUpdateLocation() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer productTypeId = ezShop.createProductType("description", "1234567890128", 1.99, "note");

        try {
            ezShop.updatePosition(-1, "1-3e-4");
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updatePosition(null, "1-3e-4");
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
        assertFalse(ezShop.updatePosition(100, "1-3a-4"));

        try {
            ezShop.updatePosition(productTypeId, "1-8t-a");
            fail();
        } catch (InvalidLocationException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testCreateUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        Integer user = ezShop.createUser("username", "password", "Administrator");
        assertNotNull(user);
        assertTrue(user > 0);

        loginAs("Administrator");
        List<User> allUsers = ezShop.getAllUsers();
        assertTrue(allUsers.size() > 0);

        boolean found = false;
        for (User u : allUsers)
            if (u.getId().intValue() == user.intValue()) found = true;
        assertTrue(found);

        Integer secondUser = ezShop.createUser("username", "password", "Cashier");
        assertEquals(-1, secondUser.intValue());
    }

    @Test
    public void testWrongParametersCreateUser() {
        try {
            ezShop.createUser(null, "password", "Administrator");
            fail();
        } catch (InvalidUsernameException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createUser("", "password", "Administrator");
            fail();
        } catch (InvalidUsernameException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.createUser("username", null, "Administrator");
            fail();
        } catch (InvalidPasswordException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createUser("username", "", "Administrator");
            fail();
        } catch (InvalidPasswordException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.createUser("username", "password", "");
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createUser("username", "password", null);
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.createUser("username", "password", "InvalidRole");
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testDeleteUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");

        Integer user = ezShop.createUser("username", "password", "Cashier");
        assertTrue(ezShop.deleteUser(user));
        assertNull(ezShop.getUser(user));
        assertFalse(ezShop.deleteUser(user));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("ShopManager");

        Integer user = ezShop.createUser("username", "password", "Cashier");
        ezShop.deleteUser(user);
    }

    @Test
    public void testWrongParametersDeleteUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        try {
            ezShop.deleteUser(null);
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteUser(-1);
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testGetAllUsers() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        loginAs("Administrator");
        for (int i = 0; i < 10; i++){
            ezShop.createUser("username" + i, "p", "Cashier");
        }

        List<User> allUsers = ezShop.getAllUsers();
        assertTrue(allUsers.size() >= 10);
        for (int i = 0; i < 10; i++) {
            boolean found = false;
            for (User u: allUsers) {
                if (u.getUsername().equals("username" + i)) found = true;
            }
            assertTrue(found);
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetAllUsers() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        loginAs("ShopManager");
        for (int i = 0; i < 10; i++){
            ezShop.createUser("username" + i, "p", "Cashier");
        }
        ezShop.getAllUsers();
    }

    @Test
    public void testGetUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        Integer user = ezShop.createUser("username", "p", "Cashier");
        User user1 = ezShop.getUser(user);
        assertEquals("username", user1.getUsername());
        assertEquals("Cashier", user1.getRole());
        assertEquals("p", user1.getPassword());
        assertEquals(user.intValue(), user1.getId().intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        Integer user = ezShop.createUser("username", "p", "Cashier");
        loginAs("Cashier");
        ezShop.getUser(user);
    }

    @Test
    public void testUpdateUserRights() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        Integer user = ezShop.createUser("username", "p", "Cashier");
        assertTrue(ezShop.updateUserRights(user, "ShopManager"));
        User user1 = ezShop.getUser(user);
        assertEquals("ShopManager", user1.getRole());

        assertFalse(ezShop.updateUserRights(100, "ShopManager"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginUpdateUserRights() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        Integer user = ezShop.createUser("username", "p", "Cashier");
        loginAs("ShopManager");
        ezShop.updateUserRights(user, "ShopManager");
    }

    @Test
    public void testWrongParametersUpdateUserRights() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        loginAs("Administrator");
        Integer user = ezShop.createUser("username", "p", "Cashier");

        try {
            ezShop.updateUserRights(-1, "ShopManager");
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateUserRights(null, "ShopManager");
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.updateUserRights(user, "InvalidRole");
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateUserRights(user, null);
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateUserRights(user, "");
            fail();
        } catch (InvalidRoleException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testWrongParametersGetUser() {
        loginAs("Administrator");
        try {
            ezShop.getUser(-1);
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.getUser(null);
            fail();
        } catch (InvalidUserIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test (expected = InvalidUsernameException.class)
    public void testLoginUsernameNull() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login(null, "password");
    }

    @Test (expected = InvalidUsernameException.class)
    public void testLoginUsernameEmpty() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("", "password");
    }

    @Test (expected = InvalidPasswordException.class)
    public void testLoginPasswordNull() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("username", null);
    }

    @Test (expected = InvalidPasswordException.class)
    public void testLoginPasswordEmpty() throws InvalidPasswordException, InvalidUsernameException {
        ezShop.login("username", "");
    }

    @Test
    public void testLoginWrongPassword() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.createUser("username", "password", "Cashier");
        User login = ezShop.login("username", "wrongPassword");
        assertNull(login);
    }

    @Test
    public void testLogin() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.createUser("username", "password", "Cashier");
        User login = ezShop.login("username", "password");
        assertNotNull("The user has not been found", login);
    }

    @Test(expected = UnauthorizedException.class)
    public void testLogout() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException {
        ezShop.createUser("username", "password", "Cashier");
        ezShop.login("username", "password");
        ezShop.logout();
        ezShop.startSaleTransaction();
    }

    @Test
    public void testUpdateProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        assertTrue(ezShop.updateProduct(apple, "banana", "1234567890128", 2.99, "note"));

        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("1234567890128");
        assertNotNull(productTypeByBarCode);
        assertEquals("1234567890128", productTypeByBarCode.getBarCode());
        assertEquals("banana", productTypeByBarCode.getProductDescription());
        assertEquals(2.99, productTypeByBarCode.getPricePerUnit(), 0.1);
        assertEquals("note", productTypeByBarCode.getNote());

        assertFalse(ezShop.updateProduct(apple, "banana", "1234567890128", 2.99, "note"));
        assertFalse(ezShop.updateProduct(100, "banana", "01234567890128", 2.99, "note"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginUpdateProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        loginAs("Cashier");
        ezShop.updateProduct(apple, "banana", "1234567890128", 2.99, "note");
    }

    @Test
    public void testWrongParamentersUpdateProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);

        try {
            ezShop.updateProduct(-1, "banana", "1234567890128", 2.99, "note");
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateProduct(null, "banana", "1234567890128", 2.99, "note");
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.updateProduct(apple, null, "1234567890128", 2.99, "note");
            fail();
        } catch (InvalidProductDescriptionException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateProduct(apple, "", "1234567890128", 2.99, "note");
            fail();
        } catch (InvalidProductDescriptionException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.updateProduct(apple, "apple", null, 2.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateProduct(apple, "apple", "", 2.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateProduct(apple, "apple", "1234567890129", 2.99, "note");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.updateProduct(apple, "apple", "1234567890128", -2.99, "note");
            fail();
        } catch (InvalidPricePerUnitException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testDeleteProductType() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        assertTrue(ezShop.deleteProductType(apple));
        assertNull(ezShop.getProductTypeByBarCode("01234567890128"));
        assertFalse(ezShop.deleteProductType(apple));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteProductType() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        loginAs("Cashier");
        ezShop.deleteProductType(apple);
    }

    @Test
    public void testWrongParametersDeleteProductType() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        try {
            ezShop.deleteProductType(null);
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteProductType(-1);
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testGetAllProductTypes() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        assertEquals(0, ezShop.getAllProductTypes().size());
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer banana = ezShop.createProductType("banana", "1234567890128", 2.99, null);

        loginAs("Cashier");
        List<ProductType> allProductTypes = ezShop.getAllProductTypes();
        assertEquals(2, allProductTypes.size());
        for (ProductType pt: allProductTypes){
            if (pt.getId() != apple.intValue() && pt.getId() != banana.intValue())
                fail();
        }
    }

    @Test
    public void testGetProductTypeByBarCode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);

        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("01234567890128");
        assertNotNull(productTypeByBarCode);
        assertEquals("apple", productTypeByBarCode.getProductDescription());

        ProductType productTypeByBarCode2 = ezShop.getProductTypeByBarCode("1234567890128");
        assertNull(productTypeByBarCode2);
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetProductTypeByBarCode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        ezShop.createProductType("apple", "01234567890128", 1.99, null);

        loginAs("Cashier");
        ezShop.getProductTypeByBarCode("01234567890128");
    }

    @Test
    public void testWrongParameterGetProductTypeByBarCode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        try {
            ezShop.getProductTypeByBarCode(null);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.getProductTypeByBarCode("");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.getProductTypeByBarCode("1234567890129");
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testGetProductTypesByDescription() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer banana = ezShop.createProductType("banana", "1234567890128", 2.99, null);

        List<ProductType> all = ezShop.getProductTypesByDescription(null);
        assertEquals(2, all.size());
        for (ProductType pt: all){
            if (pt.getId() != apple.intValue() && pt.getId() != banana.intValue())
                fail();
        }

        List<ProductType> all2 = ezShop.getProductTypesByDescription("");
        assertEquals(2, all2.size());
        for (ProductType pt: all2){
            if (pt.getId() != apple.intValue() && pt.getId() != banana.intValue())
                fail();
        }

        List<ProductType> appleList = ezShop.getProductTypesByDescription("app");
        assertEquals(1, appleList.size());
        assertEquals("apple", appleList.get(0).getProductDescription());

        List<ProductType> bananaList = ezShop.getProductTypesByDescription("b");
        assertEquals(1, bananaList.size());
        assertEquals("banana", bananaList.get(0).getProductDescription());

        List<ProductType> bananaList2 = ezShop.getProductTypesByDescription("banana");
        assertEquals(1, bananaList2.size());
        assertEquals("banana", bananaList2.get(0).getProductDescription());

        List<ProductType> nothinList = ezShop.getProductTypesByDescription("nothing");
        assertEquals(0, nothinList.size());
    }

    @Test
    public void testUpdateQuantity() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer banana = ezShop.createProductType("banana", "1234567890128", 2.99, null);

        ezShop.updatePosition(apple, "1-e-3");
        assertTrue(ezShop.updateQuantity(apple, 10));
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertTrue(ezShop.updateQuantity(apple, 5));
        assertEquals(15, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());

        assertFalse(ezShop.updateQuantity(100, 10));
        assertFalse(ezShop.updateQuantity(banana, 10));
        assertFalse(ezShop.updateQuantity(apple, -20));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginUpdateQuantity() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer banana = ezShop.createProductType("banana", "1234567890128", 2.99, null);
        ezShop.updatePosition(apple, "1-e-3");

        loginAs("Cashier");
        ezShop.updateQuantity(apple, 10);
    }

    @Test
    public void testWrongParametersUpdateQuantity() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer banana = ezShop.createProductType("banana", "1234567890128", 2.99, null);
        ezShop.updatePosition(apple, "1-e-3");

        try {
            ezShop.updateQuantity(null, 10);
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.updateQuantity(-1, 10);
            fail();
        } catch (InvalidProductIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testWrongParametersIssueOrder() {
        loginAs("ShopManager");

        try {
            ezShop.issueOrder("01234567890129", 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.issueOrder("", 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.issueOrder(null, 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.issueOrder("01234567890129", -1, 1.99);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.issueOrder("01234567890129", 10, -1.99);
            fail();
        } catch (InvalidPricePerUnitException ignored) {} catch (Exception ignored) {fail();}

    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginIssueOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);

        loginAs("Cashier");
        Integer integer = ezShop.issueOrder("01234567890128", 10, 1.99);
    }

    @Test
    public void testIssueOrder() throws UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        assertNotNull(orderId1);
        assertTrue(orderId1 > 0);
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("ISSUED", allOrders.get(0).getStatus());

        assertEquals(-1, ezShop.issueOrder("1234567890128", 10, 1.99).intValue());

        Integer orderId2 = ezShop.issueOrder("01234567890128", 10, 1.99);
        assertNotNull(orderId2);
        assertEquals(orderId1 + 1, orderId2.intValue());
    }

    @Test
    public void testPayOrderFor() throws UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException {
        loginAs("ShopManager");

        ezShop.recordBalanceUpdate(100.0);
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.payOrderFor("01234567890128", 10, 1.99);
        assertNotNull(orderId1);
        assertTrue(orderId1 > 0);
        assertEquals(100 - 10*1.99, ezShop.computeBalance(), 0.1);
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("PAYED", allOrders.get(0).getStatus());

        assertEquals(-1, ezShop.payOrderFor("1234567890128", 10, 1.99).intValue());

        Integer orderId2 = ezShop.payOrderFor("01234567890128", 10, 1.99);
        assertNotNull(orderId2);
        assertEquals(orderId1 + 1, orderId2.intValue());
    }

    @Test
    public void testWrongParametersPayOrderFor() throws UnauthorizedException {
        loginAs("ShopManager");
        ezShop.recordBalanceUpdate(100.0);

        try {
            ezShop.payOrderFor("01234567890129", 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.payOrderFor("", 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.payOrderFor(null, 10, 1.99);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.payOrderFor("01234567890129", -1, 1.99);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.payOrderFor("01234567890129", 10, -1.99);
            fail();
        } catch (InvalidPricePerUnitException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginPayOrderFor() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        ezShop.recordBalanceUpdate(100.0);

        loginAs("Cashier");
        Integer integer = ezShop.payOrderFor("01234567890128", 10, 1.99);
    }

    @Test
    public void testNegativeBalancePayOrderFor() throws UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidProductDescriptionException {
        loginAs("ShopManager");

        ezShop.recordBalanceUpdate(2.00);
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.payOrderFor("01234567890128", 10, 1.99);
        assertNotNull(orderId1);
        assertEquals(-1, orderId1.intValue());
        assertEquals(2.00, ezShop.computeBalance(), 0.1);
    }

    @Test
    public void testPayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        ezShop.recordBalanceUpdate(100);

        assertTrue(ezShop.payOrder(orderId1));
        assertEquals(100 - 10 * 1.99, ezShop.computeBalance(), 0.1);
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("PAYED", allOrders.get(0).getStatus());

        assertFalse(ezShop.payOrder(100));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginPayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        ezShop.recordBalanceUpdate(100);

        loginAs("Cashier");
        ezShop.payOrder(orderId1);
    }

    @Test
    public void testNegativeBalancePayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        ezShop.recordBalanceUpdate(1.99);

        assertFalse(ezShop.payOrder(orderId1));
        assertEquals(1.99, ezShop.computeBalance(), 0.1);
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("ISSUED", allOrders.get(0).getStatus());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLogicPayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        ezShop.recordBalanceUpdate(1.99);

        loginAs("Cashier");
        ezShop.payOrder(orderId1);
    }

    @Test
    public void testAlreadyPayedPayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        ezShop.recordBalanceUpdate(1.99);

        ezShop.payOrder(orderId1);
        assertFalse(ezShop.payOrder(orderId1));
    }

    @Test
    public void testWrongParametersPayOrder() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidOrderIdException {
        loginAs("ShopManager");
        ezShop.recordBalanceUpdate(1.99);

        try {
            ezShop.payOrder(null);
            fail();
        } catch (InvalidOrderIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.payOrder(-1);
            fail();
        } catch (InvalidOrderIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testRecordOrderArrival() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidOrderIdException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        ezShop.updatePosition(apple, "1-w-2");
        ezShop.recordBalanceUpdate(100);
        Integer orderId1 = ezShop.payOrderFor("01234567890128", 10, 1.99);

        assertTrue(ezShop.recordOrderArrival(orderId1));
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("COMPLETED", allOrders.get(0).getStatus());
        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("01234567890128");
        assertEquals(10, productTypeByBarCode.getQuantity().intValue());
    }

    @Test
    public void testLocationNotSetRecordOrderArrival() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidOrderIdException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        ezShop.recordBalanceUpdate(100);
        Integer orderId1 = ezShop.payOrderFor("01234567890128", 10, 1.99);

        try {
            ezShop.recordOrderArrival(orderId1);
        } catch (InvalidLocationException ignored) {} catch (Exception e) {fail();}
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("PAYED", allOrders.get(0).getStatus());
        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("01234567890128");
        assertEquals(0, productTypeByBarCode.getQuantity().intValue());
    }

    @Test
    public void testOrderNotPayedRecordOrderArrival() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidOrderIdException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        ezShop.recordBalanceUpdate(100);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);

        assertFalse(ezShop.recordOrderArrival(orderId1));
        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals("ISSUED", allOrders.get(0).getStatus());
        ProductType productTypeByBarCode = ezShop.getProductTypeByBarCode("01234567890128");
        assertEquals(0, productTypeByBarCode.getQuantity().intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginRecordOrderArrival() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidOrderIdException, InvalidProductIdException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        ezShop.recordBalanceUpdate(100);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);

        loginAs("Cashier");
        ezShop.recordOrderArrival(orderId1);
    }

    @Test
    public void testGetAllOrders() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        Integer orderId2 = ezShop.issueOrder("01234567890128", 10, 1.99);

        List<Order> allOrders = ezShop.getAllOrders();
        assertEquals(2, allOrders.size());
        for(Order o: allOrders){
            if(o.getBalanceId().intValue() != orderId1.intValue() && o.getBalanceId().intValue() != orderId2.intValue())
                fail();
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetAllOrders() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        loginAs("ShopManager");
        Integer apple = ezShop.createProductType("apple", "01234567890128", 1.99, null);
        Integer orderId1 = ezShop.issueOrder("01234567890128", 10, 1.99);
        Integer orderId2 = ezShop.issueOrder("01234567890128", 10, 1.99);

        loginAs("Cashier");
        ezShop.getAllOrders();
    }

    @Test
    public void testDefineCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("luca");
        Integer id3 = ezShop.defineCustomer("matteo");
        assertNotNull(id);
        assertTrue(id > 0);
        Customer customer = ezShop.getCustomer(id);
        assertEquals("luca", customer.getCustomerName());

        Integer id2 = ezShop.defineCustomer("luca");
        assertEquals(-1, id2.intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDefineCustomer() throws InvalidCustomerNameException, UnauthorizedException {
        ezShop.defineCustomer("luca");
    }

    @Test
    public void testWrongParametersDefineCustomer() {
        loginAs("Cashier");
        try {
            ezShop.defineCustomer("");
            fail();
        } catch (InvalidCustomerNameException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.defineCustomer(null);
            fail();
        } catch (InvalidCustomerNameException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testModifyCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        String card1 = ezShop.createCard();
        String card = ezShop.createCard();
        assertTrue(card1.compareTo(card) < 0);
        assertTrue(ezShop.modifyCustomer(id, "luca", card));
        Customer customer = ezShop.getCustomer(id);
        assertNotNull(customer);
        assertEquals("luca", customer.getCustomerName());
        assertEquals(card, customer.getCustomerCard());

        assertFalse(ezShop.modifyCustomer(100, "matteo", null));
    }

    @Test
    public void testCardNotPresentModifyCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        assertFalse(ezShop.modifyCustomer(id, "luca", "0000000001"));
    }

    @Test
    public void testDeleteCardModifyCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        assertTrue(ezShop.modifyCustomer(id, "luca", ""));
        Customer customer = ezShop.getCustomer(id);
        assertNotNull(customer);
        assertEquals("luca", customer.getCustomerName());
        assertNull(customer.getCustomerCard());
    }

    @Test
    public void testDeleteCardModifyCustomer2() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");
        String card = ezShop.createCard();
        assertTrue(ezShop.modifyCustomer(id, "luca", card));

        assertTrue(ezShop.modifyCustomer(id, "luca", null));
        Customer customer = ezShop.getCustomer(id);
        assertNotNull(customer);
        assertEquals("luca", customer.getCustomerName());
        assertEquals(card, customer.getCustomerCard());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginModifyCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        ezShop.logout();
        ezShop.modifyCustomer(id, "luca", null);
    }

    @Test
    public void testWrongParametersModifyCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        try {
            ezShop.modifyCustomer(-1, "luca", null);
            fail();
        } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.modifyCustomer(id, "", null);
            fail();
        } catch (InvalidCustomerNameException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.modifyCustomer(id, null, null);
            fail();
        } catch (InvalidCustomerNameException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.modifyCustomer(id, "marco", "00000001");
            fail();
        } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testDeleteCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        assertTrue(ezShop.deleteCustomer(id));
        assertFalse(ezShop.deleteCustomer(id));
        assertFalse(ezShop.deleteCustomer(100));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        ezShop.logout();
        ezShop.deleteCustomer(id);
    }

    @Test
    public void testWrongParametersDeleteCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        try {
            ezShop.deleteCustomer(-1);
            fail();
        } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteCustomer(null);
            fail();
        } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testGetCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        Customer customer = ezShop.getCustomer(id);
        assertNotNull(customer);
        assertEquals("lucas", customer.getCustomerName());

        assertNull(ezShop.getCustomer(10));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetCustomer() throws UnauthorizedException, InvalidCustomerIdException {
        ezShop.getCustomer(1);
    }

    @Test
    public void testWrongParametersGetCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        loginAs("Cashier");
        Integer id = ezShop.defineCustomer("lucas");

        Customer customer = ezShop.getCustomer(id);
        assertNotNull(customer);
        assertEquals("lucas", customer.getCustomerName());

        try {
            ezShop.getCustomer(-1);
            fail();
        } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.getCustomer(null);
            fail();
        } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testGetAllCustomers() throws InvalidCustomerNameException, UnauthorizedException {
        loginAs("ShopManager");
        Integer id = ezShop.defineCustomer("lucas");
        Integer id2 = ezShop.defineCustomer("matteo");

        List<Customer> allCustomers = ezShop.getAllCustomers();
        assertEquals(2, allCustomers.size());
        for(Customer o: allCustomers){
            if(o.getId().intValue() != id && o.getId().intValue() != id2)
                fail();
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetAllCustomers() throws UnauthorizedException {
        ezShop.getAllCustomers();
    }

    @Test
    public void testStartSaleTransaction() throws UnauthorizedException {
        loginAs("Cashier");
        Integer id1 = ezShop.startSaleTransaction();
        Integer id2 = ezShop.startSaleTransaction();

        assertNotEquals(-1, id1.intValue());
        assertEquals(id1 + 1, id2.intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginStartSaleTransaction() throws UnauthorizedException {
        ezShop.startSaleTransaction();
    }

    @Test
    public void testAddProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);

        Integer saleId = ezShop.startSaleTransaction();
        assertTrue(ezShop.addProductToSale(saleId, "01234567890128", 2));
        assertEquals(10 - 2, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertTrue(ezShop.addProductToSale(saleId, "01234567890128", 2));
        assertEquals(10 - 2 - 2, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());

        assertFalse(ezShop.addProductToSale(saleId, "1234567890128", 1));
        assertFalse(ezShop.addProductToSale(10, "01234567890128", 1));
        assertFalse(ezShop.addProductToSale(saleId, "01234567890128", 11));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginAddProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);

        ezShop.logout();
        ezShop.startSaleTransaction();
    }

    @Test
    public void testWrongParametersAddProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);

        Integer saleId = ezShop.startSaleTransaction();
        try {
            ezShop.addProductToSale(-1, "1234567890128", 1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.addProductToSale(null, "1234567890128", 1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.addProductToSale(saleId, null, 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.addProductToSale(saleId, "", 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.addProductToSale(saleId, "1221", 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.addProductToSale(saleId, "01234567890128", -1);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testDeleteProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException, InvalidPaymentException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());

        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 4);
        assertEquals(10 - 4, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertTrue(ezShop.deleteProductFromSale(saleId, "01234567890128", 1));
        assertEquals(10 - 4 + 1, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertTrue(ezShop.deleteProductFromSale(saleId, "01234567890128", 3));
        assertEquals(10 - 4 + 1 + 3, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());

        assertFalse(ezShop.deleteProductFromSale(saleId, "01234567890128", 1));
        assertFalse(ezShop.deleteProductFromSale(saleId, "1234567890128", 1));
        assertFalse(ezShop.deleteProductFromSale(100, "01234567890128", 1));

        // Not allow delete products
        ezShop.endSaleTransaction(saleId);
        assertFalse(ezShop.deleteProductFromSale(saleId, "01234567890128", 1));
        ezShop.receiveCashPayment(saleId, 100);
        assertFalse(ezShop.deleteProductFromSale(saleId, "01234567890128", 1));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 4);

        ezShop.logout();
        ezShop.deleteProductFromSale(saleId, "01234567890128", 2);
    }

    @Test
    public void testWrongParametersDeleteProductToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException, InvalidProductIdException, InvalidLocationException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 4);

        try {
            ezShop.deleteProductFromSale(-1, "01234567890128", 2);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteProductFromSale(null, "01234567890128", 2);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.deleteProductFromSale(saleId, "", 2);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteProductFromSale(saleId, null, 2);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteProductFromSale(saleId, "1234567890127", 2);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.deleteProductFromSale(saleId, "1234567890127", -2);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testApplyDiscountRateToProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        assertTrue(ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.10));
        assertFalse(ezShop.applyDiscountRateToProduct(saleId, "1234567890128", 0.10));
        assertFalse(ezShop.applyDiscountRateToProduct(100, "01234567890128", 0.10));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginApplyDiscountRateToProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        ezShop.logout();
        ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.10);
    }

    @Test
    public void testWrongParametersApplyDiscountRateToProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        try {
            ezShop.applyDiscountRateToProduct(-1, "01234567890128", 0.10);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.applyDiscountRateToProduct(null, "01234567890128", 0.10);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.applyDiscountRateToProduct(saleId, null, 0.10);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.applyDiscountRateToProduct(saleId, "", 0.10);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.applyDiscountRateToProduct(saleId, "1234567890127", 0.10);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.applyDiscountRateToProduct(saleId, "1234567890128", 1.0);
            fail();
        } catch (InvalidDiscountRateException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.applyDiscountRateToProduct(saleId, "1234567890128", -0.1);
            fail();
        } catch (InvalidDiscountRateException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testApplyDiscountRateToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidLocationException, InvalidDiscountRateException, InvalidPaymentException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        assertTrue(ezShop.applyDiscountRateToSale(saleId, 0.1));
        assertFalse(ezShop.applyDiscountRateToSale(100, 0.1));
        ezShop.endSaleTransaction(saleId);
        assertTrue(ezShop.applyDiscountRateToSale(saleId, 0.1));
        ezShop.receiveCashPayment(saleId, 100);
        assertFalse(ezShop.applyDiscountRateToSale(saleId, 0.1));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginApplyDiscountRateToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidLocationException, InvalidDiscountRateException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        ezShop.logout();
        ezShop.applyDiscountRateToSale(saleId, 0.1);
    }

    @Test
    public void testWrongParameterApplyDiscountRateToSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidLocationException, InvalidDiscountRateException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 1.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 2);

        try {
            ezShop.applyDiscountRateToProduct(-1, "1234567890128", 1.1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.applyDiscountRateToProduct(null, "1234567890128", 1.1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            assertTrue(ezShop.applyDiscountRateToSale(saleId, -0.1));
            fail();
        } catch (InvalidDiscountRateException ignored) {} catch (Exception ignored) {fail();}
        try {
            assertTrue(ezShop.applyDiscountRateToSale(saleId, 1.0));
            fail();
        } catch (InvalidDiscountRateException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testComputePointsForSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidTransactionIdException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 6.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 20);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 10);

        assertEquals(6, ezShop.computePointsForSale(saleId));
        assertEquals(-1, ezShop.computePointsForSale(100));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginComputePointsForSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidTransactionIdException {
        loginAs("ShopManager");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 6.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 20);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 10);

        ezShop.logout();
        ezShop.computePointsForSale(saleId);
    }

    @Test
    public void testWrongParametersComputePointsForSale() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidTransactionIdException {
        loginAs("ShopManager");

        try {
            ezShop.computePointsForSale(-1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.computePointsForSale(null);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testEndSaleTransaction() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        loginAs("ShopManager");
        double initialBalance = ezShop.computeBalance();
        Integer productType = ezShop.createProductType("apple", "01234567890128", 6.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 20);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 10);
        ezShop.applyDiscountRateToSale(saleId, 0.2);
        ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.1);

        assertTrue(ezShop.endSaleTransaction(saleId));
        assertFalse(ezShop.endSaleTransaction(saleId));
        assertFalse(ezShop.endSaleTransaction(100));
        assertFalse(ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.1));

        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        assertNotNull(saleTransaction);
        double priceWithoutSaleDiscount = (6.99 - 6.99 * 0.1) * 10;
        assertEquals(priceWithoutSaleDiscount - priceWithoutSaleDiscount * 0.2, saleTransaction.getPrice(), 0.1);

        assertEquals(initialBalance, ezShop.computeBalance(), 0.1);
        boolean find = false;
        for (BalanceOperation op: ezShop.getCreditsAndDebits(null, null))
            if (op.getType().equals("SALE")) find = true;
        assertFalse(find);

        ezShop.receiveCashPayment(saleId, 100);
        assertFalse(ezShop.endSaleTransaction(saleId));
        assertFalse(ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.1));
        assertFalse(ezShop.deleteSaleTransaction(saleId));
    }

    @Test (expected = UnauthorizedException.class)
    public void testWrongLoginEndSaleTransaction() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.endSaleTransaction(1);
    }

    @Test
    public void testWrongParametersEndSaleTransaction() {
        loginAs("ShopManager");
        try {
            assertTrue(ezShop.endSaleTransaction(null));
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            assertTrue(ezShop.endSaleTransaction(-1));
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testDeleteSaleTransaction() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("ShopManager");

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
        assertFalse(ezShop.deleteSaleTransaction(saleId));

        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        List<BalanceOperation> creditsAndDebits = ezShop.getCreditsAndDebits(null, null);

        assertEquals(initialBalance, ezShop.computeBalance(), 0.1);
        boolean find = false;
        for (BalanceOperation op: creditsAndDebits)
            if (op.getType().equals("SALE")) find = true;
        assertFalse(find);
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        ezShop.deleteSaleTransaction(1);
    }

    @Test
    public void testWrongParametersDeleteSaleTransaction() {
        loginAs("ShopManager");
        try {
            assertTrue(ezShop.endSaleTransaction(null));
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            assertTrue(ezShop.endSaleTransaction(-1));
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testNormalSale2() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
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
        double resto2 = ezShop.receiveCashPayment(100, 100);
        double resto3 = ezShop.receiveCashPayment(saleId, 0.99);
        double resto = ezShop.receiveCashPayment(saleId, 100);

        assertEquals(-1, resto2, 0.1);
        assertEquals(-1, resto3, 0.1);
        assertEquals(100.0-(5*1 + 2*2), resto, 0.1);

        assertEquals(5*1 + 2*2, ezShop.computeBalance(), 0.1);

        List<BalanceOperation> creditsAndDebits = ezShop.getCreditsAndDebits(null, null);
        boolean find = false;
        for (BalanceOperation op: creditsAndDebits)
            if (op.getType().equals("SALE") && op.getMoney() == 9.0) find = true;
        assertTrue(find);
    }

    @Test
    public void testGetSaleTransaction() throws UnauthorizedException, InvalidLocationException, InvalidProductIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException {
        loginAs("Administrator");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 6.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 20);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 10);

        loginAs("Cashier");
        assertNull(ezShop.getSaleTransaction(saleId));
        assertNull(ezShop.getSaleTransaction(100));
        ezShop.endSaleTransaction(saleId);
        assertNotNull(ezShop.getSaleTransaction(saleId));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetSaleTransaction() throws UnauthorizedException, InvalidLocationException, InvalidProductIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.getSaleTransaction(1);
    }

    @Test
    public void testWrongParametersGetSaleTransaction() throws UnauthorizedException, InvalidLocationException, InvalidProductIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidTransactionIdException {
        loginAs("Administrator");
        Integer productType = ezShop.createProductType("apple", "01234567890128", 6.99, "note");
        ezShop.updatePosition(productType, "1-3-2");
        ezShop.updateQuantity(productType, 20);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "01234567890128", 10);

        loginAs("Cashier");
        try {
            ezShop.getSaleTransaction(null);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.getSaleTransaction(-1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testStartReturnTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidDiscountRateException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.applyDiscountRateToProduct(saleId, "1234567890128", 0.2);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.1);
        ezShop.applyDiscountRateToSale(saleId, 0.5);

        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertEquals(-1, returnId.intValue());

        ezShop.endSaleTransaction(saleId);

        returnId = ezShop.startReturnTransaction(saleId);
        assertEquals(-1, returnId.intValue());

        assertFalse(ezShop.addProductToSale(saleId, "1234567890128", 1));
        ezShop.receiveCashPayment(saleId, 100);
        assertFalse(ezShop.addProductToSale(saleId, "1234567890128", 1));
        double initialBalance = ezShop.computeBalance();

        Integer invalidSaleId = ezShop.startReturnTransaction(100);
        assertEquals(-1, invalidSaleId.intValue());
        returnId = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId.intValue());
        assertFalse(ezShop.returnProduct(returnId, "1234567890128", 6));
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertFalse(ezShop.returnProduct(returnId, "01234567890128", 3));
        assertTrue(ezShop.returnProduct(returnId, "01234567890128", 2));

        assertEquals(-1, ezShop.returnCashPayment(returnId), 0.1);
        assertEquals(-1, ezShop.returnCashPayment(100), 0.1);

        loginAs("Cashier");
        assertTrue(ezShop.endReturnTransaction(returnId, true));

        loginAs("ShopManager");
        assertEquals(5+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5-2, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2-2, t.getAmount());
        }

        double tot = 2*(2-2*0.1) + 2*(1 - 1 * 0.2);
        assertEquals(tot - tot*0.5, ezShop.returnCashPayment(returnId), 0.1);
        assertEquals(-1, ezShop.returnCashPayment(returnId), 0.1);
        assertFalse(ezShop.deleteReturnTransaction(returnId));

        for (BalanceOperation t : ezShop.getCreditsAndDebits(null, null)){
            if(t.getType().equals("RETURN")) assertEquals(- (tot - tot*0.5), t.getMoney(), 0.1);
        }

        assertEquals(initialBalance - (tot - tot*0.5), ezShop.computeBalance(), 0.1);


        // Try to open another return transaction
        Integer return2 = ezShop.startReturnTransaction(saleId);
        assertTrue(return2 > 0 && returnId < return2);
        assertTrue(ezShop.returnProduct(return2, "1234567890128", 2));
        assertTrue(ezShop.endReturnTransaction(return2, true));
        assertEquals(7+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginStartReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        ezShop.startReturnTransaction(11);
    }

    @Test
    public void testWrongParametersStartReturnTransaction() {
        loginAs("Cashier");
        try {
            ezShop.startReturnTransaction(null);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.startReturnTransaction(-1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginReturnProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);

        Integer returnId = ezShop.startReturnTransaction(saleId);
        ezShop.logout();
        ezShop.returnProduct(returnId, "1234567890128", 1);
    }

    @Test
    public void testWrongParametersReturnProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);

        Integer returnId = ezShop.startReturnTransaction(saleId);
        try {
            ezShop.returnProduct(null, "1234567890128", 1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnProduct(-1, "1234567890128", 1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.returnProduct(returnId, null, 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnProduct(returnId, "", 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnProduct(returnId, "1234567890129", 1);
            fail();
        } catch (InvalidProductCodeException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.returnProduct(returnId, "1234567890129", 0);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnProduct(returnId, "1234567890129", -1);
            fail();
        } catch (InvalidQuantityException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testReturnTransactionDeleteBeforePay() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");

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

        assertTrue(ezShop.deleteReturnTransaction(returnId));
        assertFalse(ezShop.endReturnTransaction(returnId, false));
        assertFalse(ezShop.endReturnTransaction(returnId, true));
        assertEquals(initialBalance, ezShop.computeBalance(), 0.1);
        assertEquals(5, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(8, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2, t.getAmount());
        }
    }

    @Test
    public void testReturnTransactionCommitFalse() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");

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

        assertTrue(ezShop.endReturnTransaction(returnId, false));
        assertFalse(ezShop.endReturnTransaction(returnId, false));
        assertEquals(initialBalance, ezShop.computeBalance(), 0.1);
        assertEquals(5, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(8, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2, t.getAmount());
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginEndReturnTransaction() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);
        Integer returnId = ezShop.startReturnTransaction(saleId);
        ezShop.returnProduct(returnId, "1234567890128", 2);

        ezShop.logout();
        ezShop.endReturnTransaction(returnId, false);
    }

    @Test
    public void testCreateCard() throws UnauthorizedException{
        loginAs("Cashier");
        String card = ezShop.createCard();
        String card2 = ezShop.createCard();
        assertNotNull(card);
        assertNotNull(card2);
    }

    @Test(expected = UnauthorizedException.class)
    public void testNotLoggedCreateCard() throws UnauthorizedException{
        ezShop.createCard();
    }

    @Test
    public void testModifyPointsOnCard() throws InvalidCustomerCardException, UnauthorizedException, InvalidCustomerNameException, InvalidCustomerIdException {
        loginAs("Cashier");
        String card = ezShop.createCard();
        Integer customerId = ezShop.defineCustomer("matteo");
        ezShop.attachCardToCustomer(card, customerId);

        assertTrue(ezShop.modifyPointsOnCard(card, 10));
        assertEquals(10, ezShop.getCustomer(customerId).getPoints().intValue());

        assertTrue(ezShop.modifyPointsOnCard(card, -10));
        assertEquals(0, ezShop.getCustomer(customerId).getPoints().intValue());

        assertFalse(ezShop.modifyPointsOnCard(card, -10));
        assertEquals(0, ezShop.getCustomer(customerId).getPoints().intValue());

        assertFalse(ezShop.modifyPointsOnCard("0000000011", 10));
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginModifyPointsOnCard() throws InvalidCustomerCardException, UnauthorizedException{
       loginAs("Cashier");
       String card = ezShop.createCard();

       ezShop.logout();
       ezShop.modifyPointsOnCard(card,12);
    }

    @Test
    public void testWrongParametersModifyPointsOnCard() {
        loginAs("Cashier");

        try {
            ezShop.modifyPointsOnCard("",12);
            fail();
        } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.modifyPointsOnCard("121",12);
            fail();
        } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.modifyPointsOnCard(null,12);
            fail();
        } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException, InvalidCustomerNameException {
        loginAs("Cashier");
        String card = ezShop.createCard();
        String card1 = ezShop.createCard();
        Integer customerId = ezShop.defineCustomer("matteo");
        Integer customerId2 = ezShop.defineCustomer("luca");
        assertNull(ezShop.getCustomer(customerId).getCustomerCard());
        assertTrue(ezShop.attachCardToCustomer(card1, customerId));
        assertTrue(ezShop.attachCardToCustomer(card, customerId));
        assertEquals(0, ezShop.getCustomer(customerId).getPoints().intValue());

        assertFalse(ezShop.attachCardToCustomer(card, 100));
        assertFalse(ezShop.attachCardToCustomer(card, customerId2));
        assertFalse(ezShop.attachCardToCustomer("0000000010", customerId2));
    }


    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        loginAs("Cashier");
        String card = ezShop.createCard();
        ezShop.logout();
        ezShop.attachCardToCustomer(card,1);
    }

    @Test
        public void testWrongParametersAttachCardToCustomer() throws UnauthorizedException {
            loginAs("Cashier");
            String card= ezShop.createCard();

            try {
                ezShop.attachCardToCustomer(card,null);
                fail();
            } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}
            try {
                ezShop.attachCardToCustomer(card,-1);
                fail();
            } catch (InvalidCustomerIdException ignored) {} catch (Exception ignored) {fail();}

            try {
                ezShop.attachCardToCustomer(null,1);
                fail();
            } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
            try {
                ezShop.attachCardToCustomer("",1);
                fail();
            } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
            try {
                ezShop.attachCardToCustomer("cliente22299",1);
                fail();
            } catch (InvalidCustomerCardException ignored) {} catch (Exception ignored) {fail();}
        }

    @Test
    public void testWrongParametersEndReturnTransaction() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);
        Integer returnId = ezShop.startReturnTransaction(saleId);
        ezShop.returnProduct(returnId, "1234567890128", 2);

        try {
            ezShop.endReturnTransaction(-1, false);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.endReturnTransaction(null, false);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        ezShop.deleteReturnTransaction(1);
    }

    @Test
    public void testWrongParametersDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        loginAs("Administrator");
        assertFalse(ezShop.deleteReturnTransaction(100));
        try {
            ezShop.deleteReturnTransaction(-1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.deleteReturnTransaction(null);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginReceiveCashPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException {
        ezShop.receiveCashPayment(100, 100);
    }

    @Test
    public void testWrongParametersReceiveCashPayment() throws InvalidTransactionIdException, UnauthorizedException, InvalidPaymentException {
        loginAs("Administrator");
        try {
            ezShop.receiveCashPayment(-1, 0.99);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.receiveCashPayment(null, 0.99);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.receiveCashPayment(100, -0.1);
            fail();
        } catch (InvalidPaymentException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testReceiveCreditCardPayment() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidCreditCardException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        assertFalse(ezShop.receiveCreditCardPayment(saleId, "4716258050958645"));
        assertFalse(ezShop.receiveCreditCardPayment(100, "4485370086510891"));
        assertFalse(ezShop.receiveCreditCardPayment(saleId, "4507039484760770"));

        assertTrue(ezShop.receiveCreditCardPayment(saleId, "4485370086510891"));

        assertEquals(5*1, ezShop.computeBalance(), 0.1);
        List<BalanceOperation> creditsAndDebits = ezShop.getCreditsAndDebits(null, null);
        boolean find = false;
        for (BalanceOperation op: creditsAndDebits)
            if (op.getType().equals("SALE") && op.getMoney() == 5.0) find = true;
        assertTrue(find);
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginReceiveCreditCardPayment() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidCreditCardException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.logout();
        ezShop.receiveCreditCardPayment(saleId, "4485370086510891");
    }

    @Test
    public void testWrongParametersReceiveCreditCardPayment() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidCreditCardException {
        loginAs("Administrator");
        try {
            ezShop.receiveCreditCardPayment(-1, "4485370086510891");
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.receiveCreditCardPayment(null, "4485370086510891");
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.receiveCreditCardPayment(1, null);
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.receiveCreditCardPayment(1, "");
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.receiveCreditCardPayment(1, "3213213213");
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginReturnCashPayment() throws InvalidTransactionIdException, UnauthorizedException {
        ezShop.returnCashPayment(1);
    }

    @Test
    public void testWrongParametersReturnCashPayment() {
        loginAs("Administrator");
        try {
            ezShop.returnCashPayment(-1);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnCashPayment(null);
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testReturnCreditCardPayment() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidDiscountRateException, InvalidCreditCardException {
        loginAs("Administrator");

        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);

        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);
        double initialBalance = ezShop.computeBalance();
        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertEquals(-1, ezShop.returnCreditCardPayment(returnId, "4716258050958645"), 0.1);
        assertTrue(ezShop.endReturnTransaction(returnId, true));

        assertEquals(2*1, ezShop.returnCreditCardPayment(returnId, "4716258050958645"), 0.1);
        assertEquals(initialBalance - 2, ezShop.computeBalance(), 0.1);
        assertEquals(-1, ezShop.returnCreditCardPayment(returnId, "4716258050958645"), 0.1);
        assertEquals(-1, ezShop.returnCreditCardPayment(100, "4716258050958645"), 0.1);
        assertEquals(-1, ezShop.returnCreditCardPayment(100, "4507039484760770"), 0.1);

        for (BalanceOperation t : ezShop.getCreditsAndDebits(null, null)){
            if(t.getType().equals("RETURN")) assertEquals(-2, t.getMoney(), 0.1);
        }
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginReturnCreditCardPayment() throws UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {
        ezShop.returnCreditCardPayment(1, "4716258050958645");
    }

    @Test
    public void testWrongParametersReturnCreditCardPayment() {
        loginAs("Administrator");
        try {
            ezShop.returnCreditCardPayment(null, "4716258050958645");
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnCreditCardPayment(-1, "4716258050958645");
            fail();
        } catch (InvalidTransactionIdException ignored) {} catch (Exception ignored) {fail();}

        try {
            ezShop.returnCreditCardPayment(1, null);
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnCreditCardPayment(1, "");
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
        try {
            ezShop.returnCreditCardPayment(1, "a12213");
            fail();
        } catch (InvalidCreditCardException ignored) {} catch (Exception ignored) {fail();}
    }

    @Test
    public void testRecordBalanceUpdate() throws UnauthorizedException {
        loginAs("ShopManager");
        assertTrue(ezShop.recordBalanceUpdate(10));
        assertFalse(ezShop.recordBalanceUpdate(-11));
        assertTrue(ezShop.recordBalanceUpdate(-5));
        assertEquals(5, ezShop.computeBalance(), 0.1);
        assertEquals(2, ezShop.getCreditsAndDebits(null, null).size());
        for (BalanceOperation op : ezShop.getCreditsAndDebits(null, null)){
            if(op.getType().equals("CREDIT")){
                assertEquals(10, op.getMoney(), 0.1);
            } else {
                assertEquals(-5, op.getMoney(), 0.1);
            }
        }

        assertEquals(2, ezShop.getCreditsAndDebits(LocalDate.now().plusDays(1), LocalDate.of(2019, 01, 01)).size());
        assertEquals(2, ezShop.getCreditsAndDebits(LocalDate.of(2019, 01, 01), LocalDate.now().plusDays(1)).size());
        assertEquals(2, ezShop.getCreditsAndDebits(LocalDate.of(2019, 01, 01), null).size());
        assertEquals(2, ezShop.getCreditsAndDebits(null, LocalDate.now().plusDays(1)).size());
        assertEquals(0, ezShop.getCreditsAndDebits(LocalDate.now().plusDays(1), null).size());
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginRecordBalanceUpdate() throws UnauthorizedException {
        loginAs("Cashier");
        ezShop.recordBalanceUpdate(10);
    }

    @Test(expected = UnauthorizedException.class)
    public void testWrongLoginGetCreditsAndDebits() throws UnauthorizedException {
        loginAs("Cashier");
        ezShop.getCreditsAndDebits(null, null);
    }

    @Test
    public void testReset() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        loginAs("Administrator");
        // BALANCE UPDATE
        assertTrue(ezShop.recordBalanceUpdate(100));

        // SALE
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);

        // ORDER
        ezShop.payOrderFor("1234567890128", 10, 1.99);

        assertEquals(1, ezShop.getAllOrders().size());
        assertEquals(1, ezShop.getAllProductTypes().size());
        assertEquals(3, ezShop.getCreditsAndDebits(null, null).size());
        assertNotNull(ezShop.getSaleTransaction(saleId));

        // Try simulate on-off-on
        loginAs("Administrator");
        assertEquals(1, ezShop.getAllOrders().size());
        assertEquals(1, ezShop.getAllProductTypes().size());
        assertEquals(3, ezShop.getCreditsAndDebits(null, null).size());
        assertNotNull(ezShop.getSaleTransaction(saleId));

        ezShop.reset();
        assertEquals(0, ezShop.getAllOrders().size());
        assertEquals(0, ezShop.getAllProductTypes().size());
        assertEquals(0, ezShop.getCreditsAndDebits(null, null).size());
        assertNull(ezShop.getSaleTransaction(saleId));

        ezShop.close();
        ezShop = new EZShop();
        loginAs("Administrator");
        assertEquals(0, ezShop.getAllOrders().size());
        assertEquals(0, ezShop.getAllProductTypes().size());
        assertEquals(0, ezShop.getCreditsAndDebits(null, null).size());
        assertNull(ezShop.getSaleTransaction(saleId));
    }

    @Test
    public void testLoadFromDB() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidDiscountRateException {
        loginAs("Administrator");
        // BALANCE UPDATE
        assertTrue(ezShop.recordBalanceUpdate(100));

        // SALE and RETURN
        Integer apple = ezShop.createProductType("apple", "1234567890128", 1, "empty");
        ezShop.updatePosition(apple, "1-1-2");
        ezShop.updateQuantity(apple, 10);
        Integer banana = ezShop.createProductType("banana", "01234567890128", 2, "empty");
        ezShop.updatePosition(banana, "1-1-3");
        ezShop.updateQuantity(banana, 10);

        // Sale = 5 apple and 2 banana
        Integer saleId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(saleId, "1234567890128", 5);
        ezShop.applyDiscountRateToProduct(saleId, "1234567890128", 0.2);
        ezShop.addProductToSale(saleId, "01234567890128", 2);
        ezShop.applyDiscountRateToProduct(saleId, "01234567890128", 0.1);
        ezShop.applyDiscountRateToSale(saleId, 0.5);
        ezShop.endSaleTransaction(saleId);
        ezShop.receiveCashPayment(saleId, 100);
        double initialBalance = ezShop.computeBalance();

        Integer invalidSaleId = ezShop.startReturnTransaction(100);
        assertEquals(-1, invalidSaleId.intValue());
        Integer returnId = ezShop.startReturnTransaction(saleId);
        assertNotEquals(-1, returnId.intValue());
        assertFalse(ezShop.returnProduct(returnId, "1234567890128", 6));
        assertTrue(ezShop.returnProduct(returnId, "1234567890128", 2));
        assertFalse(ezShop.returnProduct(returnId, "01234567890128", 3));
        assertTrue(ezShop.returnProduct(returnId, "01234567890128", 2));

        assertEquals(-1, ezShop.returnCashPayment(returnId), 0.1);
        assertEquals(-1, ezShop.returnCashPayment(100), 0.1);
        assertTrue(ezShop.endReturnTransaction(returnId, true));

        assertEquals(5+2, ezShop.getProductTypeByBarCode("1234567890128").getQuantity().intValue());
        assertEquals(10, ezShop.getProductTypeByBarCode("01234567890128").getQuantity().intValue());
        SaleTransaction saleTransaction = ezShop.getSaleTransaction(saleId);
        for(TicketEntry t : saleTransaction.getEntries()) {
            if(t.getBarCode().equals("1234567890128")) assertEquals(5-2, t.getAmount());
            if(t.getBarCode().equals("01234567890128")) assertEquals(2-2, t.getAmount());
        }

        double tot = 2*(2-2*0.1) + 2*(1 - 1 * 0.2);
        assertEquals(tot - tot*0.5, ezShop.returnCashPayment(returnId), 0.1);
        assertEquals(-1, ezShop.returnCashPayment(returnId), 0.1);
        assertFalse(ezShop.deleteReturnTransaction(returnId));

        for (BalanceOperation t : ezShop.getCreditsAndDebits(null, null)){
            if(t.getType().equals("RETURN")) assertEquals(- (tot - tot*0.5), t.getMoney(), 0.1);
        }

        assertEquals(initialBalance - (tot - tot*0.5), ezShop.computeBalance(), 0.1);

        // ORDER
        ezShop.payOrderFor("1234567890128", 10, 1.99);

        assertEquals(1, ezShop.getAllOrders().size());
        assertEquals(2, ezShop.getAllProductTypes().size());
        assertEquals(4, ezShop.getCreditsAndDebits(null, null).size());
        assertNotNull(ezShop.getSaleTransaction(saleId));

        // Try simulate on-off-on

        ezShop.close();
        ezShop = new EZShop();
        loginAs("Administrator");
        assertEquals(1, ezShop.getAllOrders().size());
        assertEquals(2, ezShop.getAllProductTypes().size());
        assertEquals(4, ezShop.getCreditsAndDebits(null, null).size());
        assertNotNull(ezShop.getSaleTransaction(saleId));
    }
}
