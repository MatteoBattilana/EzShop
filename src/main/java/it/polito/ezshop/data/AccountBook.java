package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBook {
    Map<Integer, BalanceOperationImpl> mBalanceOperations;
    double mBalance;
    private DatabaseConnection databaseConnection;

    public AccountBook(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        mBalanceOperations = new HashMap<>();
        mBalance = 0.0;
    }

    public int recordBalanceUpdate(double toBeAdded, String status, String type) {
        int lastId = getLastId() + 1;
        BalanceOperationImpl operation = new BalanceOperationImpl(lastId, LocalDate.now(), toBeAdded, type, status);
        mBalanceOperations.put(
                lastId,
                operation
        );

        if(operation.getStatus().equals("PAID")) {
            mBalance += toBeAdded;
            databaseConnection.saveBalanceOperation(operation);
        }
        databaseConnection.updateBalance(mBalance);

        return operation.getBalanceId();
    }

    public void add(BalanceOperationImpl operation) {
        mBalanceOperations.put(operation.getBalanceId(), operation);
    }

    List<BalanceOperation> getCreditAndDebits(LocalDate from, LocalDate to) {
        List<BalanceOperation> filtered = new ArrayList<>();
        for (BalanceOperationImpl op : mBalanceOperations.values()) {
            // Check that the operation date is >= from and <= to
            if ((from == null || op.getDate().compareTo(from) >= 0) && (to == null || op.getDate().compareTo(to) < 0) && op.getStatus().equals("PAID")) {
                filtered.add(op);
            }
        }
        return filtered;
    }

    /**
     * Mark a balance as paid
     * @param id of the balance operation
     */
    public void setAsPaid(Integer id) {
        BalanceOperationImpl balanceOperation = mBalanceOperations.get(id);
        if (balanceOperation != null) {
            mBalance += balanceOperation.getMoney();
            balanceOperation.setStatus("PAID");
            databaseConnection.updateBalance(mBalance);
        }
    }

    public double computeBalance() {
        return mBalance;
    }

    public void reset() {
        databaseConnection.updateBalance(0.0);
        for(BalanceOperation op : mBalanceOperations.values()){
            if(op instanceof SaleTransactionImpl) {
                databaseConnection.deleteSaleTransaction((SaleTransactionImpl) op);
            }
            else if(op instanceof BalanceOperationImpl) {
                databaseConnection.deleteBalanceOperation(op);
            }
        }
        mBalanceOperations = new HashMap<>();
        mBalance = 0.0;
    }

    public boolean checkIfEnoughMoney(double toBeAdded) {
        return computeBalance() + toBeAdded >= 0;
    }

    int getLastId() {
        int newIdBalance = 0;
        for (Integer id : mBalanceOperations.keySet()) {
            if (id > newIdBalance) {
                newIdBalance = id;
            }
        }
        return newIdBalance;
    }

    public void remove(int balanceId) {
        if (balanceId > 0)
            mBalanceOperations.remove(balanceId);
    }

    void loadFromFromDb(){
        mBalanceOperations.putAll(databaseConnection.getAllBalanceOperations());
        mBalance = databaseConnection.getBalance();
    }
}
