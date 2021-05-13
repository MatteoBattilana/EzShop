package it.polito.ezshop.blackbox;




import it.polito.ezshop.data.BalanceOperationImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;
import java.time.LocalDate;




import static org.junit.Assert.*;

public class BalanceOperationTest {


    @Test
    public void NegativeSetBalanceId(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), "order", "done");

        balance.setBalanceId(-5);
        assertEquals(-1,balance.getBalanceId());
        // boundary
        balance.setBalanceId(0);
        assertEquals(-1,balance.getBalanceId());

    }

    @Test
    public void PositiveSetBalanceId(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), "order", "done");

        balance.setBalanceId(15);
        assertEquals(15,balance.getBalanceId());

    }


    @Test
    public void PositiveSetDate(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), "order", "done");

        balance.setDate(LocalDate.of(2022,10,12));
        assertEquals(LocalDate.of(2022,10,12),balance.getDate());

    }
    @Test
    public void NullSetDate(){

        BalanceOperationImpl balance = new BalanceOperationImpl(5,LocalDate.of(2022,10,12), "order", "done");

        assertThrows(java.time.DateTimeException.class, () -> {balance.setDate(LocalDate.of(00,00,00)); } );


    }
}
