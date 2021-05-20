package it.polito.ezshop.data;

import java.time.LocalDate;

public class BalanceOperationImpl implements BalanceOperation {
    private int balanceId;
    private LocalDate date;
    private double amount;
    private String type;
    private String status;

    public BalanceOperationImpl(int balanceId, LocalDate date, double mMoney, String type, String status) {
        this.balanceId = balanceId;
        this.date = date;
        this.amount = mMoney;
        this.type = type;
        this.status = status;
    }

    public BalanceOperationImpl(int balanceId, LocalDate date, String type, String status) {
        this(balanceId, date, 0, type, status);
    }

    /**
     * Method used to update the status of a transaction, it can be OPENED, CLOSED or PAID
     *
     * @param status of the transaction
     */
    public void setStatus(String status){
        if(status != null && (status.equals("OPENED") || status.equals("CLOSED") || status.equals("PAID")))
            this.status = status;
    }

    @Override
    public int getBalanceId() {
        return balanceId;
    }

    @Override
    public void setBalanceId(int balanceId) {
        if (balanceId > 0)
            this.balanceId = balanceId;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        if (date != null)
            this.date = date;
    }

    @Override
    public double getMoney() {
        return amount;
    }

    @Override
    public void setMoney(double money) {
        amount = money;
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * Method used to update the type of a transaction, it can be SALE, ORDER, DEBIT or CREDIT
     *
     * @param type of the transaction
     */
    @Override
    public void setType(String type) {
        if(type != null && (type.equals("SALE") || type.equals("ORDER") || type.equals("RETURN") || type.equals("DEBIT") || type.equals("CREDIT")))
            this.type = type;
    }

    public String getStatus() {
        return status;
    }
}
