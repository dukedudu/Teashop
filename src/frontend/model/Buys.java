package frontend.model;

public class Buys {
    private String UName;
    private String GName;

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }

    public String getBuyingDate() {
        return BuyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        BuyingDate = buyingDate;
    }

    private String BuyingDate;

    public Buys(String UName, String GName, String buyingDate) {
        this.UName = UName;
        this.GName = GName;
        BuyingDate = buyingDate;
    }
}
