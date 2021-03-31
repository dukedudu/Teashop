package backend.database;
import frontend.model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection = null;
    private int usage_index = 0, minAmount = 20;

    public void databaseConnect() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ORACLE_URL, "ora_sunjingy", "a48346902");
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
            stmt.executeUpdate("CREATE TABLE Users(UName VARCHAR2(20) PRIMARY KEY, Password VARCHAR2(20), StreetName VARCHAR2(20), HouseNumber INT DEFAULT 0, City VARCHAR2(20), PostalCode VARCHAR2(20))");
//            System.out.println("Table User created");
            stmt.executeUpdate("CREATE TABLE Recipe(RName VARCHAR2(20) PRIMARY KEY, Kind VARCHAR2(20), Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
//            System.out.println("Table Recipe created");
            //                   "FOREIGN KEY(PostalCode) REFERENCES Address(PostalCode) ON DELETE CASCADE, FOREIGN KEY(StreetName) REFERENCES Address(StreetName) ON DELETE CASCADE, FOREIGN KEY(House#) REFERENCES Address(House#) ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Address(PostalCode varchar2(20),StreetName varchar2(20),House# INT, City varchar2(20), PRIMARY KEY(PostalCode, StreetName, House#))"); //no connection
//            System.out.println("Table Address created");
            stmt.executeUpdate("CREATE TABLE Grocery(GName VARCHAR2(20), Amount INT DEFAULT 0, BuyingDate DATE, PRIMARY KEY (GName,BuyingDate), Duration INT, ExpiryDate DATE)");
            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration))");
            stmt.executeUpdate("CREATE TABLE Buys(UName VARCHAR2(20), GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY(UName, GName, BuyingDate), " +
                    "FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
            //                    ",Amount INT DEFAULT 0, BuyingDate DATE, Duration INT," +
//                    "FOREIGN KEY(UserId) REFERENCES User(UserId) ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery(GName) ON DELETE CASCADE, FOREIGN KEY(BuyingDate) REFERENCES Grocery(BuyingDate) ON DELETE CASCADESystem.out.println("Table Recipe passed\n");
            stmt.executeUpdate("CREATE TABLE MakeRecipe(UName VARCHAR2(20), RName VARCHAR2(20), PRIMARY KEY(UName,RName), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(RName) REFERENCES Recipe)");
//            System.out.println("Table MakeRecipe created");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RName VARCHAR2(20), UsingDate DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE Generates(RName VARCHAR2(20), UseID INT, PRIMARY KEY(RName, UseID), FOREIGN KEY(RName) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage(UseID) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE DailyReport(ReportDay DATE PRIMARY KEY, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            //"FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);"
//            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName VARCHAR2(20) NOT NULL)");
//            System.out.println("Table Supplier created");
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY (SupplierId))");
//                                    "FOREIGN KEY(SupplierId) REFERENCES Supplier(SupplierId), FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
//            System.out.println("Table Supplies created");
            stmt.executeUpdate("CREATE TABLE ShoppingList(GName CHAR(20), Amount INT NOT NULL, ListDate DATE, PRIMARY KEY (GName,ListDate))");
//            System.out.println("Table ShoppingList created");
            //                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName,ListDate) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Lists(GName CHAR(20), ListDate DATE, UseID INT, PRIMARY KEY (UseID, GName, ListDate))");
//            System.out.println("Table List created");
//                                    "FOREIGN KEY (GName,ListDate) REFERENCES ShoppingList(GName, ListDate) ON DELETE CASCADE, FOREIGN KEY (UseID) REFERENCES Usage(UseID) ON DELETE CASCADE)");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: create table error");
        }
        User u1 = new User("Sam", "123", "23rd W Ave", 2341, "Vancouver", "V6S1H6");
//        User u2 = new User("Lily", "234", "23rd W Ave", 2341, "Vancouver", "V6S1H6");
        insertUser(u1);
        Recipe r1 = new Recipe("Red tea", "Milk Tea", 20, 0, 0, 0);
        Recipe r2 = new Recipe("Pearl Milk Tea", "Milk Tea", 20, 0, 0, 0);
        insertRecipe(r1);
        insertRecipe(r2);
        Grocery g1 = new Grocery("Pearl", 0, 20, Date.valueOf("2021-03-29")); //天的运算
        insertGrocery(g1);
        Grocery g2 = new Grocery("Pearl", 50, 20, Date.valueOf("2021-03-30"));
        insertGrocery(g2);
        DailyReport d1 = new DailyReport(Date.valueOf("2021-03-29"), 20, 0, 0, 0);
        insertDailyReport(d1);
        DailyReport d2 = new DailyReport(Date.valueOf("2021-03-30"), 20, 0, 0, 0);
        insertDailyReport(d2);
        ShoppingList s1 = new ShoppingList("Pearl", 20, Date.valueOf("2021-03-29"));
        insertShoppingList(s1);
        ShoppingList s2 = new ShoppingList("Pearl", 20, Date.valueOf("2021-03-30"));
        insertShoppingList(s2);
        insertMakeRecipe("Sam", "Red tea");
        insertSupplier(0, "T&T");
        insertSupplier(1, "Coco");
    }

    public void insertSupplies(int id, String Name) {
        try {
            Statement stmt = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplies (SupplierId,GName) VALUES ('%d', '%s')", id, Name);
            stmt.executeQuery(cmd);
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplies");
            rollbackConnection();
        }
    }

    public void insertSupplier(int id, String Name) {
        try {
            Statement stmt = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplier (SupplierId,CompanyName) VALUES ('%d', '%s')", id, Name);
            stmt.executeQuery(cmd);
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplier");
            rollbackConnection();
        }
    }

    public void insertMakeRecipe(String UName, String RName) {
        try {
            Statement stmt = connection.createStatement();
            String cmd = String.format("INSERT INTO MakeRecipe (UName,RName) VALUES ('%s', '%s')", UName, RName);
            stmt.executeQuery(cmd);
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert make recipe");
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

//    public boolean changePassword(String UName, String newPassword, String confirmPassword) {
//        if (!newPassword.equals(confirmPassword)) {
//            System.out.println("confirmPassword should be same as newPassword.");
//            return false;
//        }
//        try {
//            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET Password = ? WHERE UName = ?");
//            ps.setString(1, newPassword);
//            ps.setString(2, UName);
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                System.out.println(WARNING_TAG + " User does not exist!");
//                return false;
//            }
//            connection.commit();
//            ps.close();
//            return true;
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//        return false;
//    }

    public void insertUser(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?,?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStreet());
            ps.setInt(4, user.getHouseNumber());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getCode());
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
                //MainMenu.makeWarningDialog(WARNING_TAG + " Tenant " + sin + " does not exist!");
                System.out.println(WARNING_TAG + " Recipe " + recipe.getName() + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            //MainMenu.makeWarningDialog(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

//    public void deleteRecipe(String name) { }

    public Recipe[] selectAllRecipe() {
        ArrayList<Recipe> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Recipe");
            while (rs.next()) {
                Recipe temp = new Recipe(rs.getString("RName"),
                        rs.getString("Kind"),
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
            rs.next();
            result = new Recipe(rs.getString("RName"),
                    rs.getString("Kind"),
                    rs.getInt("Pearl"),
                    rs.getInt("Jelly"),
                    rs.getInt("Lemon"),
                    rs.getInt("Orange")
            );
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
                        rs.getString("Kind"),
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

//    public Recipe[] selectRecipeByKind(String kind) {
//        ArrayList<Recipe> recipes = new ArrayList<>();
//        try {
//            Statement stmt1 = connection.createStatement();
//            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE Kind=" + kind);
//            while (rs1.next()) {
//                Recipe temp = new Recipe(rs1.getString("name"),
//                        rs1.getString("Kind"),
//                        rs1.getInt("pearl"),
//                        rs1.getInt("jelly"),
//                        rs1.getInt("lemon"),
//                        rs1.getInt("orange")
//                );
//                recipes.add(temp);
//            }
//            rs1.close();
//            stmt1.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//        return recipes.toArray(new Recipe[recipes.size()]);
//    }

    public void makeRecipe(String name, Date today) {
        Recipe recipe = selectRecipeByRname(name); //
        deleteWithZero();
        Grocery pearlStock = findEarliestAmount("Pearl");
        Grocery jellyStock = findEarliestAmount("Jelly");
        Grocery lemonStock = findEarliestAmount("Lemon");
        Grocery orangeStock = findEarliestAmount("Orange");

        if (checkAddToList(recipe.getPearl(), pearlStock, "Pearl", today) &&
                checkAddToList(recipe.getJelly(), jellyStock, "Jelly", today) &&
                checkAddToList(recipe.getLemon(), lemonStock, "Lemon", today) &&
                checkAddToList(recipe.getPearl(), orangeStock, "Orange", today)) {
            //to Eric:
            pearlStock.setAmount(pearlStock.getAmount() - recipe.getPearl());
            System.out.println(pearlStock.getAmount() + " " + recipe.getPearl());
            //Grocery pearl = new Grocery("Pearl", pearlStock.getAmount() - recipe.getPearl(), 5, (Date) pearlStock.getExpiryDate());
            updateGrocery(pearlStock);
            //Grocery jelly = new Grocery("Jelly", jellyStock.getAmount() - recipe.getJelly(), 5, (Date) jellyStock[1]);
            jellyStock.setAmount(jellyStock.getAmount() - recipe.getJelly());
            updateGrocery(jellyStock);
            //Grocery lemon = new Grocery("Lemon", lemonStock.getAmount( - recipe.getLemon(), 5, (Date) lemonStock[1]);
            lemonStock.setAmount(lemonStock.getAmount() - recipe.getLemon());
            updateGrocery(lemonStock);
            //Grocery orange = new Grocery("Orange", orangeStock.getAmount() - recipe.getOrange(), 5, (Date) orangeStock[1]);
            orangeStock.setAmount(orangeStock.getAmount() - recipe.getOrange());
            updateGrocery(orangeStock);
            //insert usage --- daily report
            insertUsage(recipe, today);
            System.out.println("Success! Enjoy!");
            Grocery[] rest = {pearlStock,jellyStock,lemonStock,orangeStock};
            checkCutOff(rest); // if the remaining amount is less than min required, dispose that tuple.
        } else {
            System.out.println("Missing Ingredients, help you add to today's shopping list");
        }
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

    public boolean checkAddToList(int needAmount, Grocery stock, String Gname, Date today) {
        boolean enough = false;
        if (needAmount <= stock.getAmount()) {
            enough = true;
        } else {
            int shortAmount = needAmount;
            //to Eric
            stock.setAmount(0);
            updateGrocery(stock);
            deleteWithZero();
            String cmd = "SELECT * FROM ShoppingList WHERE Gname = '" + Gname + "' AND ListDate = DATE'" + today.toString() + "'";
            ShoppingList[] glist = selectLists(cmd);
            if (glist.length == 0) {//today we have not buy this grocery yet
                ShoppingList list = new ShoppingList(Gname, shortAmount, today);
                insertShoppingList(list);
            } else {//update today's existing
                updateShoppingList(today, Gname, shortAmount + glist[0].getAmount());
            }
        }
        System.out.println(enough);
        return enough;
    }

//    public Recipe[] dietSelection(int toppingNums){
//        try {
//            PreparedStatement ps = connection.prepareStatement("SELECT RName, Pearl, Jelly, Orange, Lemon, Pearl+Jelly+Orange+Lemon AS \"Total\" INTO #RecipeSum FROM Recipe");
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                System.out.println(WARNING_TAG + "No tuple with zero amount");
//            }
//            connection.commit();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }

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

    public void deleteWithZero() {
        //todo: delete all tuples with 0 amount
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

    public Grocery findEarliestAmount(String name){ //return amount and date!!
        Grocery result = new Grocery();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GName, MIN(ExpiryDate) as Target FROM Grocery GROUP BY GName HAVING GName = '" +name+ "'");
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

    public void insertGrocery(Grocery grocery) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Grocery VALUES (?,?,?,?,?)");
            ps.setString(1, grocery.getName());
            ps.setInt(2, grocery.getAmount());
            ps.setDate(3, grocery.getBuyingDate());
            ps.setInt(4, grocery.getDuration());
            ps.setDate(5, grocery.getExpiryDate());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateGrocery(Grocery grocery) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Grocery SET Amount=?, Duration=?, ExpiryDate=? WHERE GName='" + grocery.getName() + "'" + " AND BuyingDate=?");
            ps.setInt(1, grocery.getAmount());
            ps.setInt(2, grocery.getDuration());
            ps.setDate(3, grocery.getExpiryDate());
            ps.setDate(4, grocery.getBuyingDate());
            ps.executeUpdate();
            connection.commit();
            ps.close();
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
                        rs.getInt("Duration"),
                        rs.getDate("BuyingDate")
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Grocery WHERE GName = '"+ name +"'"+ " AND BuyingDate='" + date + "'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = new Grocery(rs.getString("GName"),
                    rs.getInt("Amount"),
                    rs.getInt("Duration"),
                    rs.getDate("BuyingDate")
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
        Grocery g = new Grocery();
        try {
            PreparedStatement stmt1 = connection.prepareStatement("SELECT * FROM Grocery WHERE GName = '" + name + "'" + "AND ExpiryDate = ?");
            stmt1.setDate(1, date);
            ResultSet rs1 = stmt1.executeQuery();
            //ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Grocery WHERE GName = '"+ name +"'"+ "AND ExpiryDate = ?");
            while (rs1.next()) {
                g = new Grocery(rs1.getString("GName"),
                        rs1.getInt("Amount"),
                        rs1.getInt("Duration"),
                        rs1.getDate("BuyingDate")
                );
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Failed to select recipe by uname");
            rollbackConnection();
        }
        return g;
    }

    public Grocery[] orderGroceryByDate() {
        ArrayList<Grocery> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Grocery ORDER BY BuyingDate");
            while (rs.next()) {
                Grocery temp = new Grocery(rs.getString("GName"),
                        rs.getInt("Amount"),
                        rs.getInt("Duration"),
                        rs.getDate("BuyingDate")
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
//                System.out.println(rs.getString("GName")+rs.getInt("Total"));
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Usage VALUES (?,?,?,?,?,?,?)");
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
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    //Eric comments
//    private DailyReport[] selectReports(String query) {
//        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                DailyReport model = new DailyReport(
//                        rs.getDate("ReportDay"),
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

//    private ShoppingList[] selectLists(String query) {
//        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                ShoppingList model = new ShoppingList(
//                        rs.getString("GName" ),
//                        rs.getInt("Amount" ),
//                        rs.getDate("ListDate"));
//                result.add(model);
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//        return result.toArray(new ShoppingList[result.size()]);
//    }

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

    public void updateDailyReport(Date date, int pearl, int jelly, int lemon, int orange) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DailyReport SET ReportDay=?, Pearl=?, Jelly=?, Lemon=?, Orange=? WHERE ReportDay =" + date);
            ps.setDate(1, date);
            ps.setInt(2, pearl);
            ps.setInt(3, jelly);
            ps.setInt(4, lemon);
            ps.setInt(5, orange);
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
//        return selectReports("SELECT * FROM DailyReport");
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
//        if (name.equals("Pearl")) {
//            return selectReports("SELECT * FROM DailyReport WHERE Pearl > 0");
//        }
//        else if (name.equals("Jelly")) {
//            return selectReports("SELECT * FROM DailyReport WHERE Jelly > 0");
//        }
//        else if (name.equals("Lemon")) {
//            return selectReports("SELECT * FROM DailyReport WHERE Lemon > 0");
//        }
//        else {
//            return selectReports("SELECT * FROM DailyReport WHERE Orange > 0");
//        }
    }

    public DailyReport[] selectReportWithEvery() {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DailyReport D WHERE NOT EXISTS ((SELECT * FROM DailyReport D1) MINUS (SELECT * FROM DailyReport D2 WHERE D2.Pearl > 0 AND D2.Jelly > 0 AND D2.Lemon > 0 AND D2.Orange > 0))");
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
//        String query = "SELECT * FROM DailyReport D WHERE NOT EXISTS ((SELECT * FROM DailyReport D1) MINUS (SELECT * FROM DailyReport D2 WHERE D2.Pearl > 0 AND D2.Jelly > 0 AND D2.Lemon > 0 AND D2.Orange > 0))";
//        return selectReports(query);
    }

    public void insertShoppingList(ShoppingList list) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ShoppingList VALUES (?,?,?)");
            stmt.setString(1, list.getGname());
            stmt.setInt(2, list.getAmount());
            stmt.setDate(3, list.getDate());
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
            PreparedStatement stmt = connection.prepareStatement("UPDATE ShoppingList SET Gname=?, Amount=?, ListDate=? WHERE GName ='" + Gname + "' AND ListDate = " + date);
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
//        return selectLists("SELECT * FROM ShoppingList");
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
//        return selectLists("SELECT * FROM ShoppingList WHERE GName ='" + gname + "'");
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
//        return selectLists("SELECT * FROM ShoppingList WHERE ListDate>'" + date1 + "' AND ListDate<'" + date2 + "'");
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
                    case "users": { stmt2.execute("DROP TABLE Users CASCADE CONSTRAINTS"); break; }
                    case "recipe": { stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS"); break; }
                    case "address": { stmt2.execute("DROP TABLE Address CASCADE CONSTRAINTS"); break; }
                    case "grocery": { stmt2.execute("DROP TABLE Grocery CASCADE CONSTRAINTS"); break; }
                    case "grocerydate": { stmt2.execute("DROP TABLE GroceryDate CASCADE CONSTRAINTS"); break; }
                    case "makerecipe": { stmt2.execute("DROP TABLE MakeRecipe CASCADE CONSTRAINTS"); break; }
                    case "buys": { stmt2.execute("DROP TABLE Buys CASCADE CONSTRAINTS"); break; }
                    case "usage": { stmt2.execute("DROP TABLE Usage CASCADE CONSTRAINTS"); break; }
                    case "generates": { stmt2.execute("DROP TABLE Generates CASCADE CONSTRAINTS"); break; }
                    case "dailyreport": { stmt2.execute("DROP TABLE DailyReport CASCADE CONSTRAINTS"); break; }
                    case "supplier": { stmt2.execute("DROP TABLE Supplier CASCADE CONSTRAINTS"); break; }
                    case "supplies": { stmt2.execute("DROP TABLE Supplies CASCADE CONSTRAINTS"); break; }
                    case "shoppinglist": { stmt2.execute("DROP TABLE ShoppingList CASCADE CONSTRAINTS"); break; }
                    case "lists": { stmt2.execute("DROP TABLE LISTS CASCADE CONSTRAINTS"); break; }
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