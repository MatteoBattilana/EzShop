package it.polito.ezshop.data;

public class ProductTypeImpl implements ProductType {
    int mQuantity;
    String mLocation;
    String mNote;
    String mDescription;
    String mBarcode;
    double mPricePerUnit;
    int mId;

    public ProductTypeImpl(int mQuantity, String mLocation, String mNote, String mDescription, String mBarcode, double mPricePerUnit, int mId) {
        this.mQuantity = mQuantity;
        this.mLocation = mLocation;
        this.mNote = mNote;
        this.mDescription = mDescription;
        this.mBarcode = mBarcode;
        this.mPricePerUnit = mPricePerUnit;
        this.mId = mId;
    }

    @Override
    public Integer getQuantity() {
        return mQuantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        if(quantity != null && quantity >= 0) mQuantity = quantity;
    }

    @Override
    public String getLocation() {
        return mLocation;
    }

    @Override
    public void setLocation(String location) {
        mLocation = location;
    }

    @Override
    public String getNote() {
        return mNote;
    }

    @Override
    public void setNote(String note) {
        if (note != null && !note.isEmpty()) mNote = note;
    }

    @Override
    public String getProductDescription() {
        return mDescription;
    }

    @Override
    public void setProductDescription(String productDescription) {
        if (productDescription != null && !productDescription.isEmpty()) mDescription = productDescription;
    }

    @Override
    public String getBarCode() {
        return mBarcode;
    }

    @Override
    public void setBarCode(String barCode) {
        if (barCode != null && !barCode.isEmpty()) mBarcode = barCode;
    }

    @Override
    public Double getPricePerUnit() {
        return mPricePerUnit;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {
        if(pricePerUnit != null && pricePerUnit >= 0) mPricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getId() {
        return mId;
    }

    @Override
    public void setId(Integer id) {
        if(id != null && id > 0) mId = id;
    }

    public ProductTypeImpl clone() {
        return  new ProductTypeImpl(
                mQuantity,
                mLocation,
                mNote,
                mDescription,
                mBarcode,
                mPricePerUnit,
                mId
        );
    }
}
