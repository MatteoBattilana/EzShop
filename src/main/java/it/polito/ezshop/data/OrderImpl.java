package it.polito.ezshop.data;

public class OrderImpl implements Order {
    private int mBalanceId;
    private String mProductCode;
    private double mPricePerUnit;
    private int mQuantity;
    private String mStatus;

    public OrderImpl(int mBalanceId, String mProductCode, double mPricePerUnit, int mQuantity, String status) {
        this.mBalanceId = mBalanceId;
        this.mProductCode = mProductCode;
        this.mPricePerUnit = mPricePerUnit;
        this.mQuantity = mQuantity;
        this.mStatus = status;
    }

    @Override
    public Integer getBalanceId() {
        return mBalanceId;
    }

    @Override
    public void setBalanceId(Integer balanceId) {
        mBalanceId = balanceId;
    }

    @Override
    public String getProductCode() {
        return mProductCode;
    }

    @Override
    public void setProductCode(String productCode) {
        mProductCode = productCode;
    }

    @Override
    public double getPricePerUnit() {
        return mPricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        mPricePerUnit = pricePerUnit;
    }

    @Override
    public int getQuantity() {
        return mQuantity;
    }

    @Override
    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    @Override
    public String getStatus() {
        return mStatus;
    }

    @Override
    public void setStatus(String status) {
        mStatus = status;
    }

    @Override
    public Integer getOrderId() {
        return mBalanceId;
    }

    @Override
    public void setOrderId(Integer orderId) {
        mBalanceId = orderId;
    }
}
