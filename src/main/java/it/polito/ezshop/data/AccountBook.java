package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountBook {
    // List of the balance opearations (sale, credit, order, debit, return)
    private List<BalanceOperationImpl> mBalanceOperations;
    // Reference to the database
    private final DatabaseConnection mDatabaseConnection;

    public AccountBook(DatabaseConnection databaseConnection) {
        this.mDatabaseConnection = databaseConnection;
        mBalanceOperations = new ArrayList<>();
    }

    /**
     * Method used to add a BalanceOperationImpl into the AccountBook
     * @param operation BalanceOperationImpl instance to be added
     */
    public void add(BalanceOperationImpl operation) {
        mBalanceOperations.add(operation);
    }

    /**
     * Return the list of all credits and debit that has the state set as PAID
     * between two dates, if null the date is not considered
     *
     * @param from start date
     * @param to end date
     * @return list of BalanceOperation within the selected period
     */
    List<BalanceOperation> getCreditAndDebits(LocalDate from, LocalDate to) {
        // Invert in case
        if(from != null && to != null && from.compareTo(to) > 0) {
            LocalDate temp = from;
            from = to;
            to = temp;
        }

        List<BalanceOperation> filtered = new ArrayList<>();
        for (BalanceOperationImpl op : mBalanceOperations) {
            // Check that the operation date is >= from and <= to
            if ((from == null || op.getDate().compareTo(from) >= 0) && (to == null || op.getDate().compareTo(to) < 0) && op.getStatus().equals("PAID")) {
                filtered.add(op);
            }
        }
        return filtered;
    }

    /**
     * Return the balance of the system, excluding the RetunrTransaction
     * since the original SaleTransaction price is reduced when returning a product
     *
     * @return the balance of the system, from the list of the balance operations
     */
    public double computeBalance() {
        double sum = 0.0;
        for(BalanceOperationImpl op: mBalanceOperations) {
            // Skip return transaction
            if(!(op instanceof ReturnTransaction))
                sum += op.getMoney();
        }

        return sum;
    }

    /**
     * Reset the internal lists and
     */
    public void reset() {
        for (BalanceOperationImpl op: mBalanceOperations){
            if(op != null)
            mDatabaseConnection.deleteBalanceOperation(op);
        }
        mBalanceOperations = new ArrayList<>();
    }

    public void removeById(int id) {
        mBalanceOperations.removeIf(op -> op.getBalanceId() == id);
    }

    void loadFromFromDb(Map<Integer, ProductTypeImpl> mProducts) {
        mBalanceOperations.addAll(mDatabaseConnection.getAllBalanceOperations());
        mBalanceOperations.addAll(mDatabaseConnection.getAllOrders().values());
        for (SaleTransactionImpl sale : mDatabaseConnection.getAllSaleTransaction(mProducts).values()){
            mBalanceOperations.add(sale);
            mBalanceOperations.addAll(sale.getReturnTransactions());
        }
    }
}
