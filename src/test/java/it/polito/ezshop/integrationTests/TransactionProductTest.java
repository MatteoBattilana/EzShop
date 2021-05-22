package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.TransactionProduct;
import it.polito.ezshop.data.ProductTypeImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionProductTest {
    @Test
    public void testProductType() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
       TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        assertEquals(product, trProduct.getProductType());
    }
    @Test
    public void testPositiveSetBarCode() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        trProduct.setBarCode("010003004369");
        assertEquals("010003004369", trProduct.getBarCode());
    }


    @Test
    public void testNullSetBarCode() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        String barcode = trProduct.getBarCode();
        product.setBarCode(null);
        assertEquals(barcode, product.getBarCode());
    }

    @Test
    public void testInvalidSetBarCode() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        String barcode = trProduct.getBarCode();
        product.setBarCode("");
        assertEquals(barcode, product.getBarCode());
    }
    @Test
    public void testInvalidSetProductDescription() {

        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        String description = trProduct.getProductDescription();
       trProduct.setProductDescription("");
        assertEquals(description, trProduct.getProductDescription());
    }

    @Test
    public void testNullSetProductDescription() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        String description = trProduct.getProductDescription();
        trProduct.setProductDescription(null);
        assertEquals(description, trProduct.getProductDescription());
    }


    @Test
    public void testPositiveSetProductDescription() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        trProduct.setProductDescription("yellow");
        assertEquals("yellow", trProduct.getProductDescription());
    }
    @Test
    public void testNegativeSetAmount() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        int quantity = trProduct.getAmount();
        trProduct.setAmount(-5);
        assertEquals(quantity, trProduct.getAmount());
        //boundary
        trProduct.setAmount(-1);
        assertEquals(quantity, trProduct.getAmount());
        }

    @Test
    public void testPositiveSetAmount() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        trProduct.setAmount(200);
        assertEquals(200, trProduct.getAmount());
        //boundary
        trProduct.setAmount(0);
        assertEquals(0, trProduct.getAmount());
        //boundary
        trProduct.setAmount(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, trProduct.getAmount());
       }

    @Test
    public void testPricePerUnit() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        trProduct.setPricePerUnit(-Double.MAX_VALUE);
        assertEquals(-Double.MAX_VALUE, trProduct.getPricePerUnit(),0.1);

        trProduct.setPricePerUnit(0);
        assertEquals(0, trProduct.getPricePerUnit(),0.1);

        trProduct.setPricePerUnit(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, trProduct.getPricePerUnit(),0.1);
    }
    @Test
    public void testDiscountRate() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        trProduct.setDiscountRate(-Double.MAX_VALUE);
        assertEquals(-Double.MAX_VALUE, trProduct.getDiscountRate(),0.1);

        trProduct.setDiscountRate(0);
        assertEquals(0, trProduct.getDiscountRate(),0.1);

        trProduct.setDiscountRate(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, trProduct.getDiscountRate(),0.1);
    }

    @Test
    public void testApplyDiscountRateToProduct() {
        ProductTypeImpl product = new ProductTypeImpl(5, "shelves", "good", "red", "010003004367", 0.50, 50);
        TransactionProduct trProduct = new TransactionProduct( product, 0.50, 3, 1.20);
        assertTrue(trProduct.applyDiscountRateToProduct(-Double.MAX_VALUE));
        assertTrue(trProduct.applyDiscountRateToProduct(0));
        assertTrue(trProduct.applyDiscountRateToProduct(Double.MAX_VALUE));

    }

}




