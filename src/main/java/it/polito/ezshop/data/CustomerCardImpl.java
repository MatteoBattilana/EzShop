package it.polito.ezshop.data;

public class CustomerCardImpl {
    private String id;
    private Integer cardpoints;

    public CustomerCardImpl(String customerCard, Integer points) {
        cardpoints = points;
        id = customerCard;
    }

    public String getCardId() {
        return id;
    }

    public Integer getCardPoints() {
        return cardpoints;
    }

    public void setCardPoints(Integer points) {
        cardpoints = points;
    }

    public void setId(String customerCard) {
        id = customerCard;
    }
}