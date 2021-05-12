package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountBook {
    private List<BalanceOperationImpl> mBalanceOperations;
    double mBalance;
    private DatabaseConnection mDatabaseConnection;

    public AccountBook(DatabaseConnection databaseConnection) {
        this.mDatabaseConnection = databaseConnection;
        mBalanceOperations = new ArrayList<>();
        mBalance = 0.0;
    }

    public void add(BalanceOperationImpl operation) {
        mBalanceOperations.add(operation);
    }

    public boolean recordBalanceUpdate(double toBeAdded) {
        if (mBalance + toBeAdded >= 0) {
            if (mDatabaseConnection.updateBalance(mBalance + toBeAdded)) {
                mBalance += toBeAdded;
                return true;
            }
        }
        return false;
    }

    List<BalanceOperation> getCreditAndDebits(LocalDate from, LocalDate to) {
        List<BalanceOperation> filtered = new ArrayList<>();
        for (BalanceOperationImpl op : mBalanceOperations) {
            // Check that the operation date is >= from and <= to
            if ((from == null || op.getDate().compareTo(from) >= 0) && (to == null || op.getDate().compareTo(to) < 0) && op.getStatus().equals("PAID")) {
                filtered.add(op);
            }
        }
        return filtered;
    }

    public double computeBalance() {
        return mBalance;
    }

    public void reset() {
        mDatabaseConnection.updateBalance(0.0);
        for (BalanceOperationImpl op: mBalanceOperations){
            if(op != null)
            mDatabaseConnection.deleteBalanceOperation(op);
        }
        mBalanceOperations = new ArrayList<>();
        mBalance = 0.0;
    }

    public void removeById(int id) {
        mBalanceOperations.removeIf(op -> op.getBalanceId() == id);
    }

    void loadFromFromDb(Map<Integer, ProductTypeImpl> mProducts) {
        mBalanceOperations.addAll(mDatabaseConnection.getAllBalanceOperations());
        mBalance = mDatabaseConnection.getBalance();
        for (SaleTransactionImpl sale : mDatabaseConnection.getAllSaleTransaction(mProducts).values()){
            mBalanceOperations.add(sale);
            mBalanceOperations.addAll(sale.getReturnTransactions());
        }
    }
}
