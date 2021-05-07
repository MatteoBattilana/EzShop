package it.polito.ezshop.data;

import java.time.LocalDate;

public class ReturnTransaction extends BalanceOperationImpl {
    private final int mId;
    private ProductTypeImpl mProduct;
    private int mAmount;
    private boolean mCommited;

    public ReturnTransaction(int id, int balanceId) {
        super(balanceId, LocalDate.now(), "RETURN", "UNPAID");
        mId = id;
        mCommited = false;
        mAmount = 0;
        mProduct = null;
    }

    public Integer getId() {
        return mId;
    }

    public void setCommited(boolean commited) {
        mCommited = commited;
    }

    public void setProduct(ProductTypeImpl prod, int amount) {
        mProduct = prod;
        mAmount = amount;
    }

    public ProductTypeImpl getProduct() {
        return mProduct;
    }

    public int getAmount() {
        return mAmount;
    }

    public boolean isCommited() {
        return mCommited;
    }

    public double computeTotal(){
        return Math.abs(getMoney());
    }

    @Override
    public double getMoney() {
        if(mProduct != null)
            return -mProduct.getPricePerUnit() * mAmount;
        else
            return 0;
    }
}
