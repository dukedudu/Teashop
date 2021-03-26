package backend.database;

import java.sql.*;
import java.util.ArrayList;

import frontend.model.BranchModel;
import frontend.model.Report;
import frontend.model.ShoppingList;

public class DailyReports {

    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DailyReports() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private Report[] selectReports(String query) {
        ArrayList<Report> result = new ArrayList<Report>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Report model = new Report(rs.getInt("ReportID" ),
                        rs.getInt("UseID" ),
                        rs.getInt("UserID" ),
                        rs.getInt("Pearl" ),
                        rs.getInt("Jelly" ),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange"),
                        rs.getDate("Date"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new Report[result.size()]);
    }

    private ShoppingList[] selectLists(String query) {
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ShoppingList model = new ShoppingList(rs.getInt("SListId" ),
                        rs.getInt("Amount" ),
                        rs.getInt("UserId" ),
                        rs.getString("GName" ),
                        rs.getDate("Date"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ShoppingList[result.size()]);
    }

    public Report[] selectReportsAll() {
        return selectReports("SELECT * FROM DailyReport");
    }

    public ShoppingList[] selectShoppingListsAll() {
        return selectLists("SELECT * FROM ShoppingList");
    }

    public ShoppingList[] selectShoppingListsGrocery(String grocery) {
        return selectLists("SELECT * FROM ShoppingList WHERE GName = " + grocery);
    }

}
