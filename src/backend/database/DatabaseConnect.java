package backend.database;
import frontend.model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection = null;
    private int usage_index = 1, minAmount = 20;

    public void databaseConnect() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ORACLE_URL, "ora_dmy0604", "a44147163");
            System.out.println("Logged in");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void setup() {
        dropAllTableIfExists();
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Address(StreetName VARCHAR2(20), HouseNumber INT, City VARCHAR2(20), PostalCode VARCHAR2(20), PRIMARY KEY(PostalCode, StreetName, HouseNumber))");  //no connection
            stmt.executeUpdate("CREATE TABLE Users(UName VARCHAR2(20) PRIMARY KEY, Password VARCHAR2(20), StreetName VARCHAR2(20), HouseNumber INT DEFAULT 0, PostalCode VARCHAR2(20)," +
                                    "FOREIGN KEY(StreetName, HouseNumber, PostalCode) REFERENCES Address(StreetName, HouseNumber, PostalCode) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE Recipe(RName VARCHAR2(20) PRIMARY KEY, Kind VARCHAR2(20), Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE MakeRecipe(UName VARCHAR2(20), RName VARCHAR2(20), PRIMARY KEY(UName,RName), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(RName) REFERENCES Recipe)");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RName VARCHAR2(20), UsingDate DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)"); //foreign key RName?
            stmt.executeUpdate("CREATE TABLE Generates(RName VARCHAR2(20), UseID INT, PRIMARY KEY(RName, UseID), FOREIGN KEY(RName) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage(UseID) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration))");
            stmt.executeUpdate("CREATE TABLE Grocery(GName VARCHAR2(20), Amount INT DEFAULT 0, BuyingDate DATE, Duration INT, PRIMARY KEY (GName, BuyingDate), FOREIGN KEY(BuyingDate, Duration) REFERENCES GroceryDate(BuyingDate, Duration) ON DELETE CASCADE)"); //ExpiryDate in GroceryDate?
            stmt.executeUpdate("CREATE TABLE Buys(UName VARCHAR2(20), GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY(UName, GName, BuyingDate), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE DailyReport(ReportDay DATE PRIMARY KEY, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName VARCHAR2(20) NOT NULL)");;
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName VARCHAR2(20), PRIMARY KEY(SupplierId, GName), FOREIGN KEY(SupplierId) REFERENCES Supplier(SupplierId))");
            stmt.executeUpdate("CREATE TABLE ShoppingList(GName VARCHAR2(20), ListDate DATE, Amount INT NOT NULL, PRIMARY KEY (GName, ListDate))"); //UseId?);
            stmt.executeUpdate("CREATE TABLE Lists(GName VARCHAR2(20), ListDate DATE, UseID INT, PRIMARY KEY (UseID, GName, ListDate), FOREIGN KEY (GName,ListDate) REFERENCES ShoppingList(GName, ListDate) ON DELETE CASCADE)");
            stmt.executeQuery("INSERT INTO Address(StreetName, HouseNumber, City, PostalCode) VALUES ('23rd W Ave', '2341', 'Vancouver', 'V6S1H6')");
            stmt.executeQuery("INSERT INTO Users(UName, Password, StreetName, HouseNumber, PostalCode) VALUES ('test', '123123', '23rd W Ave', '2341', 'V6S1H6')");
            User u2 = new User("Lily", "234", "3rd W Ave", 2341, "Richmond", "V321H4");
            insertUser(u2);
            stmt.executeQuery("INSERT INTO Recipe(RName, Kind, Pearl, Jelly, Lemon, Orange) VALUES ('Red tea', 'Milk Tea', '20', '0', '0', '0')");
            stmt.executeQuery("INSERT INTO MakeRecipe(UName,RName) VALUES ('test', 'Red tea')");
            stmt.executeQuery("INSERT INTO Usage(UseID, RName, UsingDate, Pearl, Jelly, Lemon, Orange) VALUES ('0', 'Red tea', DATE'2021-03-29', '20', '0', '0', '0')");
            stmt.executeQuery("INSERT INTO Generates(RName, UseID) VALUES ('Red tea', '0')");
            stmt.executeQuery("INSERT INTO GroceryDate(BuyingDate, Duration, ExpiryDate) VALUES (DATE'2021-03-29', '20', DATE'2021-04-17')");
            stmt.executeQuery("INSERT INTO Grocery(GName, Amount, BuyingDate, Duration) VALUES ('Pearl', '0', DATE'2021-03-29', '20')");
            Grocery g2 = new Grocery("Jelly", 200, Date.valueOf("2021-02-16"), 10);
            insertGrocery(g2);
            stmt.executeQuery("INSERT INTO Buys(UName, GName, BuyingDate) VALUES ('test', 'Pearl', DATE'2021-03-29')");
            stmt.executeQuery("INSERT INTO DailyReport(ReportDay, Pearl, Jelly, Lemon, Orange) VALUES (DATE'2021-03-29', '20','0','0','0')");
            stmt.executeQuery("INSERT INTO Supplier(SupplierId,CompanyName) VALUES ('0', 'T&T')");
            stmt.executeQuery("INSERT INTO Supplies(SupplierId, GName) VALUES ('0', 'Pearl')");
            stmt.executeQuery("INSERT INTO ShoppingList(GName, ListDate, Amount) VALUES ('Pearl', DATE'2021-03-29', '20')");
            stmt.executeQuery("INSERT INTO Lists(GName, ListDate, UseID) VALUES ('Pearl', DATE'2021-03-29', '0')");
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: create table error");
            rollbackConnection();
        }
    }

    public boolean selectPassword(User user) {
        boolean result = false;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password FROM Users WHERE UName = '" + user.getName() + "'");
            if(rs.next()) {
                String truePassword = rs.getString("Password");
                result = (user.getPassword().equals(truePassword));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }
    public void insertUser(User user) {
        insertAddress(user.getStreet(),user.getHouseNumber(),user.getCity(),user.getCode());
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStreet());
            ps.setInt(4, user.getHouseNumber());
            ps.setString(5, user.getCode());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void insertAddress(String streetName, int houseNumber, String city, String pc){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Address VALUES (?,?,?,?)");
            ps.setString(1, streetName);
            ps.setInt(2, houseNumber);
            ps.setString(3, city);
            ps.setString(4, pc);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void insertRecipe(Recipe recipe) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Recipe VALUES (?,?,?,?,?,?)");
            ps.setString(1, recipe.getName());
            ps.setString(2, recipe.getKind());
            ps.setInt(3, recipe.getPearl());
            ps.setInt(4, recipe.getJelly());
            ps.setInt(5, recipe.getLemon());
            ps.setInt(6, recipe.getOrange());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void updateRecipe(Recipe recipe) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Recipe SET Pearl=?, Jelly=?, Lemon=?, Orange=? WHERE RName='" + recipe.getName() + "'");
            ps.setInt(1, recipe.getPearl());
            ps.setInt(2, recipe.getJelly());
            ps.setInt(3, recipe.getLemon());
            ps.setInt(4, recipe.getOrange());
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Recipe " + recipe.getName() + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public Recipe[] selectAllRecipe() {
        ArrayList<Recipe> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Recipe");
            while (rs.next()) {
                Recipe temp = new Recipe(rs.getString("RName"),
                        rs.getInt("Pearl"),
                        rs.getInt("Jelly"),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange")
                );
                result.add(temp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Recipe[result.size()]);
    }
    public Recipe selectRecipeByRname(String rname) {
        Recipe result = new Recipe();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Recipe WHERE RName = '" + rname + "'");
            while(rs.next()){
                result = new Recipe(rs.getString("RName"),
                        rs.getInt("Pearl"),
                        rs.getInt("Jelly"),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }
    public Recipe[] selectRecipeByUname(String uname) {
        ArrayList<Recipe> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Recipe R, Users U, MakeRecipe MR WHERE MR.RName = R.RName AND MR.UName = U.UName AND U.UName = '" + uname + "'");
            while (rs.next()) {
                result.add(new Recipe(
                        rs.getString("RName"),
                        rs.getInt("Pearl"),
                        rs.getInt("Jelly"),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Failed to select recipe by uname");
            rollbackConnection();
        }
        return result.toArray(new Recipe[result.size()]);
    }
    public boolean makeRecipe(String name, Date today) {
        boolean result = false;
        System.out.println(today.toString());
        today = Date.valueOf(today.toString());
        Recipe recipe = selectRecipeByRname(name);
        deleteWithZero();
        Grocery pearlStock = findEarliestAmount("Pearl");
        Grocery jellyStock = findEarliestAmount("Jelly");
        Grocery lemonStock = findEarliestAmount("Lemon");
        Grocery orangeStock = findEarliestAmount("Orange");

        if (checkAddToList(recipe.getPearl(), pearlStock, "Pearl", today) &&
                checkAddToList(recipe.getJelly(), jellyStock, "Jelly", today) &&
                checkAddToList(recipe.getLemon(), lemonStock, "Lemon", today) &&
                checkAddToList(recipe.getOrange(), orangeStock, "Orange", today)) {
            pearlStock.setAmount(pearlStock.getAmount() - recipe.getPearl());
            updateGrocery(pearlStock);
            jellyStock.setAmount(jellyStock.getAmount() - recipe.getJelly());
            updateGrocery(jellyStock);
            lemonStock.setAmount(lemonStock.getAmount() - recipe.getLemon());
            updateGrocery(lemonStock);
            orangeStock.setAmount(orangeStock.getAmount() - recipe.getOrange());
            updateGrocery(orangeStock);
            insertUsage(recipe, today);
            System.out.println("Success! Enjoy!");
            Grocery[] rest = {pearlStock,jellyStock,lemonStock,orangeStock};
            checkCutOff(rest); // if the remaining amount is less than min required, dispose that tuple.
            result = true;
        } else {
            System.out.println("Missing Ingredients, help you add to today's shopping list");
        }
        return result;
    }

    public void deleteWithZero() {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Grocery WHERE amount = 0");
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + "No tuple with zero amount");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public Grocery findEarliestAmount(String name){
        Grocery result = new Grocery();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT G.GName, MIN(GD.ExpiryDate) as Target FROM Grocery G, GroceryDate GD WHERE G.BuyingDate=GD.BuyingDate AND G.Duration=GD.Duration GROUP BY G.GName HAVING G.GName = '" +name+ "'");
            while (rs.next()) {
                result = selectGroceryByExpiryDate(name,rs.getDate("Target"));
            }
            rs.close();
            stmt.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }
    public boolean checkAddToList(int needAmount, Grocery stock, String gname, Date today) {
        boolean enough = false;
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        if (needAmount <= stock.getAmount()) {
            enough = true;
        } else {
            int shortAmount = needAmount;
            stock.setAmount(0);
            updateGrocery(stock);
            deleteWithZero();
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM ShoppingList WHERE Gname = '" + gname + "' AND ListDate= DATE'" + today + "'");
                while (rs.next()) {
                    ShoppingList temp = new ShoppingList(rs.getString("Gname"),
                            rs.getInt("Amount"),
                            rs.getDate("Date"));
                    result.add(temp);
                }
            } catch (SQLException e) {
                System.out.println(EXCEPTION_TAG + " " + e.getMessage());
                rollbackConnection();
            }
            if (result.size() == 0) {//today we have not buy this grocery yet
                ShoppingList list = new ShoppingList(gname, shortAmount, today);
                insertShoppingList(list);
            } else {//update today's existing
                updateShoppingList(today, gname, shortAmount + result.get(0).getAmount());
            }
        }
        System.out.println(enough);
        return enough;
    }
    public void checkCutOff(Grocery[] list){
        for(Grocery g:list){
            if (g.getAmount()<minAmount){
                g.setAmount(0);
                updateGrocery(g);
            }
        }
        deleteWithZero();
    }
    public String recommendKind(String gname){
        String result = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT AVG("+gname+") as temp, Kind FROM Recipe R GROUP BY Kind HAVING AVG("+gname+") >= all(SELECT AVG(R."+gname+") FROM Recipe R GROUP BY R.Kind)");
            rs.next();
            result = rs.getString("Kind");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }
    public void insertGrocery(Grocery grocery) {
        insertGroceryDate(grocery.getBuyingDate(),grocery.getDuration());
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Grocery VALUES (?,?,?,?)");
            ps.setString(1, grocery.getName());
            ps.setInt(2, grocery.getAmount());
            ps.setDate(3, grocery.getBuyingDate());
            ps.setInt(4, grocery.getDuration());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void insertGroceryDate(Date buyingDate, int Duration){
        if(selectGroceryDate(buyingDate,Duration)){
            return;
        }
        else{
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO GroceryDate(BuyingDate, Duration, ExpiryDate) VALUES (?, ?, ?)");
                ps.setDate(1, buyingDate);
                ps.setInt(2, Duration);
                ps.setDate(3, Grocery.addDays(buyingDate,Duration));
                ps.executeUpdate();
                connection.commit();
                ps.close();
                System.out.println("insert GD ok");
            } catch (SQLException e) {
                System.out.println(EXCEPTION_TAG + " " + e.getMessage());
                rollbackConnection();
            }
        }
    }
    public boolean selectGroceryDate(Date buyingDate, int Duration){
        int length = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM GroceryDate WHERE BUYINGDATE= ?AND DURATION=?");
            ps.setDate(1, buyingDate);
            ps.setInt(2, Duration);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    length++;
            }
            rs.close();
            System.out.println(length);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return  (length > 0);
    }
    public void updateGrocery(Grocery grocery) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Grocery SET Amount=?, Duration=? WHERE GName='" + grocery.getName() + "'" + " AND BuyingDate=?");
            ps.setInt(1, grocery.getAmount());
            ps.setInt(2, grocery.getDuration());
            ps.setDate(3, grocery.getBuyingDate());
            ps.executeUpdate();
            connection.commit();
            ps.close();
            System.out.println(123123);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public Grocery[] selectAllGrocery() {
        ArrayList<Grocery> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Grocery");
            while (rs.next()) {
                Grocery temp = new Grocery(rs.getString("GName"),
                        rs.getInt("Amount"),
                        rs.getDate("BuyingDate"),
                        rs.getInt("Duration")
                );
                result.add(temp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return  result.toArray(new Grocery[result.size()]);
    }
    public Grocery selectGrocery(String name, Date date) {
        Grocery result = new Grocery();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Grocery WHERE GName = '"+ name +"'"+ " AND BuyingDate= DATE'" + date.toString() + "'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = new Grocery(rs.getString("GName"),
                    rs.getInt("Amount"),
                    rs.getDate("BuyingDate"),
                    rs.getInt("Duration")
            );
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }
    public Grocery selectGroceryByExpiryDate(String name, Date date) {
        Grocery result = new Grocery();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT GName, Amount, GROCERY.BUYINGDATE, GROCERY.DURATION FROM Grocery, GroceryDate WHERE GName = '" + name + "'" + " AND ExpiryDate=?");
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = new Grocery(rs.getString("GName"),
                        rs.getInt("Amount"),
                        rs.getDate("BuyingDate"),
                        rs.getInt("Duration")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Failed to select recipe by uname");
            rollbackConnection();
        }
        return result;
    }
    public Grocery[] orderGroceryByDate() {
        ArrayList<Grocery> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Grocery ORDER BY BuyingDate");
            while (rs.next()) {
                Grocery temp = new Grocery(rs.getString("GName"),
                        rs.getInt("Amount"),
                        rs.getDate("BuyingDate"),
                        rs.getInt("Duration")
                );
                result.add(temp);
                System.out.println("backend: " + temp.getName() + " " + temp.getBuyingDate());
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Grocery[result.size()]);
    }
    public Grocery[] sumGroceryAmount(String name) {
        ArrayList<Grocery> result = new ArrayList<>();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GName, SUM(Amount) as Total FROM Grocery g GROUP BY GName");
            while (rs.next()) {
                Grocery temp = new Grocery(rs.getString("GName"),
                        rs.getInt("Total"));
                result.add(temp);
            }
            rs.close();
            stmt.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Grocery[result.size()]);
    }
    public void insertUsage(Recipe recipe, Date date) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO  Usage VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, usage_index);
            ps.setString(2, recipe.getName());
            ps.setDate(3, date);
            ps.setInt(4, recipe.getPearl());
            ps.setInt(5, recipe.getJelly());
            ps.setInt(6, recipe.getLemon());
            ps.setInt(7, recipe.getOrange());
            ps.executeUpdate();
            connection.commit();
            ps.close();
            usage_index++;
            dailyReportUpdate(recipe, date);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void dailyReportUpdate(Recipe recipe, Date date){
        String cmd = "SELECT * FROM DailyReport WHERE reportDay = DATE'"+date.toString()+"'";
        DailyReport[] r = selectReports(cmd);
        if (r.length == 0){
            DailyReport d1 = new DailyReport(date,recipe.getPearl(),recipe.getJelly(),recipe.getLemon(),recipe.getOrange());
            insertDailyReport(d1);
        }
        else {
            DailyReport target = r[0];
            target.setPearl(target.getPearl()+recipe.getPearl());
            target.setJelly(target.getJelly()+recipe.getJelly());
            target.setOrange(target.getOrange()+recipe.getOrange());
            target.setLemon(target.getLemon()+recipe.getLemon());
            updateDailyReport(date,target);
        }
    }
    private DailyReport[] selectReports(String query) {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DailyReport model = new DailyReport(
                        rs.getDate("ReportDay"),
                        rs.getInt("Pearl" ),
                        rs.getInt("Jelly" ),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new DailyReport[result.size()]);
    }
    public void insertDailyReport(DailyReport d) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DailyReport VALUES (?,?,?,?,?)");
            ps.setObject(1, d.getDate());
            ps.setInt(2, d.getPearl());
            ps.setInt(3, d.getJelly());
            ps.setInt(4, d.getLemon());
            ps.setInt(5, d.getOrange());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "fail to insert daily report");
            rollbackConnection();
        }
    }
    public void updateDailyReport(Date date, DailyReport report) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DailyReport SET ReportDay=?, Pearl=?, Jelly=?, Lemon=?, Orange=? WHERE ReportDay = DATE'" + date.toString() + "'");
            ps.setDate(1, date);
            ps.setInt(2, report.getPearl());
            ps.setInt(3, report.getJelly());
            ps.setInt(4, report.getLemon());
            ps.setInt(5, report.getOrange());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public DailyReport[] selectAllReport() {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DailyReport");
            while (rs.next()) {
                DailyReport model = new DailyReport(
                        rs.getDate("ReportDay"),
                        rs.getInt("Pearl" ),
                        rs.getInt("Jelly" ),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new DailyReport[result.size()]);
    }
    public DailyReport[] selectReportByGname(String gname) {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DailyReport WHERE " + gname + ">0");
            while (rs.next()) {
                DailyReport model = new DailyReport(
                        rs.getDate("ReportDay"),
                        rs.getInt("Pearl" ),
                        rs.getInt("Jelly" ),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new DailyReport[result.size()]);
    }
    public DailyReport[] selectReportWithEvery() {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DailyReport WHERE ReportDay NOT IN ((SELECT ReportDay FROM DailyReport) MINUS (SELECT ReportDay FROM DailyReport WHERE Pearl > 0 AND Jelly > 0 AND Lemon > 0 AND Orange > 0))");
            while (rs.next()) {
                DailyReport model = new DailyReport(
                        rs.getDate("ReportDay"),
                        rs.getInt("Pearl" ),
                        rs.getInt("Jelly" ),
                        rs.getInt("Lemon"),
                        rs.getInt("Orange"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new DailyReport[result.size()]);
    }

    public void insertShoppingList(ShoppingList list) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ShoppingList VALUES (?,?,?)");
            stmt.setString(1, list.getGname());
            stmt.setDate(2, list.getDate());
            stmt.setInt(3, list.getAmount());
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateShoppingList(Date date, String Gname, int amount){
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE ShoppingList SET Gname=?, Amount=?, ListDate=? WHERE GName ='" + Gname + "' AND ListDate = '" + date + "'");
            stmt.setString(1, Gname);
            stmt.setInt(2, amount);
            stmt.setDate(3, date);
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public ShoppingList[] selectAllList() {
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ShoppingList");
            while (rs.next()) {
                ShoppingList model = new ShoppingList(
                        rs.getString("GName" ),
                        rs.getInt("Amount" ),
                        rs.getDate("ListDate"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ShoppingList[result.size()]);
    }
    public ShoppingList[] selectListByGname(String gname) {
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ShoppingList WHERE GName ='" + gname + "'");
            while (rs.next()) {
                ShoppingList model = new ShoppingList(
                        rs.getString("GName" ),
                        rs.getInt("Amount" ),
                        rs.getDate("ListDate"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ShoppingList[result.size()]);
    }
    public ShoppingList[] selectListByDate(Date date1, Date date2) {
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ShoppingList WHERE ListDate>'" + date1 + "' AND ListDate<'" + date2 + "'");
            while (rs.next()) {
                ShoppingList model = new ShoppingList(
                        rs.getString("GName" ),
                        rs.getInt("Amount" ),
                        rs.getDate("ListDate"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ShoppingList[result.size()]);
    }
    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void dropAllTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");
            while (rs.next()) {
                Statement stmt2 = connection.createStatement();
                switch (rs.getString(1).toLowerCase()) {
                    case "address": { stmt2.execute("DROP TABLE Address CASCADE CONSTRAINTS"); break; }
                    case "users": { stmt2.execute("DROP TABLE Users CASCADE CONSTRAINTS"); break; }
                    case "recipe": { stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS"); break; }
                    case "makerecipe": { stmt2.execute("DROP TABLE MakeRecipe CASCADE CONSTRAINTS"); break; }
                    case "usage": { stmt2.execute("DROP TABLE Usage CASCADE CONSTRAINTS"); break; }
                    case "generates": { stmt2.execute("DROP TABLE Generates CASCADE CONSTRAINTS"); break; }
                    case "grocerydate": { stmt2.execute("DROP TABLE GroceryDate CASCADE CONSTRAINTS"); break; }
                    case "grocery": { stmt2.execute("DROP TABLE Grocery CASCADE CONSTRAINTS"); break; }
                    case "buys": { stmt2.execute("DROP TABLE Buys CASCADE CONSTRAINTS"); break; }
                    case "dailyreport": { stmt2.execute("DROP TABLE DailyReport CASCADE CONSTRAINTS"); break; }
                    case "supplier": { stmt2.execute("DROP TABLE Supplier CASCADE CONSTRAINTS"); break; }
                    case "supplies": { stmt2.execute("DROP TABLE Supplies CASCADE CONSTRAINTS"); break; }
                    case "shoppinglist": { stmt2.execute("DROP TABLE ShoppingList CASCADE CONSTRAINTS"); break; }
                    case "lists": { stmt2.execute("DROP TABLE Lists CASCADE CONSTRAINTS"); break; }
                    default: System.out.println(rs.getString(1).toLowerCase());
                }
                stmt2.close();
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "drop table error");
        }
    }
}