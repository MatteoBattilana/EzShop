package it.polito.ezshop.data;

import java.time.LocalDate;

public class OrderImpl extends BalanceOperationImpl {
    private String mProductCode;
    private double mPricePerUnit;
    private int mQuantity;
    private String mOrderStatus;
    private LocalDate mArrival;

    public OrderImpl(int mBalanceId, LocalDate date, String mProductCode, double mPricePerUnit, int mQuantity, String status, String orderStatus) {
        super(mBalanceId, date, "ORDER", status);
        this.mProductCode = mProductCode;
        this.mPricePerUnit = mPricePerUnit;
        this.mQuantity = mQuantity;
        this.mOrderStatus = orderStatus;
    }

    public OrderImpl(int mBalanceId, String mProductCode, double mPricePerUnit, int mQuantity, String status, String orderStatus) {
        super(mBalanceId, LocalDate.now(), "ORDER", status);
        this.mProductCode = mProductCode;
        this.mPricePerUnit = mPricePerUnit;
        this.mQuantity = mQuantity;
        this.mOrderStatus = orderStatus;
    }

    @Override
    public double getMoney() {
        return -mQuantity * mPricePerUnit;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public String getProductCode() {
        return mProductCode;
    }

    public void setProductCode(String productCode) {
        if(productCode != null)
            mProductCode = productCode;
    }

    public double getPricePerUnit() {
        return mPricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        if(pricePerUnit >= 0.0)
            mPricePerUnit = pricePerUnit;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String status) {
        if(status != null && (status.equals("ISSUED") || status.equals("PAYED") || status.equals("COMPLETED")))
            mOrderStatus = status;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0)
            mQuantity = quantity;
    }

    public void recordOrderArrival() {
        setOrderStatus("COMPLETED");
        mArrival = LocalDate.now();
    }

    public LocalDate getDateArrival() {
        return mArrival;
    }
}
