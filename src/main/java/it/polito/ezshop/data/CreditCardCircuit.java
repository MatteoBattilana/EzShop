package it.polito.ezshop.data;

import java.util.HashMap;
import java.util.Map;

public class CreditCardCircuit {
    Map<String, Double> creditCardMoney;

    public CreditCardCircuit() {
        creditCardMoney = new HashMap<>();
        creditCardMoney.put("79927398713", 1000.0);
    }

    public boolean validateCreditCard(String creditCard) {
        if (creditCard == null) return false;
        int nSum = 0;
        boolean isSecond = false;
        for (int i = creditCard.length() - 1; i >= 0; i--) {
            int d = creditCard.charAt(i) - '0';
            if (isSecond)
                d = d * 2;
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public boolean isValid(String creditCard){
        return creditCardMoney.containsKey(creditCard);
    }

    public boolean pay(String creditCard, double money) {
        if (creditCard == null || money < 0 || !creditCardMoney.containsKey(creditCard)) return false;
        double amount = creditCardMoney.get(creditCard);
        if (amount < money) return false;
        else {
            creditCardMoney.put(creditCard, amount - money);
            return true;
        }
    }
}
