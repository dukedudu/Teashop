package frontend.model;
import java.sql.Date;

public class DailyReport {
    private Date date;
    private int pearl, jelly, lemon, orange;

    public void DailyReport() {
        this.date = null;
        this.pearl = 0;
        this.jelly = 0;
        this.lemon = 0;
        this.orange = 0;
    }

    public DailyReport(Date date, int pearl, int jelly, int lemon, int orange) {
        this.date = date;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPearl(int pearl) {
        this.pearl = pearl;
    }

    public void setJelly(int jelly) {
        this.jelly = jelly;
    }

    public void setLemon(int lemon) {
        this.lemon = lemon;
    }

    public void setOrange(int orange) {
        this.orange = orange;
    }
}
