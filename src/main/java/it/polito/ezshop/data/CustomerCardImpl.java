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

    /**
     * This method add the points to the current points
     *
     * @param toBeAdded points to be added, it can be both negative or positive
     * @return true if the
     */
    public boolean modifyPointsOnCard(int toBeAdded) {
        this.points += toBeAdded;
        return true;
    }

    /**
     * Method used to link the customer card to the customer
     *
     * @param customerCard the code of the customer card
     */
    public void setCustomer(String customerCard) {
        if(id != null && customerCard != null && !customerCard.isEmpty())
            id = customerCard;
    }
}