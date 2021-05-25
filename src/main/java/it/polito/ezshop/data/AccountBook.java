package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountBook {
    // List of the balance opearations (sale, credit, order, debit, return)
    private List<BalanceOperationImpl> opList;
    // Reference to the database
    private final DatabaseConnection databaseConnection;
    // Store the balance
    private double balance;

    public AccountBook(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        opList = new ArrayList<>();
        balance = 0.0;
    }

    /**
     * Method used to add a BalanceOperationImpl into the AccountBook
     * @param operation BalanceOperationImpl instance to be added
     */
    public void add(BalanceOperationImpl operation) {
        if(operation != null)
            opList.add(operation);
    }

    /**
     * This method update the balance
     *
     * @param toBeAdded to the balance
     */
    public boolean recordBalanceUpdate(double toBeAdded) {
        if(balance + toBeAdded >= 0){
            if(databaseConnection.updateBalance(balance + toBeAdded)){
                balance += toBeAdded;
                return true;
            }
        }
        return false;
    }

    /**
     * Return the list of all credits and debit that has the state set as PAID
     * between two dates, if null the date is not considered
     *
     * @param from start date
     * @param to end date
     * @return list of BalanceOperation within the selected period
     */
    public List<BalanceOperation> getCreditAndDebits(LocalDate from, LocalDate to) {
        // Invert in case
        if(from != null && to != null && from.compareTo(to) > 0) {
            LocalDate temp = from;
            from = to;
            to = temp;
        }

        List<BalanceOperation> filtered = new ArrayList<>();
        for (BalanceOperationImpl op : opList) {
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
        return balance;
    }

    /**
     * Reset the internal lists and
     */
    public void reset() {
        for (BalanceOperationImpl op: opList){
            if(op != null)
                databaseConnection.deleteBalanceOperation(op);
        }
        opList = new ArrayList<>();
        balance = 0.0;
        databaseConnection.updateBalance(balance);
    }

    /**
     * Method used to load all the balance operations at the startup
     *
     * @param sales the sale map, used to record the sales
     */
    void loadFromFromDb(Map<Integer, SaleTransactionImpl> sales) {
        balance = databaseConnection.getBalance();
        opList.addAll(databaseConnection.getAllBalanceOperations());
        opList.addAll(databaseConnection.getAllOrders().values());
        for (SaleTransactionImpl sale : sales.values()){
            opList.add(sale);
            opList.addAll(sale.getReturnTransactions());
        }
    }
}
