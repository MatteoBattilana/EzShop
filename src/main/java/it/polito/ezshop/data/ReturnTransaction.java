package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReturnTransaction extends BalanceOperationImpl {
    // Product discount rate
    private final double mDiscountRate;
    // Map of all the transaction product used for returning the products
    private final Map<TransactionProduct, Integer> mReturns;

    // Constructor used for the database
    public ReturnTransaction(int balanceId, LocalDate date, String type, String status, double discountRate) {
        super(balanceId, date, type, status);
        mDiscountRate = discountRate;
        mReturns = new HashMap<>();
    }

    public ReturnTransaction(int balanceId, double discountRate) {
        this(balanceId, LocalDate.now(), "RETURN", "UNPAID", discountRate);
    }

    /**
     * Add a product to be returned; it checks also if the remaining quantity in
     * the TransactionProduct is larger or equal the amount
     *
     * @param prod product to be returned
     * @param amount of product to be returned, must be lower than the original sale
     * @return if the product has been added to the return map
     */
    public boolean addProduct(TransactionProduct prod, int amount) {
        if (mReturns.containsKey(prod)) {
            // If already present, increase the amount only if there are enough
            if(prod.getAmount() - mReturns.get(prod) - amount >= 0) {
                mReturns.put(prod, mReturns.get(prod) + amount);
                return true;
            }
        } else {
            // If not present, add only if the amount is withing the original order amount
            if(prod.getAmount() - amount >= 0) {
                mReturns.put(prod, amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to compute the total of the return transaction, in absolute value
     *
     * @return absolute value of the return transaction
     */
    public double computeTotal() {
        return Math.abs(getMoney());
    }

    /**
     * Method used to compute the total of the return transaction, in negative value
     *
     * @return negative amount of money
     */
    @Override
    public double getMoney() {
        double sum = 0.0;
        // For all element to be returned, compute the price using the original discount price
        for (Map.Entry<TransactionProduct, Integer> element : mReturns.entrySet()) {
            double priceWithDiscount = element.getKey().getPricePerUnit() - element.getKey().getPricePerUnit() * element.getKey().getDiscountRate();
            priceWithDiscount *= element.getValue();
            sum -= priceWithDiscount - priceWithDiscount * mDiscountRate;
        }

        return sum;
    }

    /**
     * Return the map of TransactionProduct
     *
     * @return the map of TransactionProduct
     */
    public Map<TransactionProduct, Integer> getReturns() {
        return mReturns;
    }
}
