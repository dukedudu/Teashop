package backend.database;

import frontend.model.DailyReport;
import frontend.model.ShoppingList;

import java.sql.*;
import java.util.ArrayList;

public class DailyReports {

//    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
//    private static final String EXCEPTION_TAG = "[EXCEPTION]";
//    private static final String WARNING_TAG = "[WARNING]";
//
//    private Connection connection = null;
//
//    public DailyReports() {
//        try {
//            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private DailyReport[] selectReports(String query) {
//        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                DailyReport model = new DailyReport(
//                        rs.getDate("Date").toString(),
//                        rs.getInt("Pearl" ),
//                        rs.getInt("Jelly" ),
//                        rs.getInt("Lemon"),
//                        rs.getInt("Orange"));
//                result.add(model);
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//        return result.toArray(new DailyReport[result.size()]);
//    }
//
//    private ShoppingList[] selectLists(String query) {
//        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                ShoppingList model = new ShoppingList(
//                        rs.getString("GName" ),
//                        rs.getInt("Amount" ),
//                       rs.getDate("Date"));
//                result.add(model);
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//        return result.toArray(new ShoppingList[result.size()]);
//    }
//
//    public DailyReport[] selectReportsAll() {
//        return selectReports("SELECT * FROM DailyReport");
//    }
//
//    public DailyReport[] selectReportsAllGroceriesUsed() {
//
//       String query = "SELECT * FROM DailyReport D WHERE NOT EXISTS ((SELECT * FROM DailyReport D1) EXCEPT (SELECT * FROM DailyReport D2 WHERE D2.Pearl > 0 AND D2. Jelly > 0 AND D2.Lemon > 0 AND D2. Orange > 0))";
//       return selectReports(query);
//    }
//
//    public ShoppingList[] selectShoppingListsAll() {
//        return selectLists("SELECT * FROM ShoppingList");
//    }
//
//    public ShoppingList[] selectShoppingListsGrocery(String grocery, Date day) {
//        return selectLists("SELECT * FROM ShoppingList WHERE GName = " + grocery + "AND Date="+day);//add day
//    }
//
//    public void insertShoppingList(ShoppingList list){
//
//        try {
//            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ShoppingList VALUES (?,?,?)");
//            stmt.setString(1, list.getGname());
//            stmt.setInt(2, list.getAmount());
//            stmt.setDate(3, list.getDate());
//
//            stmt.executeUpdate();
//            connection.commit();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            //rollbackConnection();
//        }
//    }
//    public void updateShoppingList(Date date, String Gname, int amount){
//
//        try {
//            PreparedStatement stmt = connection.prepareStatement("UPDATE ShoppingList SET Gname=?, Amount=?, Date=? WHERE GName =" + Gname + "AND Date =" + date.toString());
//            stmt.setString(1, Gname);
//            stmt.setInt(2, amount);
//            stmt.setDate(3, date);
//
//            stmt.executeUpdate();
//            connection.commit();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            //rollbackConnection();
//        }
//    }
//
//    public void updateDailyReport(Date date, int pearl, int jelly, int lemon, int orange) {
//        try {
//            PreparedStatement stmt = connection.prepareStatement("UPDATE DailyReport SET reportDay=?, Pearl=?, Jelly=?, Lemon=?, Orange=? WHERE reportDay =" + date.toString());
//            stmt.setDate(1, date);
//            stmt.setInt(2, pearl);
//            stmt.setInt(3, jelly);
//            stmt.setInt(4, lemon);
//            stmt.setInt(5, orange);
//
//            stmt.executeUpdate();
//            connection.commit();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            //rollbackConnection();
//        }
//    }
}
