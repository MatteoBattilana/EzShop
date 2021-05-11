package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleTransactionImpl extends BalanceOperationImpl implements SaleTransaction {
    private Map<ProductTypeImpl, TransactionProduct> mTicketEntries;
    private Map<Integer, ReturnTransaction> mReturns;
    private double mDiscountRate;
    private String mTransactionStatus;

    public SaleTransaction clone() {
        SaleTransactionImpl saleTransaction = new SaleTransactionImpl(getBalanceId());
        saleTransaction.setDiscountRate(mDiscountRate);
        saleTransaction.setTransactionStatus(mTransactionStatus);

        Map<ProductTypeImpl, TransactionProduct> map = new HashMap<>();
        for (Map.Entry<ProductTypeImpl, TransactionProduct> entry : mTicketEntries.entrySet()) {
            map.put(entry.getKey().clone(), entry.getValue().clone());
        }
        saleTransaction.setTransactionProductMap(map);
        return saleTransaction;
    }

    public void setTransactionProductMap (Map<ProductTypeImpl, TransactionProduct> map) {
        mTicketEntries = map;
    }

    public SaleTransactionImpl(int id, LocalDate date, String type, String status, Map<ProductTypeImpl, TransactionProduct> tickets, Map<Integer, ReturnTransaction> returns, double discount, String transactionStatus) {
        super(id, date, type, status);
        mTicketEntries = tickets;
        mReturns = returns;
        mDiscountRate = discount;
        mTransactionStatus = transactionStatus;
    }

    public SaleTransactionImpl(int id) {
        this(id, LocalDate.now(), "SALE", "UNPAID", new HashMap<>(), new HashMap<>(), 0.0, "OPENED");
    }

    public List<ReturnTransaction> getReturnTransactions(){
        return new ArrayList<>(mReturns.values());
    }

    public ReturnTransaction startReturnTransaction(int balanceId) {
        ReturnTransaction returnT = new ReturnTransaction(balanceId);
        mReturns.put(balanceId, returnT);
        return returnT;
    }

    public boolean setReturnProduct(Integer returnId, ProductTypeImpl prod, int amount) {
        TransactionProduct soldP = mTicketEntries.get(prod);
        if(soldP != null && soldP.getAmount() >= amount) {
            ReturnTransaction returnTransaction = mReturns.get(returnId);
            if(returnTransaction != null) {
                returnTransaction.setProduct(prod, amount);
                return true;
            }
        }
        return false;
    }

    public List<TransactionProduct> getTicketEntries() {
        return new ArrayList<>(mTicketEntries.values());
    }

    public String getTransactionStatus() {
        return mTransactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        mTransactionStatus = transactionStatus;
    }

    @Override
    public Integer getTicketNumber() {
        return getBalanceId();
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        setBalanceId(ticketNumber);
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
        if (mTransactionStatus.equals("OPENED") && product != null && product.getQuantity() - amount >= 0) {
            TransactionProduct transactionProduct = mTicketEntries.get(product);
            if(transactionProduct != null) {
                transactionProduct.setAmount(transactionProduct.getAmount() + amount);
            }
            else {
                mTicketEntries.put(
                        product,
                        new TransactionProduct(product, 0, amount)
                );
            }
            product.setQuantity(product.getQuantity() - amount);
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

                product.setQuantity(transactionProduct.getAmount() + amount);
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

    public ReturnTransaction getReturnById(int id) {
        return mReturns.get(id);
    }


    public ReturnTransaction deleteReturnTransaction(Integer returnId) {
        ReturnTransaction returnTransaction = mReturns.get(returnId);
        if(returnTransaction != null && !returnTransaction.getStatus().equals("PAID")) {
            ProductTypeImpl product = returnTransaction.getProduct();
            if(product != null && returnTransaction.isCommited()){
                product.setQuantity(product.getQuantity() + returnTransaction.getAmount());
            }
        }
        return returnTransaction;
    }

    public double getReturnTransactionTotal(Integer returnId) {
        ReturnTransaction retT = mReturns.get(returnId);
        if(retT != null && retT.isCommited() && retT.getStatus().equals("UNPAID")){
            return retT.computeTotal();
        }

        return -1;
    }
}
