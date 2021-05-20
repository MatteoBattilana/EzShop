package it.polito.ezshop.data;

public class CustomerImpl implements Customer {
    private int id;
    private String name;
    private CustomerCardImpl ccard;

    public CustomerImpl(int id, String customerName) {
        this.id = id;
        name = customerName;
        ccard = null;
    }

    @Override
    public String getCustomerName() {
        return name;
    }

    @Override
    public void setCustomerName(String customerName) {
        if (customerName != null && customerName.length() > 0) {
            name = customerName;
        }
    }

    @Override
    public String getCustomerCard() {
        if (ccard != null) {
            return ccard.getCustomer();
        }
        return null;
    }

    @Override
    public void setCustomerCard(String customerCard) {
        if (customerCard != null && !customerCard.isEmpty()) {
            if(ccard != null)
                ccard.setCustomer(customerCard);
            else
                ccard = new CustomerCardImpl(customerCard, 0);
        } else {
            ccard = null;
        }
    }

    public void setCustomerCard(CustomerCardImpl card) {
        ccard = card;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        if (id > 0) {
            this.id = id;
        }
    }

    @Override
    public Integer getPoints() {
        if (ccard != null)
            return ccard.getPoints();
        return null;
    }

    @Override
    public void setPoints(Integer points) {
        if (ccard != null)
            ccard.setPoints(points);
    }

    public void removeCard() {
        ccard = null;
    }
}