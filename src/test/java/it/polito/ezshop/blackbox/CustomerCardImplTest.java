package it.polito.ezshop.blackbox;


import it.polito.ezshop.data.CustomerCardImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;





import static org.junit.Assert.*;


public class CustomerCardImplTest {



    @Test
    public void setCardId(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);
        card.setId("Cliente002");
        assertEquals("Cliente002",card.getCardId());
        card.setId(null);
        assertEquals(null,card.getCardId());
        card.setId("");
        assertEquals("",card.getCardId());

    }
    public void setCardPoint(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);
        card.setCardPoints(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE,card.getCardPoints(),1);
        card.setCardPoints(0);
        assertEquals(0,card.getCardPoints(),1);
        card.setCardPoints(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE,card.getCardPoints(),1);

    }
}

