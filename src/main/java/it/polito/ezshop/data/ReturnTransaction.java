package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnTransaction extends BalanceOperationImpl {
    // Product discount rate
    private final double discountRate;
    // Map of all the transaction product used for returning the products
    private final Map<TransactionProduct, Integer> returns;
    private final Map<TransactionProduct, List<Product>> returns2;

    // Constructor used for the database
    public ReturnTransaction(int balanceId, LocalDate date, String type, String status, double discountRate) {
        super(balanceId, date, type, status);
        this.discountRate = discountRate;
        this.returns = new HashMap<>();
        this.returns2 = new HashMap<>();
    }

    public ReturnTransaction(int balanceId, double discountRate) {
        this(balanceId, LocalDate.now(), "RETURN", "OPENED", discountRate);
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
        if (returns.containsKey(prod)) {
            // If already present, increase the amount only if there are enough
            if(prod.getAmount() - returns.get(prod) - amount >= 0) {
                returns.put(prod, returns.get(prod) + amount);
                return true;
            }
        } else {
            // If not present, add only if the amount is withing the original order amount
            if(prod.getAmount() - amount >= 0) {
                returns.put(prod, amount);
                return true;
            }
        }
        return false;
    }

    public boolean addProduct(TransactionProduct prod, Product p) {
        if(addProduct(prod, 1)){
            if(returns2.get(prod) == null) {
                returns2.put(prod, new ArrayList<>());
            }
            returns2.get(prod).add(p);
            return true;
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
        for (Map.Entry<TransactionProduct, Integer> element : returns.entrySet()) {
            double priceWithDiscount = element.getKey().getPricePerUnit() - element.getKey().getPricePerUnit() * element.getKey().getDiscountRate();
            priceWithDiscount *= element.getValue();
            sum -= priceWithDiscount - priceWithDiscount * discountRate;
        }

        return sum;
    }

    /**
     * Return the map of TransactionProduct
     *
     * @return the map of TransactionProduct
     */
    public Map<TransactionProduct, Integer> getReturns() {
        return returns;
    }

    public Map<TransactionProduct, List<Product>> getAllInReturnProducts() {
        return returns2;
    }
}
