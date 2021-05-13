package it.polito.ezshop.unitBB;

import it.polito.ezshop.data.BalanceOperationImpl;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class BalanceOperationImplTest {
    @Test
    public void setBalanceId(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setBalanceId(2);
        assertEquals(2, balanceOperation.getBalanceId());
        balanceOperation.setBalanceId(0);
        assertEquals(2, balanceOperation.getBalanceId());
        balanceOperation.setBalanceId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, balanceOperation.getBalanceId());
    }

    @Test
    public void setDate(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        LocalDate now = LocalDate.now();
        balanceOperation.setDate(now);
        assertEquals(now, balanceOperation.getDate());
        balanceOperation.setDate(null);
        assertEquals(now, balanceOperation.getDate());
    }

    @Test
    public void setMoney(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setMoney(-Double.MAX_VALUE);
        assertEquals(-Double.MAX_VALUE, balanceOperation.getMoney(), 0.1);
        balanceOperation.setMoney(0);
        assertEquals(0, balanceOperation.getMoney(), 0.1);
        balanceOperation.setMoney(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, balanceOperation.getMoney(), 0.1);
    }

    @Test
    public void setType(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 0.0, "SALE", "UNPAID");
        balanceOperation.setType("SALE");
        assertEquals("SALE", balanceOperation.getType());
        balanceOperation.setType("CREDIT");
        assertEquals("CREDIT", balanceOperation.getType());
        balanceOperation.setType("DEBIT");
        assertEquals("DEBIT", balanceOperation.getType());
        balanceOperation.setType("ORDER");
        assertEquals("ORDER", balanceOperation.getType());
        balanceOperation.setType("RETURN");
        assertEquals("RETURN", balanceOperation.getType());

        balanceOperation.setType("");
        assertEquals("RETURN", balanceOperation.getType());
        balanceOperation.setType(null);
        assertEquals("RETURN", balanceOperation.getType());
    }

    @Test
    public void setStatus(){
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