package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReturnTransaction extends BalanceOperationImpl {
    // Product discount rate
    private final double discountRate;
    // Map of all the transaction product used for returning the products
    private final Map<TransactionProduct, Map<String, Product>> returns;

    // Constructor used for the database
    public ReturnTransaction(int balanceId, LocalDate date, String type, String status, double discountRate) {
        super(balanceId, date, type, status);
        this.discountRate = discountRate;
        this. returns = new HashMap<>();
    }

    public ReturnTransaction(int balanceId, double discountRate) {
        this(balanceId, LocalDate.now(), "RETURN", "OPENED", discountRate);
    }

    /**
     * Add a product to be returned; it checks also if the remaining quantity in
     * the TransactionProduct is larger or equal the amount
     *
     * @param prod product to be returned
     * @return if the product has been added to the return map
     */
    public boolean addProduct(TransactionProduct prod, Product pt) {
        if(prod != null && pt != null) {
            if (returns.containsKey(prod) && !returns.get(prod).containsKey(pt.getRFID())) {
                // If already present, increase the amount only if there are enough
                if (prod.getAmount() - returns.get(prod).size() - 1 >= 0) {
                    Map<String, Product> stringProductMap = returns.get(prod);
                    if (!stringProductMap.containsKey(pt.getRFID())) {
                        returns.get(prod).put(pt.getRFID(), pt);
                        return true;
                    }
                }
            } else {
                // If not present, add only if the amount is withing the original order amount
                if (prod.getAmount() - 1 >= 0) {
                    returns.put(prod, new HashMap<>());
                    returns.get(prod).put(pt.getRFID(), pt);
                    return true;
                }
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
        for (Map.Entry<TransactionProduct, Map<String, Product>> element : returns.entrySet()) {
            double priceWithDiscount = element.getKey().getPricePerUnit() - element.getKey().getPricePerUnit() * element.getKey().getDiscountRate();
            priceWithDiscount *= element.getValue().size();
            sum -= priceWithDiscount - priceWithDiscount * discountRate;
        }

        return sum;
    }

    /**
     * Return the map of TransactionProduct
     *
     * @return the map of TransactionProduct
     */
    public Map<TransactionProduct, Map<String, Product>> getReturns() {
        return returns;
    }

    public void remove(TransactionProduct soldP, Product removed) {
        returns.get(soldP).remove(removed.getRFID());
    }

    public void set(TransactionProduct product, Map<String, Product> allReturnProducts) {
        returns.put(product, allReturnProducts);
    }
}
