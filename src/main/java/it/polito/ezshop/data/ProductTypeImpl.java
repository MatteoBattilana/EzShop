package it.polito.ezshop.data;

public class ProductTypeImpl implements ProductType {
    private int quantity;
    private String position;
    private String note;
    private String description;
    private String barcode;
    private double pricePerUnit;
    private double discountRate;
    private int id;

    public ProductTypeImpl(int quantity, String position, String note, String description, String barcode, double pricePerUnit, int id) {
        this.quantity = quantity;
        this.position = position;
        this.note = note;
        this.description = description;
        this.barcode = barcode;
        this.pricePerUnit = pricePerUnit;
        this.id = id;
        this.discountRate = 0.0;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        if(quantity != null && quantity >= 0) this.quantity = quantity;
    }

    public boolean setTemporaryQuantity(Integer quantity){
        if(quantity != null && quantity >= 0){
        setQuantity(quantity);
        return true;}
        return false;
    }

    @Override
    public String getLocation() {
        return position;
    }

    @Override
    public void setLocation(String location) {
        position = location;
    }

    public void setPosition(String location){if( location!= null && !location.isEmpty())
        setLocation(location);
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        if (note != null && !note.isEmpty()) this.note = note;
    }

    @Override
    public String getProductDescription() {
        return description;
    }

    @Override
    public void setProductDescription(String productDescription) {
        if (productDescription != null && !productDescription.isEmpty()) description = productDescription;
    }

    @Override
    public String getBarCode() {
        return barcode;
    }

    @Override
    public void setBarCode(String barCode) {
        if (barCode != null && !barCode.isEmpty()) barcode = barCode;
    }

    @Override
    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {
        if(pricePerUnit != null && pricePerUnit >= 0) this.pricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        if(id != null && id > 0) this.id = id;
    }
}
