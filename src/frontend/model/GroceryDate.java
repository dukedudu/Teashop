package frontend.model;
public class GroceryDate {
    private String BuyingDate,ExpiryDate;
    private int Duration;

    public GroceryDate(String buyingDate, String expiryDate, int duration) {
        BuyingDate = buyingDate;
        ExpiryDate = expiryDate;
        Duration = duration;
    }
    public String getBuyingDate() {
        return BuyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        BuyingDate = buyingDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }
}
//link https://www.javatpoint.com/java-sql-date
//import java.sql.Date;
//public class StringToSQLDateExample {
//    public static void main(String[] args) {
//        String str="2015-03-31";
//        Date date=Date.valueOf(str);//converting string into sql date
//        System.out.println(date);
//    }
//}