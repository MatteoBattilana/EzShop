package it.polito.ezshop.unitTests;

import it.polito.ezshop.data.OrderImpl;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class OrderImplTest {

    @Test
    public void testGetMoney(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        assertEquals(-1.0*10, order.getMoney(), 0.1);
    }

    @Test
    public void testGetArrivalDate(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.getDateArrival();
    }

    @Test
    public void testRecordOrderArrival(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.recordOrderArrival();
        assertEquals("COMPLETED", order.getOrderStatus());
    }

    @Test
    public void testSetProductCode(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.setProductCode("01234567890128");
        assertEquals("01234567890128", order.getProductCode());
        order.setProductCode(null);
        assertEquals("01234567890128", order.getProductCode());
    }

    @Test
    public void testSetPricePerUnit(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.setPricePerUnit(0.6);
        assertEquals(0.6, order.getPricePerUnit(), 0.1);
        order.setPricePerUnit(-0.6);
        assertEquals(0.6, order.getPricePerUnit(), 0.1);
    }

    @Test
    public void testSetOrderStatus(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2022,10,12), "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.setOrderStatus("COMPLETED");
        assertEquals("COMPLETED", order.getOrderStatus());
        order.setOrderStatus("PAYED");
        assertEquals("PAYED", order.getOrderStatus());
        order.setOrderStatus("ISSUED");
        assertEquals("ISSUED", order.getOrderStatus());

        order.setOrderStatus(null);
        assertEquals("ISSUED", order.getOrderStatus());
        order.setOrderStatus("wrong");
        assertEquals("ISSUED", order.getOrderStatus());
    }

    @Test
    public void testSetQuantity(){
        OrderImpl order = new OrderImpl(5, "1234567890128", 1.0, 10, "SALE", "UNPAID");
        order.setQuantity(10);
        assertEquals(10, order.getQuantity());
        order.setQuantity(-10);
        assertEquals(10, order.getQuantity());
        order.setQuantity(0);
        assertEquals(10, order.getQuantity());
    }
}

