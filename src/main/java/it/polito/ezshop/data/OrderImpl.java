package it.polito.ezshop.data;

import java.time.LocalDate;

public class OrderImpl extends BalanceOperationImpl {
    private String mProductCode;
    private double mPricePerUnit;
    private int mQuantity;
    private String mOrderStatus;

    public OrderImpl(int mBalanceId, LocalDate date, String mProductCode, double mPricePerUnit, int mQuantity, String status, String orderStatus) {
        super(mBalanceId, date, "ORDER", status);
        this.mProductCode = mProductCode;
        this.mPricePerUnit = mPricePerUnit;
        this.mQuantity = mQuantity;
        this.mOrderStatus = orderStatus;
    }

    public OrderImpl(int id, LocalDate date_op, String status, int quantity, String product_code, String order_status, double price_per_unit) {
        super(id, date_op, "ORDER", status);
        mQuantity = quantity;
        mProductCode = product_code;
        mOrderStatus = order_status;
        mPricePerUnit = price_per_unit;
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
        mProductCode = productCode;
    }

    public double getPricePerUnit() {
        return mPricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        mPricePerUnit = pricePerUnit;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String status) {
        mOrderStatus = status;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void recordOrderArrival() {
        mOrderStatus = "COMPLETED";
    }
}
