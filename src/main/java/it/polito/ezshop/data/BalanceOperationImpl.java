package it.polito.ezshop.data;

import java.time.LocalDate;

public class BalanceOperationImpl implements BalanceOperation {
    int mBalanceId;
    LocalDate mDate;
    double mMoney;
    String mType;
    String mStatus;

    public BalanceOperationImpl(int mBalanceId, LocalDate mDate, double mMoney, String mType, String status) {
        this.mBalanceId = mBalanceId;
        this.mDate = mDate;
        this.mMoney = mMoney;
        this.mType = mType;
        this.mStatus = status;
    }

    public BalanceOperationImpl(int mBalanceId, LocalDate mDate, String mType, String status) {
        this(mBalanceId, mDate, 0, mType, status);
    }

    public void setStatus(String status){
        if(status != null && (status.equals("OPENED") || status.equals("CLOSED") || status.equals("PAID")))
            this.mStatus = status;
    }

    @Override
    public int getBalanceId() {
        return mBalanceId;
    }

    @Override
    public void setBalanceId(int balanceId) {
        if (balanceId > 0)
            mBalanceId = balanceId;
    }

    @Override
    public LocalDate getDate() {
        return mDate;
    }

    @Override
    public void setDate(LocalDate date) {
        if (date != null)
            mDate = date;
    }

    @Override
    public double getMoney() {
        return mMoney;
    }

    @Override
    public void setMoney(double money) {
        mMoney = money;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void setType(String type) {
        if(type != null && (type.equals("SALE") || type.equals("ORDER") || type.equals("RETURN") || type.equals("DEBIT") || type.equals("CREDIT")))
            mType = type;
    }

    public String getStatus() {
        return mStatus;
    }
}
