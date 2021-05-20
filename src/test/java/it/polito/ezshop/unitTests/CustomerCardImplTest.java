package it.polito.ezshop.unitTests;

import it.polito.ezshop.data.CustomerCardImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerCardImplTest {

    @Test
    public void testPositiveSetCustomer(){
        CustomerCardImpl card = new CustomerCardImpl("Cliente009", 0);
        card.setCustomer("Cliente002");
        assertEquals("Cliente002",card.getCustomer());
    }
    @Test
    public void testEmptySetCustomer(){
        CustomerCardImpl card = new CustomerCardImpl("Cliente009", 0);
        card.setCustomer("");
        assertEquals("Cliente009",card.getCustomer());
    }
    @Test
    public void testNullSetCustomer(){
        CustomerCardImpl card = new CustomerCardImpl("Cliente009", 0);
        card.setCustomer(null);
        assertEquals("Cliente009",card.getCustomer());
    }
    @Test
    public void testInvalidSetCustomer(){
        CustomerCardImpl card = new CustomerCardImpl(null, 0);
        card.setCustomer("Cliente002");
        assertEquals(null,card.getCustomer());
    }

    @Test
    public void testInvalidSetCardPoint() {
        CustomerCardImpl card = new CustomerCardImpl("cartacliente",2 );
        card.setPoints(-5);
        assertEquals(2, card.getPoints(), 1);
        //boundary
        card.setPoints(-1);
        assertEquals(2, card.getPoints(), 1);
    }
    @Test
    public void testPositiveSetCardPoint() {
        CustomerCardImpl card = new CustomerCardImpl("cartacliente",2 );
        card.setPoints(23);
        assertEquals(23,card.getPoints(),1);
        //boundary
        card.setPoints(0);
        assertEquals(0,card.getPoints(),1);
        card.setPoints(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE,card.getPoints(),1);

    }

    @Test
    public void testModifyPointsOnCard(){
        CustomerCardImpl card = new CustomerCardImpl("cartacliente", 0);

        assertTrue(card.modifyPointsOnCard(Integer.MIN_VALUE));
        assertTrue(card.modifyPointsOnCard(0));
        assertTrue(card.modifyPointsOnCard(Integer.MAX_VALUE));

    }
}

