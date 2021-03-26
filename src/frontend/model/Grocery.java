package frontend.model;

import java.sql.Date;

public class Grocery {
    private String name;
    private int amount;
    private Date date;

    public Grocery() {
        this.name = "";
        this.amount = 0;
        this.date = null;
    }

    public Grocery(String name, int amount, int duration, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
