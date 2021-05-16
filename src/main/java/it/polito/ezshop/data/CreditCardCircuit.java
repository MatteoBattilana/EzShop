package it.polito.ezshop.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreditCardCircuit {
    private Map<String, Double> mCreditCardMoney;
    private String fileName;
    /**
     * Constructor used to load the file cards.txt
     */
    public CreditCardCircuit(String fileName) {
        this.fileName = fileName;
        mCreditCardMoney = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                if (line.charAt(0) != '#') {
                    String[] card = line.split(";");
                    if (card.length == 2)
                        mCreditCardMoney.put(card[0], Double.valueOf(card[1]));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Checks if the credit card code is valid
     * @param creditCard
     * @return true if the credit card code is valid, according to the lunh algorithm
     */
    public boolean validateCreditCard(String creditCard) {
        if (creditCard == null || creditCard.isEmpty()) return false;
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

    /**
     * Checks the availability of the card
     * @param creditCard
     * @return if the credit card is in the list
     */
    public boolean isValid(String creditCard){
        return mCreditCardMoney.containsKey(creditCard);
    }

    /**
     *
     * Allows to pay with the credit card, of exists the money are substracted
     * @param creditCard code
     * @param money to pay
     * @return true if the payment has been successful
     */
    public boolean pay(String creditCard, double money) {
        if (isValid(creditCard) && money >= 0) {
            double amount = mCreditCardMoney.get(creditCard);
            if (amount >= money) {
                mCreditCardMoney.put(creditCard, amount - money);

                // Updates the amount of money of the cards into a file
                try {
                    FileWriter myWriter = new FileWriter(fileName, false);
                    for (Map.Entry<String, Double> card: mCreditCardMoney.entrySet()) {
                        myWriter.write(card.getKey() + ";" + card.getValue() + "\n");
                    }
                    myWriter.close();
                } catch (IOException ignored) { }
                return true;
            }
        }
        return false;
    }
}
