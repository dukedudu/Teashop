package backend.database;

import frontend.model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection = null;

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
            stmt.executeUpdate("CREATE TABLE UserInfo(UName varchar2(20) PRIMARY KEY, StreetName varchar2(20), HouseNumber INT DEFAULT 0, City varchar2(20), PostalCode varchar2(20), Certificate varchar2(20), Budget INT DEFAULT 0)");
            System.out.println("Table User passed\n");
            stmt.executeUpdate("CREATE TABLE Recipe(RName varchar2(20) PRIMARY KEY, Kind varchar2(20), Tea varchar2(20), Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0, Calories INT DEFAULT 0)");
            //                   "FOREIGN KEY(PostalCode) REFERENCES Address(PostalCode) ON DELETE CASCADE, FOREIGN KEY(StreetName) REFERENCES Address(StreetName) ON DELETE CASCADE, FOREIGN KEY(House#) REFERENCES Address(House#) ON DELETE CASCADE);");
//            stmt.executeUpdate("CREATE TABLE Address(PostalCode CHAR(20),StreetName CHAR(20),House# INT,City CHAR(20), PRIMARY KEY(PostalCode, StreetName, House#));");
//            stmt.executeUpdate("CREATE TABLE Grocery(GName CHAR(20) PRIMARY KEY,Amount INT DEFAULT 0, BuyingDate DATE, Duration INT," +
//                    "FOREIGN KEY(BuyingDate) REFERENCES GroceryDate(BuyingDate) ON DELETE CASCADE, FOREIGN KEY(Duration) REFERENCES GroceryDate(Duration) ON DELETE CASCADE)");
//            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration));");
//            stmt.executeUpdate("CREATE TABLE Buys(UserId INT, GName CHAR(20), BuyingDate DATE, PRIMARY KEY(UserId, GName, BuyingDate)," +
//                    "FOREIGN KEY(UserId) REFERENCES User(UserId) ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery(GName) ON DELETE CASCADE, FOREIGN KEY(BuyingDate) REFERENCES Grocery(BuyingDate) ON DELETE CASCADESystem.out.println("Table Recipe passed\n");
            stmt.executeUpdate("CREATE TABLE Create(UName varchar2(20),RName varchar2(20), PRIMARY KEY(UName,RName),FOREIGN KEY(UName) REFERENCES User(Name) ON DELETE CASCADE, FOREIGN KEY(RName) REFERENCES Recipe(RName) ON DELETE CASCADE )");

            //            Create"CREATE TABLE Usage(UseID INT PRIMARY KEY, RecipeId INT, Date DATE NOT NULL, Tea CHAR(20) NOT NULL, Calories INT DEFAULT 0, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0," +
//                    "FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId) ON DELETE SET NULL);\n");
//            stmt.executeUpdate("CREATE TABLE Generates(RecipeId INT, UseID INT, PRIMARY KEY(RecipeId, UseID), " +
//                    "FOREIGN KEY(RecipeId) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage ON DELETE CASCADE);\n");
//            stmt.executeUpdate("CREATE TABLE DailyReport(ReportID INT, UseID INT, Date DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0, PRIMARY KEY(UseID, ReportID)," +
//                    "FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);");
//            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
//            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName CHAR(20) NOT NULL UNIQUE);");
//            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName CHAR(20), PRIMARY KEY (SupplierId, GName)," +
//                    "FOREIGN KEY(SupplierId) REFERENCES Supplier ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
//            stmt.executeUpdate("CREATE TABLE ShoppingList(SListId INT AUTO_INCREMENT, GName CHAR(20), Amount INT NOT NULL, Date DATE, UserId INT, PRIMARY KEY (SListId)" +
//                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
//            stmt.executeUpdate("CREATE TABLE Lists(UseID INT, GName CHAR(20), SListId INT, PRIMARY KEY (SListId, UseID, GName)," +
//                    "FOREIGN KEY (SListId) REFERENCES ShoppingList ON DELETE CASCADE, FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE, FOREIGN KEY (UseId) REFERENCES Usage ON DELETE CASCADE);");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage()+"Error: create table error");
        }
        User u1 = new User("Sam", "123", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 500);
        insertUser(u1);
        //User u2 = new User("Lily", "234", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 400);
        Recipe r1 = new Recipe("Red tea", "Red tea", "Milk Tea", 20, 0, 0, 0, 100);
        insertRecipe(r1);
        Recipe r2 = new Recipe("Perl Milk Tea", "Red tea", "Milk Tea", 20, 0, 0, 0, 100);
        insertRecipe(r2);
        try {
            Statement stmt1 = connection.createStatement();
            stmt1.executeQuery("INSERT INTO User VALUES 'Sam', 'Red Tea'");
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage()+"\nError: failed to insert create");
        }
    }

    public boolean selectPassword(String name, String password) {
        boolean result = false;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password FROM User WHERE Name=" + name);
            result = (rs.equals(password));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());

            rollbackConnection();
        }
        return result;
    }

    public void insertUser(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getCode());
            stmt.setString(4, user.getStreet());
            stmt.setInt(5, user.getHouse());
            stmt.setString(6, user.getCertificate());
            stmt.setInt(7, user.getBudget());
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRecipe(Recipe recipe) {
        //Recipe(String name, String tea, int pearl, int jelly, int lemon, int orange, int calories)
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Recipe VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getTea());
            statement.setString(3, recipe.getKind());
            statement.setInt(4, recipe.getPearl());
            statement.setInt(5, recipe.getJelly());
            statement.setInt(6, recipe.getLemon());
            statement.setInt(7, recipe.getOrange());
            statement.setInt(8, recipe.getCalories());
            statement.executeUpdate();
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateRecipe(Recipe recipe) {
        //Recipe(String name, String tea, int pearl, int jelly, int lemon, int orange, int calories)
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Recipe SET Pearl=?, Jelly=?, Lemon=?, Orange=?, Calories=? WHERE RName='" + recipe.getName() + "'");
//            ps.setString(1, recipe.getTea());
//            ps.setString(2, recipe.getKind());
            ps.setInt(1, recipe.getPearl());
            ps.setInt(2, recipe.getJelly());
            ps.setInt(3, recipe.getLemon());
            ps.setInt(4, recipe.getOrange());
            ps.setInt(5, recipe.getCalories());

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

    public Recipe selectRecipeByRname(String rname) {
        Recipe result = new Recipe();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE RName = '" + rname + "'");
            rs1.next();
            result = new Recipe(rs1.getString("RName"),
                    rs1.getString("Tea"),
                    rs1.getString("Kind"),
                    rs1.getInt("Pearl"),
                    rs1.getInt("Jelly"),
                    rs1.getInt("Lemon"),
                    rs1.getInt("Orange"),
                    rs1.getInt("Calories")
            );
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    public Recipe[] selectRecipeByUname(String uname) {
//        Recipe result = new Recipe();
//        try {
//            Statement stmt1 = connection.createStatement();
//            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE RName=" + name);
//            result = new Recipe(rs1.getString("name"),
//                    rs1.getString("tea"),
//                    rs1.getString("Kind"),
//                    rs1.getInt("pearl"),
//                    rs1.getInt("jelly"),
//                    rs1.getInt("lemon"),
//                    rs1.getInt("orange"),
//                    rs1.getInt("calories")
//            );
//            rs1.close();
//            stmt1.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
        return new Recipe[0];
    }

    public Recipe[] selectRecipeByKind(String kind) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE Kind="+kind);
            while (rs1.next()) {
                Recipe temp = new Recipe(rs1.getString("name"),
                        rs1.getString("tea"),
                        rs1.getString("Kind"),
                        rs1.getInt("pearl"),
                        rs1.getInt("jelly"),
                        rs1.getInt("lemon"),
                        rs1.getInt("orange"),
                        rs1.getInt("calories")
                );
                recipes.add(temp);
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return recipes.toArray(new Recipe[recipes.size()]);
    }

    public Recipe[] selectAllRecipe() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe");
            while (rs1.next()) {
                Recipe temp = new Recipe(rs1.getString("RName"),
                        rs1.getString("Tea"),
                        rs1.getString("Kind"),
                        rs1.getInt("Pearl"),
                        rs1.getInt("Jelly"),
                        rs1.getInt("Lemon"),
                        rs1.getInt("Orange"),
                        rs1.getInt("Calories")
                );
                recipes.add(temp);
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return recipes.toArray(new Recipe[recipes.size()]);
    }
    public String[] recipeInventor(String uName){
        ArrayList<String> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT r.RName FROM Recipe r, User u Create c WHERE c.RName = r.RName AND c.UName = u.UName AND u.UName = "+uName);
            while (rs1.next()) {
                recipes.add(rs1.getString("RName"));
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return recipes.toArray(new String[recipes.size()]);

    }
    public void makeBeverage(String name, Date today){
        Recipe recipeCandidate = selectRecipeByRname(name); //
        deleteGroceryWithZero();
        Object[] pearlStock = findEariliestGroceryAmount("Pearl");
        Object[] jellyStock = findEariliestGroceryAmount("Jelly");
        Object[] lemonStock = findEariliestGroceryAmount("Lemon");
        Object[] orangeStock = findEariliestGroceryAmount("Orange");

        if (!checkAddToList(recipeCandidate.getPearl(),(int)pearlStock[0],"Pearl", today)&&
        !checkAddToList(recipeCandidate.getJelly(),(int)jellyStock[0],"Jelly", today)&&
        !checkAddToList(recipeCandidate.getLemon(),(int)lemonStock[0],"Lemon", today)&&
        !checkAddToList(recipeCandidate.getPearl(),(int)orangeStock[0],"Orange", today)){
            //to Eric:
            Grocery pearl = new Grocery("Pearl",(int)pearlStock[0]-recipeCandidate.getPearl(),5,(Date)pearlStock[1]);
            updateGrocery(pearl);
            Grocery jelly = new Grocery("Jelly",(int)pearlStock[0]-recipeCandidate.getJelly(),5,(Date)jellyStock[1]);
            updateGrocery(jelly);
            Grocery lemon = new Grocery("Lemon",(int)pearlStock[0]-recipeCandidate.getLemon(),5,(Date)lemonStock[1]);
            updateGrocery(lemon);
            Grocery orange = new Grocery("Orange",(int)pearlStock[0]-recipeCandidate.getOrange(),5,(Date)orangeStock[1]);
            updateGrocery(orange);
            System.out.println("Success! Enjoy!");
        }
        else{
            System.out.println("Missing Ingredients, help you add to today's shopping list");
        }

    }
    public boolean checkAddToList(int needAmount,int haveAmount, String Gname, Date today){
        boolean enough = false;
        if (needAmount<=haveAmount){
            enough = true;
        }
        else{
            int shortAmount = needAmount-haveAmount;
            //to Eric
            DailyReports temp = new DailyReports();
            ShoppingList[] glist = temp.selectShoppingListsGrocery(Gname,today);
            if(glist.length==0){//today we have not buy this grocery yet
                ShoppingList list = new ShoppingList(Gname,shortAmount,today);
                temp.insertShoppingList(list);
            }
            else{//update today's existing
                temp.updateShoppingList(today, Gname, shortAmount+glist[0].getAmount());
            }
        }
        return enough;
    }
    public void deleteGroceryWithZero(){
        //todo: delete all tuples with 0 amount
    }
    public Object[] findEariliestGroceryAmount(String name){ //return amount and date!!
        int amount=0;
        Date date = null;
        return new Object[]{amount,date};
    }
    public void insertGrocery(Grocery grocery) {
    }

    public void updateGrocery(Grocery grocery) {
    }

    public int sumGroceryAmount(String name) { return 0; }

    public Grocery[] orderGroceryByDate() {
        return new Grocery[0];
    }

    public Grocery selectGrocery(String name) {
        return new Grocery();
    }

    public Grocery[] selectAllGrocery() {
        return new Grocery[0];
    }

    public void insertUsage(String name, Date date) { }

    public DailyReport[] selectReportByGname(String gname) { return new DailyReport[0]; }

    public DailyReport[] selectReportWithEvery() { return new DailyReport[0]; }

    public DailyReport[] selectAllReport() { return new DailyReport[0]; }

    public ShoppingList[] selectListByDate(Date date1, Date date2) { return new ShoppingList[0]; }

    public ShoppingList[] selectListByGname(String gname) { return new ShoppingList[0]; }

    public ShoppingList[] selectAllList() { return new ShoppingList[0]; }

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
            //Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("select table_name from user_tables");
            Statement stmt2 = connection.createStatement();
            //stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS");
            stmt2.execute("DROP TABLE UserInfo CASCADE CONSTRAINTS");
            //stmt2.execute("DROP TABLE Create CASCADE CONSTRAINTS");
            //while(rs.next()) {
//                Statement stmt2 = connection.createStatement();
//                stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS");
//                stmt2.execute("DROP TABLE User CASCADE CONSTRAINTS");
//                stmt2.execute("DROP TABLE Create CASCADE CONSTRAINTS");
////                if (rs.getString(1).toLowerCase().equals("building")) {
//                    stmt2.execute("DROP TABLE Building CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("garbageroom")) {
//                    stmt2.execute("DROP TABLE GarbageRoom CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("landlord")) {
//                    stmt2.execute("DROP TABLE Landlord CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("manage")) {
//                    stmt2.execute("DROP TABLE Manage CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("manager")) {
//                    stmt2.execute("DROP TABLE Manager CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("parkingspot")) {
//                    stmt2.execute("DROP TABLE ParkingSpot CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("postman")) {
//                    stmt2.execute("DROP TABLE Postman CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("resident")) {
//                    stmt2.execute("DROP TABLE Resident CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("room")) {
//                    stmt2.execute("DROP TABLE Room CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("serve")) {
//                    stmt2.execute("DROP TABLE Serve CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("tenant")) {
//                    stmt2.execute("DROP TABLE Tenant CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("videoserveillance")) {
//                    stmt2.execute("DROP TABLE VideoServeillance CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("dobandage")) {
//                    stmt2.execute("DROP TABLE DOBandAge CASCADE CONSTRAINTS");
//                } else if (rs.getString(1).toLowerCase().equals("sinandname")) {
//                    stmt2.execute("DROP TABLE SINandName CASCADE CONSTRAINTS");
//                }
                //stmt2.close();
            //}
            //rs.close();
            stmt2.close();
        } catch (SQLException e) {
            //MainMenu.makeWarningDialog(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println(EXCEPTION_TAG + " " + e.getMessage()+"drop table error");
        }
    }
}