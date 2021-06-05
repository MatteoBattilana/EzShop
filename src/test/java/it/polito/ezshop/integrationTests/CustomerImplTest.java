package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.CustomerCardImpl;
import org.junit.Test;
import it.polito.ezshop.data.CustomerImpl;

import static org.junit.Assert.*;
public class CustomerImplTest {
    @Test
    public void testNullSetCustomerName(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        String cusName = customer.getCustomerName();
        customer.setCustomerName(null);
        assertEquals(cusName, customer.getCustomerName());

    }

    @Test
    public void testInvalidSetCustomerName(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        String cusName = customer.getCustomerName();
        customer.setCustomerName("");
        assertEquals(cusName, customer.getCustomerName());
    }

    @Test
    public void testPositiveSetCustomerName(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        String name = "Kylie";
        customer.setCustomerName("Kylie");
        assertEquals(name, customer.getCustomerName());
    }

    @Test
    public void testNegativeSetId(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        Integer id = customer.getId();
        customer.setId(-5);
        assertEquals(id, customer.getId());

        //Boundary
        customer.setId(0);
        assertEquals(id,customer.getId());
    }

    @Test
    public void testPositiveSetId(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        Integer id = 15;
        customer.setId(15);
        assertEquals(id, customer.getId());

        customer.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, customer.getId(),1);
    }


    @Test
    public void testInvalidSetCustomerCard(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        customer.setCustomerCard(" ");
        assertEquals(" ", customer.getCustomerCard());
    }

    @Test
    public void testPositiveSetCustomerCard(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        String card = "00000000010";
        customer.setCustomerCard(card);
        assertEquals(card, customer.getCustomerCard());
        customer.setCustomerCard(card);
        assertEquals(card, customer.getCustomerCard());
    }

    @Test
    public void testNullSetCustomerCard(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        customer.setCustomerCard((String) null);
        assertEquals(null, customer.getCustomerCard());
    }

    @Test
    public void testPositiveSetCustomerImplCard(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        CustomerCardImpl card = new CustomerCardImpl("01234567890", 1);
        customer.setCustomerCard(card);
        assertEquals("01234567890", customer.getCustomerCard());
    }


    @Test
    public void testPositiveSetPoints(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        customer.setCustomerCard("12345678900");
        customer.setPoints(1);
        assertEquals(1, customer.getPoints(),1);

        //boundary
        customer.setPoints(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, customer.getPoints(),1);

        customer.setPoints(0);
        assertEquals(0, customer.getPoints(),1);

        customer.setPoints(-5);
        assertEquals(0,customer.getPoints(),1);

    }

    @Test
    public void testNegativeSetPoints(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        customer.setCustomerCard("12345678900");
        customer.setPoints(1);
        customer.setPoints(-5);
        assertEquals(1,customer.getPoints(),1);

    }

    @Test
    public void testNullSetPoints(){
        CustomerImpl customer = new CustomerImpl(34, "Sara");
        customer.setCustomerCard((String) null);
        assertEquals(null, customer.getPoints());

    }


}
