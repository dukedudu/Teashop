package frontend.model;
import java.util.*;

public class Report {

    private int ReportID, UseID, UserID, Pearl, Jelly, Lemon, Orange;
    private Date date;

    public Report(int ReportID, int UseID, int UserID, int Pearl, int Jelly, int Lemon, int Orange, Date date) {
        this.ReportID = ReportID;
        this.UseID = UseID;
        this.UserID = UserID;
        this.Pearl = Pearl;
        this.Jelly = Jelly;
        this.Lemon = Lemon;
        this.Orange = Orange;
        this.date = date;
    }
}
