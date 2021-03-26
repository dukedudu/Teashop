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
}
