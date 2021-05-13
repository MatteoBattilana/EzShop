package it.polito.ezshop.blackbox;

import it.polito.ezshop.data.CustomerImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;





import static org.junit.Assert.*;
public class CustomerTest {


    @Test
    public void InvalidSetCustomerName(){

        CustomerImpl customer= new CustomerImpl(34,"sara");
        assertThrows(NullPointerException.class, () -> {customer.setCustomerName(""); } );

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
        assertThrows(java.time.DateTimeException.class, () -> {customer.setCustomerName(null); } );


    }
    @Test
    public void NegativeSetCustomerId(){
        CustomerImpl customer= new CustomerImpl(34,"Sara");
        customer.setId(-5);
        assertEquals(-1,customer.getId());
        // boundary
        customer.setId(-5);
        assertEquals(-1,customer.getId());

    }

    @Test
    public void PositiveSetCustomerId(){

        CustomerImpl customer= new CustomerImpl(34,"Sara");
        customer.setId(15);
        assertEquals(15,customer.getId());

    }


}
