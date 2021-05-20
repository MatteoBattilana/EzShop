package it.polito.ezshop.data;

public class TransactionProduct implements TicketEntry {
    private double pricePerUnit;
    private ProductTypeImpl productType;
    int quantity;
    double discount;

    public TransactionProduct(ProductTypeImpl productType, double discount, int quantity, double pricePerUnit) {
        this.productType = productType;
        this.quantity = quantity;
        this.discount = discount;
        this.pricePerUnit = pricePerUnit;
    }

    public ProductTypeImpl getProductType(){
        return productType;
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
        if(amount >= 0)
            this.quantity = amount;
    }

    @Override
    public double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public double getDiscountRate() {
        return discount;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discount = discountRate;
    }

    public boolean applyDiscountRateToProduct(double discountRate) {
        setDiscountRate(discountRate);
        return true;
    }
}
