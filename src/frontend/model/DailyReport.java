package frontend.model;
import java.util.*;

public class DailyReport {
    private Date date;
    private String uname;
    private int id, pearl, jelly, lemon, orange, amount;

    public void DailyReport() {
        this.id = 0;
        this.date = null;
        this.uname = "";
        this.pearl = 0;
        this.jelly = 0;
        this.lemon = 0;
        this.orange = 0;
    }

    public DailyReport(int id, Date date, String uname, int pearl, int jelly, int lemon, int orange) {
        this.id = id;
        this.date = date;
        this.uname = uname;
        this.pearl = pearl;
        this.jelly = jelly;
        this.lemon = lemon;
        this.orange = orange;
    }

    public Date getDate() { return date; }

    public int getPearl() { return pearl; }

    public int getJelly() { return jelly; }

    public int getLemon() { return lemon; }

    public int getOrange() { return orange; }
}
