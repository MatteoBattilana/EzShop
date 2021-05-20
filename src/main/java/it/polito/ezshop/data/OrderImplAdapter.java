package it.polito.ezshop.data;

public class OrderImplAdapter implements Order {

    private final OrderImpl mOrder;

    public OrderImplAdapter(OrderImpl order) {
        mOrder = order;
    }

    @Override
    public Integer getBalanceId() {
        return getOrderId();
    }

    @Override
    public void setBalanceId(Integer balanceId) {
        mOrder.setBalanceId(balanceId);
    }

    @Override
    public String getProductCode() {
        return mOrder.getProductCode();
    }

    @Override
    public void setProductCode(String productCode) {
        mOrder.setProductCode(productCode);
    }

    @Override
    public double getPricePerUnit() {
        return mOrder.getPricePerUnit();
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        mOrder.setPricePerUnit(pricePerUnit);
    }

    @Override
    public int getQuantity() {
        return mOrder.getQuantity();
    }

    @Override
    public void setQuantity(int quantity) {
        mOrder.setQuantity(quantity);
    }

    @Override
    public String getStatus() {
        return mOrder.getOrderStatus();
    }

    @Override
    public void setStatus(String status) {
        mOrder.setOrderStatus(status);
    }

    @Override
    public Integer getOrderId() {
        return mOrder.getBalanceId();
    }

    @Override
    public void setOrderId(Integer orderId) {
        mOrder.setBalanceId(orderId);
    }
}
