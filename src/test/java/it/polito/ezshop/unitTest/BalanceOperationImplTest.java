package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.BalanceOperationImpl;
import org.junit.Test;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class BalanceOperationImplTest {

    @Test
    public void testNegativeSetBalanceId(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setBalanceId(-5);
        assertEquals(5,balance.getBalanceId());
        // boundary
        balance.setBalanceId(0);
        assertEquals(5,balance.getBalanceId());
    }

    @Test
    public void testPositiveSetBalanceId(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setBalanceId(15);
        assertEquals(15,balance.getBalanceId());
        // boundary
        balance.setBalanceId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, balance.getBalanceId() ,1);

    }

    @Test
    public void testNullSetDate(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        LocalDate date= balance.getDate();
        balance.setDate(null);
        assertEquals(date,balance.getDate());
    }

    @Test
    public void testPositiveSetDate(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setDate(LocalDate.of(2022,10,12));
        assertEquals(LocalDate.of(2022,10,12),balance.getDate());

    }

    @Test
    public void testNullSetType(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String type= balance.getType();
        balance.setType(null);
        assertEquals(type, balance.getType());
    }

    @Test
    public void testInvalidSetType(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String type= balance.getType();
        balance.setType(" ");
        assertEquals(type, balance.getType());
    }

    @Test
    public void testPositiveSetType(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        balance.setType("SALE");
        assertEquals("SALE", balance.getType());
        balance.setType("CREDIT");
        assertEquals("CREDIT", balance.getType());
        balance.setType("DEBIT");
        assertEquals("DEBIT", balance.getType());
        balance.setType("ORDER");
        assertEquals("ORDER", balance.getType());
        balance.setType("RETURN");
        assertEquals("RETURN", balance.getType());
    }

    @Test
    public void testNullSetStatus(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String status= balance.getStatus();
        balance.setType(null);
        assertEquals(status, balance.getStatus());
    }

    @Test
    public void testInvalidSetStatus(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String status= balance.getStatus();
        balance.setType(" ");
        assertEquals(status, balance.getStatus());
    }


    @Test
    public void testPositiveSetStatus(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        balance.setStatus("UNPAID");
        assertEquals("UNPAID", balance.getStatus());
        balance.setStatus("PAID");
        assertEquals("PAID", balance.getStatus());
    }

    @Test
    public void testPositiveSetMoney(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setMoney(0.50);
        assertEquals(0.50, balanceOperation.getMoney(), 0.1);
        //boundary
        balanceOperation.setMoney(0.0001);
        assertEquals(0.0001, balanceOperation.getMoney(), 0.1);
        balanceOperation.setMoney(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, balanceOperation.getMoney(), 0.1);
    }
    @Test
    public void testNegativeSetMoney(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setMoney(-0.0001);
        assertEquals(-0.0001, balanceOperation.getMoney(), 0.1);
    }

    @Test
    public void testSecondConstructor(){
        BalanceOperationImpl balance = new BalanceOperationImpl(1, LocalDate.now(), "SALE", "UNPAID");
        assertEquals(0, balance.getMoney(), 0.1);
        assertEquals(1, balance.getBalanceId());
        assertEquals("SALE", balance.getType());
        assertEquals("UNPAID", balance.getStatus());
    }

    @Test
    public void testSetStatus(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setStatus("UNPAID");
        assertEquals("UNPAID", balanceOperation.getStatus());
        balanceOperation.setStatus("PAID");
        assertEquals("PAID", balanceOperation.getStatus());

        balanceOperation.setStatus("");
        assertEquals("PAID", balanceOperation.getStatus());
        balanceOperation.setStatus(null);
        assertEquals("PAID", balanceOperation.getStatus());
    }

}

