package it.polito.ezshop.data;

public class CustomerImpl implements Customer {
    private int Id;
    private String cname;
    private CustomerCardImpl ccard;

    public CustomerImpl(int id, String customerName) {
        Id = id;
        cname = customerName;
        ccard = null;
    }

    @Override
    public String getCustomerName() {
        return cname;
    }

    @Override
    public void setCustomerName(String customerName) {
        if (customerName != null && customerName.length() > 0) {
            cname = customerName;
        }
    }

    @Override
    public String getCustomerCard() {
        if (ccard != null) {
            return ccard.getCardId();
        }
        return null;
    }

    @Override
    public void setCustomerCard(String customerCard) {
        if (customerCard != null && customerCard.length() > 0) {
            if(ccard != null)
                ccard.setId(customerCard);
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
        return Id;
    }

    @Override
    public void setId(Integer id) {
        if (id > 0) {
            Id = id;
        }
    }

    @Override
    public Integer getPoints() {
        if (ccard != null)
            return ccard.getCardPoints();
        return null;
    }

    @Override
    public void setPoints(Integer points) {
        if (ccard != null)
            ccard.setCardPoints(points);
    }

    public void unlinkCustomerCard() {
        ccard = null;
    }
}