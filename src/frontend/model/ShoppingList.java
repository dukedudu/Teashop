package frontend.model;

import java.sql.*;

public class ShoppingList {
    private String gname;
    private Date date;
    private int amount;

    public ShoppingList(String gname, int amount, Date date) {
        this.gname = gname;
        this.amount = amount;
        this.date = date;
    }

    public String getGname() { return gname; }
    public Date getDate() { return date; }

    public int getAmount() { return amount; }
}
