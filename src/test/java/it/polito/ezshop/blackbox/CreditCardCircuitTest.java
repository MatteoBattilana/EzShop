package it.polito.ezshop.blackbox;


import it.polito.ezshop.data.CreditCardCircuit;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardCircuitTest {


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

