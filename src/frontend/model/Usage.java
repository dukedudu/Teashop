package frontend.model;

public class Usage {
    private String UName, RName, Date, Tea;
    private int calories;
    private int pearl;
    private int jelly;
    private int lemon;
    private int orange;

    public Usage(String UName, String RName, String date, String tea, int calories, int pearl, int jelly, int lemon, int orange) {
        this.UName = UName;
        this.RName = RName;
        Date = date;
        Tea = tea;
        this.calories = calories;
        this.pearl = pearl;
        this.jelly = jelly;
        this.lemon = lemon;
        this.orange = orange;
    }
    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getRName() {
        return RName;
    }

    public void setRName(String RName) {
        this.RName = RName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTea() {
        return Tea;
    }

    public void setTea(String tea) {
        Tea = tea;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPearl() {
        return pearl;
    }

    public void setPearl(int pearl) {
        this.pearl = pearl;
    }

    public int getJelly() {
        return jelly;
    }

    public void setJelly(int jelly) {
        this.jelly = jelly;
    }

    public int getLemon() {
        return lemon;
    }

    public void setLemon(int lemon) {
        this.lemon = lemon;
    }

    public int getOrange() {
        return orange;
    }

    public void setOrange(int orange) {
        this.orange = orange;
    }
}
