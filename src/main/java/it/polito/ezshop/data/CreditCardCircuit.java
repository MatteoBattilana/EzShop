package it.polito.ezshop.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreditCardCircuit {
    Map<String, Double> mCreditCardMoney;
    private static final String sFILE = "cards.txt";

    public CreditCardCircuit() {
        mCreditCardMoney = loadFromFile();
    }

    /**
     * Method used to load the file cards.txt
     * @return the map with all cards and the balance
     */
    private Map<String, Double> loadFromFile(){
        Map<String, Double> ret = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(sFILE));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                String[] card = line.split(";");
                if(card.length == 2)
                    ret.put(card[0], Double.valueOf(card[1]));
            }
            reader.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return ret;
    }

    /**
     * Checks if the credit card code is valid
     * @param creditCard
     * @return true if the credit card code is valid, according to the lunh algorithm
     */
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
                updateFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Methods that updates the amount of money of the cards into a file
     */
    private void updateFile() {
        try {
            FileWriter myWriter = new FileWriter(sFILE, false);
            for (Map.Entry<String, Double> card: mCreditCardMoney.entrySet()) {
                myWriter.write(card.getKey() + ";" + card.getValue());
            }
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
