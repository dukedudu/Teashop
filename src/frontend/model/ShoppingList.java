package frontend.model;

import java.util.*;

public class ShoppingList {
    private int SListId, Amount, UserId;
    private String GName;
    private Date date;

    public ShoppingList(int SListId, int Amount, int UserId, String GName, Date date) {
        this.SListId = SListId;
        this.Amount = Amount;
        this.UserId = UserId;
        this.GName = GName;
        this.date = date;
    }

    public int getSListId() {
        return SListId;
    }

    public void setSListId(int SListId) {
        this.SListId = SListId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
