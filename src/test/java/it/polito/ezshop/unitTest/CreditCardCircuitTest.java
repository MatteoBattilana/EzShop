package it.polito.ezshop.unitTest;

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
    public void testNegativeValidity() {
        CreditCardCircuit card = new CreditCardCircuit(sFILE);
        assertFalse(card.isValid("4485370086510890"));
    }
    @Test
    public void testInvalidValidity() {
        CreditCardCircuit card = new CreditCardCircuit(sFILE);
        assertFalse(card.isValid(""));
    }
    @Test
    public void testNullValidity() {
        CreditCardCircuit card = new CreditCardCircuit(sFILE);
        assertFalse(card.isValid(null));
    }

    @Test
    public void testPositiveValidity() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.isValid("4485370086510891"));
    }

    @Test
    public void testPositiveValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.validateCreditCard("4485370086510891"));
    }

    @Test
    public void testNegativeValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.validateCreditCard(null));
    }

    @Test
    public void testPositivePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.pay("4485370086510891",50.50));

    }

    @Test
    public void testNegativePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.pay("4485370086510891",200));
    }

    @Test
    public void testInvalidPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertFalse(card.pay(null,60));
    }

    @Test
    public void testInvalidMoneyPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit(sFILE);
        assertTrue(card.pay("4485370086510891",-58.2));
    }

    @Test
    public void testConstructorNullFile(){
        new CreditCardCircuit("");
    }

    @Test
    public void testConstructorZeroLoop() {
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
    public void testConstructorOneLoop() {
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
    public void testValidateCreditCardZeroLoop() {
        CreditCardCircuit creditCardCircuit = new CreditCardCircuit(sFILE);
        assertFalse(creditCardCircuit.validateCreditCard(""));
    }

    @Test
    public void testValidateCreditCardOneLoop() {
        CreditCardCircuit creditCardCircuit = new CreditCardCircuit(sFILE);
        assertFalse(creditCardCircuit.validateCreditCard("1"));
    }
}

