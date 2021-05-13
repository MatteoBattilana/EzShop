package it.polito.ezshop.blackbox;

import it.polito.ezshop.data.CustomerImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;





import static org.junit.Assert.*;
public class CustomerImplTest {


    @Test
    public void InvalidSetCustomerName(){

        CustomerImpl customer= new CustomerImpl(34,"Sara");
        String CustomerName = customer.getCustomerName();
        customer.setCustomerName("");
        assertEquals(CustomerName, customer.getCustomerName());

    }

    @Test
    public void PositiveSetCustomerName(){

        CustomerImpl customer= new CustomerImpl(34,"Sara");
        customer.setCustomerName("Chiara");
        assertEquals("Chiara",customer.getCustomerName());

    }

    @Test
    public void NullSetCustomerName(){
        CustomerImpl customer= new CustomerImpl(34,"Sara");
        String CustomerName = customer.getCustomerName();
        customer.setCustomerName(null);
        assertEquals(CustomerName, customer.getCustomerName());


    }
    @Test
    public void NegativeSetCustomerId(){
        CustomerImpl customer= new CustomerImpl(34,"Sara");
       Integer id = customer.getId();
        customer.setId(-5);
        assertEquals(id,customer.getId());
        // boundary
        customer.setId(0);
        assertEquals(id,customer.getId());

    }

    @Test
    public void PositiveSetCustomerId(){

        CustomerImpl customer= new CustomerImpl(34,"Sara");
        Integer id = 15;
        customer.setId(15);
        assertEquals(id,customer.getId());

    }


}
