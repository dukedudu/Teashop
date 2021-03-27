package frontend.model;

public class ReportWeekDay {
    private String Date;
    private int Weekday;
    public ReportWeekDay(String date, int weekday) {
        Date = date;
        Weekday = weekday;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getWeekday() {
        return Weekday;
    }

    public void setWeekday(int weekday) {
        Weekday = weekday;
    }
}
