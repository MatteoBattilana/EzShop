package it.polito.ezshop.unitTests;

import it.polito.ezshop.data.UserImpl;
import org.junit.Test;


import static org.junit.Assert.*;

public class UserImplTest {


    @Test
    public void testInvalidSetUsername(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String username= user.getUsername();
        user.setUsername("");
        assertEquals(username, user.getUsername());

    }
    @Test
    public void testNullSetUsername() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String username= user.getUsername();
        user.setUsername(null);
        assertEquals(username, user.getUsername());
    }
    @Test
    public void testPositiveSetUsername(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setUsername("saraR");
        assertEquals("saraR", user.getUsername());




    }
    @Test
    public void testInvalidSetPassword(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String password= user.getPassword();
        user.setPassword("");
        assertEquals(password, user.getPassword());

    }
    @Test
    public void testNullSetPassword() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String password= user.getPassword();
        user.setPassword(null);
        assertEquals(password, user.getPassword());
    }
    @Test
    public void testPositiveSetPassword(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setPassword("passWORD");
        assertEquals("passWORD", user.getPassword());


    }

    @Test
    public void testNegativeSetId(){
        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        Integer id = user.getId();
        user.setId(-5);
        assertEquals(id,user.getId());
        // boundary
        user.setId(-1);
        assertEquals(id,user.getId());

    }

    @Test
    public void testPositiveSetId(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        Integer id = 5;
        user.setId(5);
        assertEquals(id,user.getId());
        // boundary
       user.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, user.getId() ,1);


    }
    @Test
    public void testInvalidExistenceSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole("");
        assertEquals(role, user.getRole());

    }
    @Test
    public void testNullSetRole() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole(null);
        assertEquals(role, user.getRole());
    }
    @Test
    public void testInvalidSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole("Manager");
        assertEquals(role, user.getRole());

    }
    @Test
    public void testPositiveSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setRole("ShopManager");
        assertEquals("ShopManager", user.getRole());
        user.setRole("Administrator");
        assertEquals("Administrator", user.getRole());
        user.setRole("Cashier");
        assertEquals("Cashier", user.getRole());



    }

}
