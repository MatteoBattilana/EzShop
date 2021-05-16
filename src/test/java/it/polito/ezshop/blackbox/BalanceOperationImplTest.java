package it.polito.ezshop.blackbox;




import it.polito.ezshop.data.BalanceOperationImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;
import java.time.LocalDate;




import static org.junit.Assert.*;

public class BalanceOperationImplTest {


    @Test
    public void NegativeSetBalanceId(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setBalanceId(-5);
        assertEquals(5,balance.getBalanceId());
        // boundary
        balance.setBalanceId(0);
        assertEquals(5,balance.getBalanceId());

    }

    @Test
    public void PositiveSetBalanceId(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setBalanceId(15);
        assertEquals(15,balance.getBalanceId());

    }
    @Test
    public void NullSetDate(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        LocalDate date= balance.getDate();
        balance.setDate(null);
        assertEquals(date,balance.getDate());

    }

    @Test
    public void PositiveSetDate(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");

        balance.setDate(LocalDate.of(2022,10,12));
        assertEquals(LocalDate.of(2022,10,12),balance.getDate());

    }

    @Test
    public void NullSetType(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String type= balance.getType();
        balance.setType(null);
        assertEquals(type, balance.getType());

    }

    @Test
    public void InvalidSetType(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String type= balance.getType();
        balance.setType(" ");
        assertEquals(type, balance.getType());

    }
    @Test
    public void PositiveSetType(){

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
    public void NullSetStatus(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String status= balance.getStatus();
        balance.setType(null);
        assertEquals(status, balance.getStatus());

    }

    @Test
    public void InvalidSetStatus(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        String status= balance.getStatus();
        balance.setType(" ");
        assertEquals(status, balance.getStatus());


    }
    @Test
    public void PositiveSetStatus(){
        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), 0.0, "SALE", "UNPAID");
        balance.setStatus("UNPAID");
        assertEquals("UNPAID", balance.getStatus());
        balance.setStatus("PAID");
        assertEquals("PAID", balance.getStatus());


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

}

