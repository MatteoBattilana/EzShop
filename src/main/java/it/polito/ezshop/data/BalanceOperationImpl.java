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

    public void setStatus(String status){
        this.mStatus = status;
    }

    @Override
    public int getBalanceId() {
        return 0;
    }

    @Override
    public void setBalanceId(int balanceId) {
        if (balanceId > 0)
            mBalanceId = balanceId;
        else
            mBalanceId = -1;
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
        mType = type;
    }

    public String getStatus() {
        return mStatus;
    }
}
