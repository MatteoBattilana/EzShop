package it.polito.ezshop.unitBBTest;

import it.polito.ezshop.data.CreditCardCircuit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class CreditCardCircuitTest {
    private static final String sFILE = "src/main/java/it/polito/ezshop/utils/CreditCards.txt";
    private String fileContent = "";

    @Before
    public void setup() {
        try (BufferedReader br = new BufferedReader(new FileReader(sFILE))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileContent = sb.toString();
        } catch (IOException e) {
            fail();
        }
    }

    @After
    public void end() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sFILE));
        writer.write(fileContent);
        writer.close();
    }

    @Test
    public void NegativeValidity() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertFalse(card.isValid("4485370086510890"));
    }

    @Test
    public void PositiveValidity() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertTrue(card.isValid("4485370086510891"));
    }

    @Test
    public void PositiveValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertTrue(card.validateCreditCard("4485370086510891"));
    }

    @Test
    public void NegativeValidateCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertFalse(card.validateCreditCard(null));
    }

    @Test
    public void PositivePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertTrue(card.pay("4485370086510891",50.50));

    }

    @Test
    public void NegativePayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertFalse(card.pay("4485370086510891",200));
    }

    @Test
    public void InvalidPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertFalse(card.pay(null,60));
    }

    @Test
    public void InvalidMoneyPayCreditCard() {
        CreditCardCircuit card =new CreditCardCircuit();
        assertFalse(card.pay("4485370086510891",-58.2));
    }
}

