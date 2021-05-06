package it.polito.ezshop.data;

public class TransactionProduct implements TicketEntry {
    private ProductTypeImpl productType;
    int quantity;
    double discount;

    public TransactionProduct(ProductTypeImpl productType, int quantity) {
        this.productType = productType;
        this.quantity = quantity;
        this.discount = 0;
    }

    @Override
    public String getBarCode() {
        return productType.getBarCode();
    }

    @Override
    public void setBarCode(String barCode) {
        productType.setBarCode(barCode);
    }

    @Override
    public String getProductDescription() {
        return productType.getProductDescription();
    }

    @Override
    public void setProductDescription(String productDescription) {
        productType.setProductDescription(productDescription);
    }

    @Override
    public int getAmount() {
        return quantity;
    }

    @Override
    public void setAmount(int amount) {
        this.quantity = amount;
    }

    @Override
    public double getPricePerUnit() {
        return productType.getPricePerUnit();
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        productType.setPricePerUnit(pricePerUnit);
    }

    @Override
    public double getDiscountRate() {
        return discount;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discount = discountRate;
    }
}
