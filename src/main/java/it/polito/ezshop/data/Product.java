package it.polito.ezshop.data;

public class Product {
    String RFID;

    public Product(String RFID){
        this.RFID = RFID;
    }

    public String getRFID() {
        return RFID;
    }
}
