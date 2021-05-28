package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.OrderImpl;
import it.polito.ezshop.data.OrderImplAdapter;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class OrderImplAdapterTest {

    @Test
    public void testPositiveBalanceId(){

        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
//        Integer balanceId = orderAdapter.getBalanceId();

        orderAdapter.setBalanceId(10);
        assertEquals(10, orderAdapter.getBalanceId(),1);

        orderAdapter.setBalanceId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, orderAdapter.getBalanceId(),1);

    }

    @Test
    public void testNegativeBalanceId(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
        Integer balanceId = orderAdapter.getBalanceId();

        orderAdapter.setBalanceId(-5);
        assertEquals(balanceId, orderAdapter.getBalanceId(),1);

        //boundary
        orderAdapter.setBalanceId(0);
        assertEquals(balanceId, orderAdapter.getBalanceId(),1);
    }


    @Test
    public void testSetProductCode() {
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
        orderAdapter.setProductCode("78515420");
        assertEquals("78515420", orderAdapter.getProductCode());

        orderAdapter.setProductCode(" ");
        assertEquals(" ", orderAdapter.getProductCode());

        orderAdapter.setProductCode(null);
        assertNotNull(orderAdapter.getProductCode());
    }



    @Test
    public void testSetPricePerUnit(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
        orderAdapter.setPricePerUnit(0.99);
        assertEquals(0.99, orderAdapter.getPricePerUnit(),1);

        orderAdapter.setPricePerUnit(-1.89);
        assertEquals(0.99,orderAdapter.getPricePerUnit(),1);


        orderAdapter.setPricePerUnit(0.00);
        assertEquals(0.00,orderAdapter.getPricePerUnit(),1);

        orderAdapter.setPricePerUnit(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, orderAdapter.getPricePerUnit(),1);

        orderAdapter.setPricePerUnit(Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, orderAdapter.getPricePerUnit(),1);
    }


    @Test
    public void testSetQuantity(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
//        Integer quantity = orderAdapter.getQuantity();

        orderAdapter.setQuantity(-5);
        assertEquals(2, orderAdapter.getQuantity());

        orderAdapter.setQuantity(-1);
        assertEquals(2, orderAdapter.getQuantity());

        orderAdapter.setQuantity(0);
        assertEquals(2, orderAdapter.getQuantity());

        orderAdapter.setQuantity(3);
        assertEquals(3, orderAdapter.getQuantity());

        orderAdapter.setQuantity(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, orderAdapter.getQuantity());

    }

    @Test
    public void testSetStatus(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
        String status = orderAdapter.getStatus();

        orderAdapter.setStatus("ORDER");
        assertEquals(status, orderAdapter.getStatus());


        orderAdapter.setStatus(null);
        assertEquals("ORDER", orderAdapter.getStatus());

        orderAdapter.setStatus("");
        assertEquals("ORDER", orderAdapter.getStatus());

    }

    @Test
    public void testNegativeSetOrderId(){
        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);
        Integer orderId = orderAdapter.getOrderId();

        //boundary(<=0, null)
        orderAdapter.setOrderId(0);
        assertEquals(orderId, orderAdapter.getOrderId());

        orderAdapter.setOrderId(-5);
        assertEquals(orderId, orderAdapter.getOrderId());

    }

    @Test
    public void testPositiveSetOrderId(){

        OrderImpl order = new OrderImpl(5, LocalDate.of(2021,5,17), "1234567890",0.55,2, "SALE", "ORDER");
        OrderImplAdapter orderAdapter = new OrderImplAdapter(order);

        orderAdapter.setOrderId(10);
        assertEquals(10, orderAdapter.getOrderId(),1);

        orderAdapter.setOrderId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE,orderAdapter.getOrderId(),1);
    }
}
