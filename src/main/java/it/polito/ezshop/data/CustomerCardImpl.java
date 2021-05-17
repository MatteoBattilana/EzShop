package it.polito.ezshop.data;

public class CustomerCardImpl {
    private String id;
    private int points;

    public CustomerCardImpl(String customerCard, Integer points) {
        this.points = points;
        this.id = customerCard;
    }

    public String getCustomer() {
        return id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        if(points >= 0)
        this.points = points;
    }

    public boolean modifyPointsOnCard(int toBeAdded) {
        this.points += toBeAdded;
        return true;
    }

    public void setCustomer(String customerCard) {
        if(id != null && !customerCard.isEmpty())
            id = customerCard;
    }
}