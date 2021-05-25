package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DatabaseConnection;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.data.UserImpl;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StupidTest {

    @Test
    public void test() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        EZShop ezShop = new EZShop();
        ezShop = new EZShop();
        ezShop = new EZShop();
        ezShop.createUser("a", "b", "Administrator");;
        ezShop.createUser("a", "b", "Administrator");;
        ezShop = new EZShop();
        ezShop = new EZShop();;
        ezShop = new EZShop();
        ezShop = new EZShop();
        assertNotNull(ezShop.login("a", "b"));
    }
}
