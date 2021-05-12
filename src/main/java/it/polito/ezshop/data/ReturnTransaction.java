package it.polito.ezshop.data;

import java.time.LocalDate;

public class ReturnTransaction extends BalanceOperationImpl {
    private ProductTypeImpl mProduct;
    private int mAmount;

    public ReturnTransaction(int balanceId, LocalDate date, String type, String status,  int amount, ProductTypeImpl product){
        super(balanceId, date, type, status);
        mAmount = amount;
        mProduct = product;
    }

    public ReturnTransaction(int balanceId) {
        this(balanceId, LocalDate.now(), "RETURN", "UNPAID", 0, null);
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

    public double computeTotal() {
        return Math.abs(getMoney());
    }

    @Override
    public double getMoney() {
        if (mProduct != null)
            return -mProduct.getPricePerUnit() * mAmount;
        else
            return 0;
    }
}
