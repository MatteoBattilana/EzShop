package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleTransactionImpl extends BalanceOperationImpl implements SaleTransaction {
    private int mTicketNumer;
    private Map<ProductTypeImpl, TransactionProduct> mTicketEntries;
    private double mDiscountRate;
    private String mTransactionStatus;

    public SaleTransactionImpl(int mTicketNumber) {
        super(-1, LocalDate.now(), "SALE", "UNPAID");
        this.mTicketNumer = mTicketNumber;
        mTicketEntries = new HashMap<>();
        mDiscountRate = 0.0;
        mDate = LocalDate.now();
        mTransactionStatus = "OPENED";
    }

    public String getTransactionStatus() {
        return mTransactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        mTransactionStatus = transactionStatus;
    }

    @Override
    public Integer getTicketNumber() {
        return mTicketNumer;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        mTicketNumer = ticketNumber;
    }

    @Override
    public List<TicketEntry> getEntries() {
        return new ArrayList<>(mTicketEntries.values());
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {}

    @Override
    public double getDiscountRate() {
        return mDiscountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        mDiscountRate = discountRate;
    }

    @Override
    public double getPrice() {
        double sum = 0.0;
        for (TicketEntry t: mTicketEntries.values()) {
            sum += (t.getPricePerUnit() - t.getPricePerUnit() * getDiscountRate()) * t.getAmount();
        }

        sum -= sum * mDiscountRate;
        return sum;
    }

    @Override
    public void setPrice(double price) { }

    @Override
    public double getMoney() {
        return getPrice();
    }

    @Override
    public void setMoney(double money) {}

    /**
     * Add amount of product to a sale transaction, only if enough quantity.
     *
     * @param product product to be removed
     * @param amount quantity of product to be removed from the transaction
     * @return true if the operation can be performed and if the number of product in the transaction are
     *              more than amount
     */
    public boolean addProductToSale(ProductTypeImpl product, int amount) {
        if (mTransactionStatus.equals("OPENED") && product != null && product.getQuantity() - product.getTemporaryQuantity() - amount >= 0) {
            TransactionProduct transactionProduct = mTicketEntries.get(product);
            if(transactionProduct != null) {
                transactionProduct.setAmount(transactionProduct.getAmount() + amount);
            }
            else {
                mTicketEntries.put(
                        product,
                        new TransactionProduct(product, amount)
                );
            }
            product.setTemporaryQuantity(-amount);
            return true;
        }
        return false;
    }

    /**
     * Remove an amount of product from a sale transaction
     *
     * @param product product to be removed
     * @param amount quantity of product to be removed from the transaction
     * @return true if the operation can be performed and if the number of product in the transaction are
     *              more than amount
     */
    public boolean removeProductFromSale(ProductTypeImpl product, int amount) {
        if (mTransactionStatus.equals("OPENED") && product != null) {
            TransactionProduct transactionProduct = mTicketEntries.get(product);
            if(transactionProduct != null && transactionProduct.getAmount() >= amount) {
                if (transactionProduct.getAmount() - amount == 0) {
                    mTicketEntries.remove(product);
                } else {
                    transactionProduct.setAmount(transactionProduct.getAmount() - amount);
                }
                product.setTemporaryQuantity(amount);
                return true;
            }
        }
        return false;
    }

    public boolean applyDiscountRateToProduct(ProductType product, double discountRate) {
        if (mStatus.equals("OPENED") && product != null) {
            TransactionProduct transactionProduct = mTicketEntries.get(product);
            if(transactionProduct != null) {
                transactionProduct.setDiscountRate(discountRate);
                return true;
            }
        }
        return false;
    }

    /**
     * Return the number of points of a sale transaction. Every 10€ the number of points is increased
     * by 1 (i.e. 19.99€ returns 1 point, 20.00€ returns 2 points).
     *
     * @return number of points
     */
    public int computePointsForSale() {
        return (int) Math.floor(getMoney()/10.0);
    }

    public void commitAllTemporaryQuantity() {
        for (ProductTypeImpl pt: mTicketEntries.keySet()) {
            pt.setQuantity(pt.getQuantity() + pt.getTemporaryQuantity());
            pt.setTemporaryQuantity(-pt.getTemporaryQuantity());
        }
    }
}
