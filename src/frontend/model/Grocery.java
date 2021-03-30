package frontend.model;

import java.sql.Date;

public class Grocery {
    private String name;
    private int amount, duration;
    private Date buyingDate, expiryDate;

    public Grocery() {
        this.name = "";
        this.amount = 0;
        this.duration = 0;
        this.buyingDate = null;
        this.expiryDate = null;
    }

    public Grocery(String name, int amount, int duration, Date buyingDate) {
        this.name = name;
        this.amount = amount;
        this.duration = duration;
        this.buyingDate = buyingDate;
        this.expiryDate = ;//buyingDate + duration;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public Date getBuyingDate() { return buyingDate; }

    public void setBuyingDate(Date buyingDate) { this.buyingDate = buyingDate; }

    public Date getExpiryDate() { return expiryDate; }

    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
