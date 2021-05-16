package it.polito.ezshop.unitBBTest;

import it.polito.ezshop.data.CustomerCardImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerCardImplTest {

    @Test
    public void setCardId(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);
        card.setCustomer("Cliente002");
        assertEquals("Cliente002",card.getCustomer());
        card.setCustomer(null);
        assertNull(card.getCustomer());
        card.setCustomer("");
        assertEquals("",card.getCustomer());
    }

    @Test
    public void setCardPoint(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);
        card.setPoints(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE,card.getPoints(),1);
        card.setPoints(0);
        assertEquals(0,card.getPoints(),1);
        card.setPoints(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE,card.getPoints(),1);

    }

    @Test
    public void modifyPointsOnCard(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);

        assertTrue(card.modifyPointsOnCard(Integer.MIN_VALUE));
        assertTrue(card.modifyPointsOnCard(0));
        assertTrue(card.modifyPointsOnCard(Integer.MAX_VALUE));

    }
}

