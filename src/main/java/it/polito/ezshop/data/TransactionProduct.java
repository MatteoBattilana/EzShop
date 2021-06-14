package it.polito.ezshop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionProduct implements TicketEntry {
    private double pricePerUnit;
    private ProductTypeImpl productType;
    int quantity;
    double discount;
    Map<String, Product> products;

    public TransactionProduct(ProductTypeImpl productType, double discount, int quantity, double pricePerUnit, Map<String, Product> products) {
        this.productType = productType;
        this.quantity = quantity;
        this.discount = discount;
        this.pricePerUnit = pricePerUnit;
        this.products= products;
    }

    public TransactionProduct(ProductTypeImpl productType, double discount, int quantity, double pricePerUnit) {
        this(productType, discount, quantity, pricePerUnit, new HashMap<>());
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

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public void addProduct(Product product) {
        products.put(product.getRFID(), product);
    }

    public void removeProduct(Product p) {
        products.remove(p.getRFID());
    }

    public boolean containsProduct(String RFID) {
        return products.containsKey(RFID);
    }
}
