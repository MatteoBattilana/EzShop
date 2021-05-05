package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBook {
    Map<Integer, BalanceOperationImpl> mBalanceOperations;
    double mBalance;

    public AccountBook() {
        mBalanceOperations = new HashMap<>();
        mBalance = 0.0;
    }

    public boolean recordBalanceUpdate(double toBeAdded) {
        mBalance += toBeAdded;
        mBalanceOperations.put(
                getNextId(),
                new BalanceOperationImpl(getNextId(), LocalDate.now(), toBeAdded, toBeAdded >= 0 ? "CREDIT" : "DEBIT", "PAID")
        );

        return mBalance >= 0;
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
    public void setAsPaid(String id) {
        BalanceOperationImpl balanceOperation = mBalanceOperations.get(id);
        if (balanceOperation != null) {
            mBalance += balanceOperation.getMoney();
            balanceOperation.setStatus("PAID");
        }
    }

    public double computeBalance() {
        return mBalance;
    }

    public int getNextId() {
        return mBalanceOperations.get(mBalanceOperations.size() - 1).getBalanceId() + 1;
    }
}
