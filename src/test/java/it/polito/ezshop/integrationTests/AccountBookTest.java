package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.AccountBook;
import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.BalanceOperationImpl;
import it.polito.ezshop.data.DatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AccountBookTest {
    AccountBook accountBook;
    private DatabaseConnection database;

    @Before
    public void setup() {
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
        database = new DatabaseConnection();
        accountBook = new AccountBook(database);
    }

    @After
    public void cleanup() {
        database.closeConnection();
        File f = new File("src/main/java/it/polito/ezshop/utils/database.db");
        f.delete();
    }

    @Test
    public void testUnpaidBalanceOperation() {
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), "SALE", "UNPAID");
        accountBook.add(balanceOperation);

        List<BalanceOperation> creditAndDebits = accountBook.getCreditAndDebits(null, null);
        assertEquals(0, creditAndDebits.size());
    }

    @Test
    public void testPaidBalanceOperation() {
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 10.0, "SALE", "PAID");
        accountBook.add(balanceOperation);

        List<BalanceOperation> creditAndDebits = accountBook.getCreditAndDebits(null, null);
        assertEquals(1, creditAndDebits.size());
        assertEquals(10.0, creditAndDebits.get(0).getMoney(), 0.1);
    }

    @Test
    public void testNullBalanceOperation() {
        accountBook.add(null);
        assertEquals(0, accountBook.getCreditAndDebits(null, null).size());
    }

    @Test
    public void testUpdatedBalance(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 10.0, "RETURN", "PAID");
        accountBook.add(balanceOperation);
        List<BalanceOperation> creditAndDebits = accountBook.getCreditAndDebits(null, null);
        assertTrue(accountBook.recordBalanceUpdate(10));
    }

    @Test
    public void testNotUpdatedBalance(){
        //balance = 0;
        assertFalse(accountBook.recordBalanceUpdate(-10));
    }


    @Test
    public void testComputeBalance(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 10.0, "SALE", "PAID");
        accountBook.add(balanceOperation);
        accountBook.recordBalanceUpdate(10);
        accountBook.recordBalanceUpdate(10);
        Double balance = accountBook.computeBalance();
        assertEquals(20, balance, 1);
    }


    @Test
    public void testOpListReset(){
        BalanceOperationImpl balanceOperation = new BalanceOperationImpl(1, LocalDate.now(), 10.0, "SALE", "PAID");
        accountBook.add(balanceOperation);
        accountBook.recordBalanceUpdate(100);
        accountBook.reset();
        List<BalanceOperation> creditAndDebits = accountBook.getCreditAndDebits(null, null);
        assertEquals(0,creditAndDebits.size());
        assertEquals(0,accountBook.computeBalance(),0.1);
    }

    @Test
    public void testNullOpListReset(){
        accountBook.add(null);
        accountBook.reset();
        List<BalanceOperation> creditAndDebits = accountBook.getCreditAndDebits(null, null);
        assertEquals(0,creditAndDebits.size());
    }
}
