package it.polito.ezshop.unitBBTest;

import it.polito.ezshop.data.CreditCardCircuit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class CreditCardCircuitTest {
    private static final String sFILE = "tempFile.txt";

    @Before
    public void setup() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sFILE));
        writer.write("#Do not delete the lines preceded by an \"#\" and do not modify the first three credit cards\n" +
                "#since they will be used in the acceptance tests.\n" +
                "#The lines preceded by an \"#\" must be ignored.\n" +
                "#Here you can add all the credit card numbers you need with their balance. The format MUST be :\n" +
                "#<creditCardNumber>;<balance>\n" +
                "4485370086510891;150.00\n" +
                "5100293991053009;10.00\n" +
                "4716258050958645;0.00\n");
        writer.close();
    }

    @After
    public void end() {
        File tempTile = new File(sFILE);
        tempTile.delete();
    }

    @Test
    public void NegativeValidity() {
        CreditCardCircuit card = new CreditCardCircuit(sFILE);
        assertFalse(card.isValid("4485370086510890"));
    }

    @Test
    public void PositiveValidity() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.isValid("4485370086510891"));
    }

    @Test
    public void PositiveValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.validateCreditCard("4485370086510891"));
    }

    @Test
    public void NegativeValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.validateCreditCard(null));
    }

    @Test
    public void PositivePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.pay("4485370086510891",50.50));

    }

    @Test
    public void NegativePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.pay("4485370086510891",200));
    }

    @Test
    public void InvalidPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.pay(null,60));
    }

    @Test
    public void InvalidMoneyPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.pay("4485370086510891",-58.2));
    }

    @Test
    public void constructorZeroLoop() {
        try {
            FileWriter f2 = new FileWriter(sFILE, false);
            f2.write("");
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new CreditCardCircuit(sFILE);
    }

    @Test
    public void constructorOneLoop() {
        try {
            FileWriter f2 = new FileWriter(sFILE, false);
            f2.write("4485370086510891;11");
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new CreditCardCircuit(sFILE);
    }

    @Test
    public void ValidateCreditCardOneLoop() {
        CreditCardCircuit creditCardCircuit = new CreditCardCircuit(sFILE);
        assertFalse(creditCardCircuit.validateCreditCard("1"));
    }
}

