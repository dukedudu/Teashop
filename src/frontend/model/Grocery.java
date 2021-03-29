package frontend.model;

import java.sql.Date;

public class Grocery {
    private String name;
    private int amount, duration; //added
    private Date buyingDate, expiryDate; //added

    public Grocery() {
        this.name = "";
        this.amount = 0;
        this.buyingDate = null;
        this.buyingDate = null;
    }

    public Grocery(String name, int amount, int duration, Date date) {
        this.name = name;
        this.amount = amount;
        this.buyingDate = date;
        this.duration = duration;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public Date getDate() { return buyingDate; }

    public void setDate(Date date) { this.buyingDate = date; }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
