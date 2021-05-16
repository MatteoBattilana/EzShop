package it.polito.ezshop.blackbox;



import it.polito.ezshop.data.ProductTypeImpl;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ProductTypeImplTest {


    @Test
    public void NegativeSetId(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Integer mId= product.getId();
        product.setId(-5);
        assertEquals(mId, product.getId());
        // boundary
        product.setId(0);
        assertEquals(mId,product.getId());

    }

    @Test
    public void PositiveSetId(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Integer id= 5;
        product.setId(5);
        assertEquals(id, product.getId());



    }
    @Test
    public void NullSetId(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Integer id= product.getId();
        product.setId(null);
        assertEquals(id, product.getId());



    }

    @Test
    public void NegativeSetQuantity(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Integer quantity= product.getQuantity();
        product.setQuantity(-5);
        assertEquals(quantity, product.getQuantity());
        // boundary
        product.setId(-1);
        assertEquals(quantity,product.getQuantity());

    }

    @Test
    public void PositiveSetQuantity(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        product.setQuantity(20);
        Integer quantity= 20;
        assertEquals(quantity, product.getQuantity());



    }
    @Test
    public void InvalidSetQuantity(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Integer quantity= product.getQuantity();
        product.setQuantity(null);
        assertEquals(quantity, product.getQuantity());



    }
    @Test
    public void NegativeSetPricePerUnit(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Double price = product.getPricePerUnit();
        product.setPricePerUnit(-0.50);
        assertEquals(price, product.getPricePerUnit());
        // boundary
        product.setPricePerUnit(-0.0001);
        assertEquals(price,product.getPricePerUnit());

    }
    @Test
    public void InvalidSetPricePerUnit(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        Double price = product.getPricePerUnit();
        product.setPricePerUnit(null);
        assertEquals(price, product.getPricePerUnit());
    }
    @Test
    public void PositiveSetPricePerUnit(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.50 ,50);
        product.setPricePerUnit(0.60);
        Double price = 0.60;
        assertEquals(price, product.getPricePerUnit());
        //boundary
        product.setPricePerUnit(0.0001);
        Double priceunit = 0.0001;
        assertEquals(priceunit, product.getPricePerUnit());


    }

    @Test
    public void InvalidSetBarCode(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.55 ,50);
        String barcode= product.getBarCode();
        product.setBarCode("");
        assertEquals(barcode, product.getBarCode());


    }
    @Test
    public void NullSetBarCode() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String barcode = product.getBarCode();
        product.setBarCode(null);
        assertEquals(barcode, product.getBarCode());
    }
    @Test
    public void PositiveSetBarCode(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.55 ,50);
        product.setBarCode("78515420");
        assertEquals("78515420", product.getBarCode());




    }
    @Test
    public void InvalidSetProductDescription(){

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String description= product.getProductDescription();
        product.setProductDescription("");
        assertEquals(description, product.getProductDescription());


    }
    @Test
    public void NullSetProductDescription() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String description= product.getProductDescription();
        product.setProductDescription(null);
        assertEquals(description, product.getProductDescription());
    }
    @Test
    public void PositiveSetProductDescription(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.55 ,50);
        product.setProductDescription("yellow");
        assertEquals("yellow", product.getProductDescription());




    }
    @Test
    public void InvalidNote(){

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String note= product.getNote();
        product.setNote("");
        assertEquals(note, product.getNote());


    }
    @Test
    public void NullSetNote() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004", 0.55, 50);
        String note= product.getNote();
        product.setNote(null);
        assertEquals(note, product.getNote());
    }
    @Test
    public void PositiveSetNote(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.55 ,50);
        product.setNote("bad");
        assertEquals("bad", product.getNote());




    }

    @Test
    public void SetLocation(){

        ProductTypeImpl product = new ProductTypeImpl(5,"shelves", "good", "red","010003004",0.55 ,50);
        product.setLocation("hall");
        assertEquals("hall", product.getLocation());
        product.setLocation("");
        assertEquals("", product.getLocation());
        product.setLocation(null);
        assertEquals(null, product.getLocation());




    }
 }

