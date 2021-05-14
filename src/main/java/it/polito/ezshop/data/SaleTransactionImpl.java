package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleTransactionImpl extends BalanceOperationImpl implements SaleTransaction {
    private final DatabaseConnection mDatabaseConnection;
    private Map<ProductTypeImpl, TransactionProduct> mProdList;
    private Map<Integer, ReturnTransaction> mReturnTransactions;
    private double mDiscount;
    private String mTransactionStatus;

    public SaleTransactionImpl(DatabaseConnection databaseConnection, int id, LocalDate date, String type, String status, Map<ProductTypeImpl, TransactionProduct> tickets, Map<Integer, ReturnTransaction> returns, double discount, String transactionStatus) {
        super(id, date, type, status);
        mProdList = tickets;
        mReturnTransactions = returns;
        mDiscount = discount;
        mTransactionStatus = transactionStatus;
        mDatabaseConnection = databaseConnection;
    }

    public SaleTransactionImpl(DatabaseConnection databaseConnection, int id) {
        this(databaseConnection, id, LocalDate.now(), "SALE", "UNPAID", new HashMap<>(), new HashMap<>(), 0.0, "OPENED");
    }

    /**
     * Used to get the list of the return transaction
     * @return list of the return transaction
     */
    public List<ReturnTransaction> getReturnTransactions(){
        return new ArrayList<>(mReturnTransactions.values());
    }

    /**
     * Method used to create a new ReturnTransaction; the id is passed from the outside
     * since it must be unique among all the system
     *
     * @param newId is the id of the new return transaction
     * @return the reference to the instance of the ReturnTransaction
     */
    public ReturnTransaction startReturnTransaction(int newId) {
        ReturnTransaction returnT = new ReturnTransaction(newId, mDiscount);
        mReturnTransactions.put(returnT.getBalanceId(), returnT);
        return returnT;
    }

    /**
     * Add a product to the return transaction for the current sale
     *
     * @param returnId id of the return transaction
     * @param prod the product to be returned
     * @param amount of product to be returned
     *
     * @return true if the product had been added to the sale transition as
     *          a return product
     */
    public boolean addReturnProduct(Integer returnId, ProductTypeImpl prod, int amount) {
        TransactionProduct soldP = mProdList.get(prod);
        ReturnTransaction returnTransaction = mReturnTransactions.get(returnId);
        if (soldP != null && returnTransaction != null) {
            return returnTransaction.addProduct(soldP, amount);
        }
        return false;
    }

    public List<TransactionProduct> getTicketEntries() {
        return new ArrayList<>(mProdList.values());
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
        return new ArrayList<>(mProdList.values());
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {}

    @Override
    public double getDiscountRate() {
        return mDiscount;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        mDiscount = discountRate;
    }

    @Override
    public double getPrice() {
        double sum = 0.0;
        // Compute the price using the sale and product discounts
        for (TicketEntry t: mProdList.values()) {
            sum += (t.getPricePerUnit() - t.getPricePerUnit() * getDiscountRate()) * t.getAmount();
        }

        sum -= sum * mDiscount;
        return sum;
    }

    @Override
    public void setPrice(double price) { }

    @Override
    public double getMoney() {
        return getPrice();
    }

    public double computeTotal(){
        return getMoney();
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
            TransactionProduct transactionProduct = mProdList.get(product);
            if(transactionProduct != null) {
                transactionProduct.setAmount(transactionProduct.getAmount() + amount);
            }
            else {
                mProdList.put(
                        product,
                        new TransactionProduct(product, 0, amount, product.getPricePerUnit())
                );
            }
            product.setQuantity(product.getQuantity() - amount);
            return true;
        }
        return false;
    }

    /**
     * Remove an amount of product from a sale transaction. The transaction must be OPENED and the
     * relative product must exists in the sale
     *
     * @param product product to be removed
     * @param amount quantity of product to be removed from the transaction
     * @return true if the operation can be performed and if the number of product in the transaction are
     *              more than amount
     */
    public boolean removeProductFromSale(ProductTypeImpl product, int amount) {
        if (mTransactionStatus.equals("OPENED") && product != null) {
            TransactionProduct transactionProduct = mProdList.get(product);
            if(transactionProduct != null && transactionProduct.getAmount() >= amount) {
                // Remove the ticket entry if the remaining amount is 0
                if (transactionProduct.getAmount() - amount == 0) {
                    mProdList.remove(product);
                } else {
                    transactionProduct.setAmount(transactionProduct.getAmount() - amount);
                }

                product.setQuantity(transactionProduct.getAmount() + amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Apply the discout rate to a specific product
     *
     * @param product to be applied the discount
     * @param discountRate discount between 0 and 1
     * @return true if the discount has been applied and the transaction was still OPENED
     */
    public boolean applyDiscountRateToProduct(ProductType product, double discountRate) {
        // Check if open
        if (mTransactionStatus.equals("OPENED") && product != null) {
            TransactionProduct transactionProduct = mProdList.get(product);
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

    public ReturnTransaction getReturnTransaction(int id) {
        return mReturnTransactions.get(id);
    }

    /**
     * Delete a return transaction, only if it is not PAID
     *
     * @param returnId id of the return transaction
     * @return true if the return transaction has been removed
     */
    public boolean deleteReturnTransaction(Integer returnId) {
        ReturnTransaction returnTransaction = mReturnTransactions.get(returnId);
        if(returnTransaction != null) {
            if(returnTransaction.getStatus().equals("OPENED")) {
                mReturnTransactions.remove(returnId);
                return true;
            }
            else if(returnTransaction.getStatus().equals("CLOSED")){
                // Rollback quantities
                returnTransaction.getReturns().forEach((transactionProduct, amount) -> {
                    // Remove from the sale the products
                    transactionProduct.getProductType().setQuantity(transactionProduct.getProductType().getQuantity() - amount);
                    mDatabaseConnection.updateProductType(transactionProduct.getProductType());
                    transactionProduct.setAmount(transactionProduct.getAmount() + amount);
                });
                mReturnTransactions.remove(returnId);
                return true;
            }
        }
        return false;
    }



    /**
     * Return the total to be returned to the customer in absolute value. This
     * method also check if the state is UNPAID
     *
     * @param returnId id of the return transaction
     * @return the total in positive value of the value to be returned
     *          -1 otherwise
     */
    public double getReturnTransactionTotal(Integer returnId) {
        ReturnTransaction retT = mReturnTransactions.get(returnId);
        if(retT != null && retT.getStatus().equals("CLOSED")){
            return retT.computeTotal();
        }

        return -1;
    }

    /**
     * Method used to close the return transaction. For each return product it updates the number of product in the
     * inventory and update the DB. It also updates the product amount of the initial sale transaction; the total
     * of the sale transaction is updated accordingly to the the number of products still present
     *
     * @param returnId id of the return transaction
     * @param commit if true the transaction is stored, otherwise the operation is deleted and rolled back
     * @return true if the operation has been saved/deleted correctly
     */
    public boolean endReturnTransaction(Integer returnId, boolean commit) {
        if(!commit) {
            return deleteReturnTransaction(returnId);
        }
        else {
            ReturnTransaction returnTransaction = mReturnTransactions.get(returnId);
            if(returnTransaction != null) {
                returnTransaction.setStatus("CLOSED");
                // Update quantity of all products
                returnTransaction.getReturns().forEach((transactionProduct, amount) -> {
                    // Add to inventory the returned products
                    transactionProduct.getProductType().setQuantity(transactionProduct.getProductType().getQuantity() + amount);
                    // Remove from the sale the products
                    transactionProduct.setAmount(transactionProduct.getAmount() - amount);
                });
                mDatabaseConnection.saveSaleTransaction(this);
                mDatabaseConnection.saveReturnTransaction(returnTransaction, getBalanceId());
                return true;
            }
        }

        return false;
    }

    /**
     * Method used to set a return transaction as paid. It also updates its reference
     * in the DB
     *
     * @param returnId id of the return transaction
     * @return true if the DB has been updated
     */
    public boolean setPaidReturnTransaction(Integer returnId) {
        ReturnTransaction returnTransaction = mReturnTransactions.get(returnId);
        if(returnTransaction != null && returnTransaction.getStatus().equals("CLOSED")) {
            returnTransaction.setStatus("PAID");
            if(mDatabaseConnection.setStatusReturnTransaction(returnTransaction)){
                return true;
            } else {
                returnTransaction.setStatus("CLOSED");
            }
        }
        return false;
    }

    /**
     * Used to delete all the return transactions inside this sale
     */
    public void reset() {
        for (ReturnTransaction ret : mReturnTransactions.values()){
            mDatabaseConnection.deleteReturnTransaction(ret);
        }
        mReturnTransactions.clear();
        mDatabaseConnection.deleteAllTransactionProducts(mBalanceId);
        mProdList.clear();
    }
}
