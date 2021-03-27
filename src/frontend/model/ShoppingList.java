package frontend.model;

import java.util.*;

public class ShoppingList {
    private Date date;
    private int id, amount;
    private String uname, gname;

    public ShoppingList() {
        //this.id = 0; set as auto increment, we can totally ignore it https://www.w3schools.com/sql/sql_autoincrement.asp
        //this.uname = ""; //remove; if shopping list contains uName, it is hard to select the correct tuple to update.
        this.gname = "";
        this.amount = 0;
        this.date = null;
    }

    public ShoppingList(String gname, int amount, Date date) {
        //this.id = id;
        //this.uname = uname;
        this.gname = gname;
        this.amount = amount;
        this.date = date;
    }

    public void setDate(Date date) { this.date = date; }

    public String getGname() { return gname; }

    public int getAmount() { return amount; }

    public Date getDate() { return date; }
}
