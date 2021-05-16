package it.polito.ezshop.unitBBTest;

import it.polito.ezshop.data.UserImpl;
import org.junit.Test;


import static org.junit.Assert.*;

public class UserImplTest {


    @Test
    public void InvalidSetUsername(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String username= user.getUsername();
        user.setUsername("");
        assertEquals(username, user.getUsername());

    }
    @Test
    public void NullSetUsername() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String username= user.getUsername();
        user.setUsername(null);
        assertEquals(username, user.getUsername());
    }
    @Test
    public void PositiveSetUsername(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setUsername("saraR");
        assertEquals("saraR", user.getUsername());




    }
    @Test
    public void InvalidSetPassword(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String password= user.getPassword();
        user.setPassword("");
        assertEquals(password, user.getPassword());

    }
    @Test
    public void NullSetPassword() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String password= user.getPassword();
        user.setPassword(null);
        assertEquals(password, user.getPassword());
    }
    @Test
    public void PositiveSetPassword(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setPassword("passWORD");
        assertEquals("passWORD", user.getPassword());


    }

    @Test
    public void NegativeSetId(){
        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        Integer id = user.getId();
        user.setId(-5);
        assertEquals(id,user.getId());
        // boundary
        user.setId(-1);
        assertEquals(id,user.getId());

    }

    @Test
    public void PositiveSetId(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        Integer id = 5;
        user.setId(5);
        assertEquals(id,user.getId());

    }
    @Test
    public void InvalidExistenceSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole("");
        assertEquals(role, user.getRole());

    }
    @Test
    public void NullSetRole() {

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole(null);
        assertEquals(role, user.getRole());
    }
    @Test
    public void InvalidSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        String role= user.getRole();
        user.setRole("Manager");
        assertEquals(role, user.getRole());

    }
    @Test
    public void PositiveSetRole(){

        UserImpl user= new UserImpl(34,"sara","password","Cashier");
        user.setRole("ShopManager");
        assertEquals("ShopManager", user.getRole());
        user.setRole("Administrator");
        assertEquals("Administrator", user.getRole());
        user.setRole("Cashier");
        assertEquals("Cashier", user.getRole());



    }

}
