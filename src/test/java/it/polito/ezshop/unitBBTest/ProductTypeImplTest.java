package it.polito.ezshop.unitBBTest;

import it.polito.ezshop.data.ProductTypeImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTypeImplTest {
    @Test
    public void negativeSetId() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Integer mId = product.getId();
        product.setId(-5);
        assertEquals(mId, product.getId());
        // boundary
        product.setId(0);
        assertEquals(mId, product.getId());
    }

    @Test
    public void positiveSetId() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Integer id = 5;
        product.setId(5);
        assertEquals(id, product.getId());
        // boundary
        product.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, product.getId(),1);
    }

    @Test
    public void nullSetId() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Integer id = product.getId();
        product.setId(null);
        assertEquals(id, product.getId());
    }

    @Test
    public void negativeSetQuantity() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Integer quantity = product.getQuantity();
        product.setQuantity(-5);
        assertEquals(quantity, product.getQuantity());
        // boundary
        product.setId(-1);
        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void positiveSetQuantity() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        product.setQuantity(20);
        Integer quantity = 20;
        assertEquals(quantity, product.getQuantity());
        // boundary
        product.setQuantity(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, product.getQuantity(),1);


    }

    @Test
    public void invalidSetQuantity() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Integer quantity = product.getQuantity();
        product.setQuantity(null);
        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void negativeSetPricePerUnit() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Double price = product.getPricePerUnit();
        product.setPricePerUnit(-0.50);
        assertEquals(price, product.getPricePerUnit());
        // boundary
        product.setPricePerUnit(-0.0001);
        assertEquals(price, product.getPricePerUnit());
    }

    @Test
    public void invalidSetPricePerUnit() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        Double price = product.getPricePerUnit();
        product.setPricePerUnit(null);
        assertEquals(price, product.getPricePerUnit());
    }

    @Test
    public void positiveSetPricePerUnit() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.50, 50);
        product.setPricePerUnit(0.60);
        Double price = 0.60;
        assertEquals(price, product.getPricePerUnit());
        //boundary
        product.setPricePerUnit(0.0001);
        Double priceunit = 0.0001;
        assertEquals(priceunit, product.getPricePerUnit());
    }

    @Test
    public void invalidSetBarCode() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String barcode = product.getBarCode();
        product.setBarCode("");
        assertEquals(barcode, product.getBarCode());
    }

    @Test
    public void nullSetBarCode() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String barcode = product.getBarCode();
        product.setBarCode(null);
        assertEquals(barcode, product.getBarCode());
    }

    @Test
    public void positiveSetBarCode() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        product.setBarCode("78515420");
        assertEquals("78515420", product.getBarCode());
    }

    @Test
    public void invalidSetProductDescription() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String description = product.getProductDescription();
        product.setProductDescription("");
        assertEquals(description, product.getProductDescription());
    }

    @Test
    public void nullSetProductDescription() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String description = product.getProductDescription();
        product.setProductDescription(null);
        assertEquals(description, product.getProductDescription());
    }

    @Test
    public void positiveSetProductDescription() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        product.setProductDescription("yellow");
        assertEquals("yellow", product.getProductDescription());
    }

    @Test
    public void invalidNote() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String note = product.getNote();
        product.setNote("");
        assertEquals(note, product.getNote());
    }

    @Test
    public void nullSetNote() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String note = product.getNote();
        product.setNote(null);
        assertEquals(note, product.getNote());
    }

    @Test
    public void positiveSetNote() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        product.setNote("bad");
        assertEquals("bad", product.getNote());
    }

    @Test
    public void setLocation() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        product.setLocation("hall");
        assertEquals("hall", product.getLocation());
        product.setLocation("");
        assertEquals("", product.getLocation());
        product.setLocation(null);
        assertNull(product.getLocation());
    }

    @Test
    public void setTemporaryQuantity() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        assertTrue(product.setTemporaryQuantity(Integer.MIN_VALUE));
        assertTrue(product.setTemporaryQuantity(Integer.MAX_VALUE));
        assertTrue(product.setTemporaryQuantity(0));
    }
}




