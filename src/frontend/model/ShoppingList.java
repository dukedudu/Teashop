package frontend.model;

import java.sql.*;

public class ShoppingList {
    private Date date;
    private int amount;
    private String gname;

    public ShoppingList() {

        this.gname = "";
        this.amount = 0;
        this.date = null;
    }

    public ShoppingList(String gname, int amount, Date date) {

        this.gname = gname;
        this.amount = amount;
        this.date = date;
    }

    public void setDate(Date date) { this.date = date; }

    public String getGname() { return gname; }

    public int getAmount() { return amount; }

    public Date getDate() { return date; }
}
