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
            System.out.println("Table User created");
            stmt.executeUpdate("CREATE TABLE Recipe(RName VARCHAR2(20) PRIMARY KEY, Kind VARCHAR2(20), Tea VARCHAR2(20), Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            System.out.println("Table Recipe created");
            stmt.executeUpdate("CREATE TABLE Address(PostalCode VARCHAR2(20),StreetName VARCHAR2(20),House# INT, City VARCHAR2(20), PRIMARY KEY(PostalCode, StreetName, House#))"); //no connection
            System.out.println("Table Address created");
            stmt.executeUpdate("CREATE TABLE Grocery(GName VARCHAR2(20), Amount INT DEFAULT 0, BuyingDate DATE, PRIMARY KEY (GName,BuyingDate), Duration INT)");
            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration))");
            stmt.executeUpdate("CREATE TABLE Buys(UName VARCHAR2(20), GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY(UName, GName, BuyingDate), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
            stmt.executeUpdate("CREATE TABLE MakeRecipe(UName VARCHAR2(20), RName VARCHAR2(20), PRIMARY KEY(UName,RName), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(RName) REFERENCES Recipe)");
            System.out.println("Table MakeRecipe created");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RName VARCHAR2(20), UsingDate DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE Generates(RName VARCHAR2(20), UseID INT, PRIMARY KEY(RName, UseID), FOREIGN KEY(RName) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage(UseID) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE DailyReport(ReportDay DATE PRIMARY KEY, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            //"FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);"
//            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName VARCHAR2(20) NOT NULL)");
            System.out.println("Table Supplier created");
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY (SupplierId))");
//                                    "FOREIGN KEY(SupplierId) REFERENCES Supplier(SupplierId), FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
            System.out.println("Table Supplies created");
            stmt.executeUpdate("CREATE TABLE ShoppingList(GName CHAR(20), Amount INT NOT NULL, ListDate DATE, PRIMARY KEY (GName,ListDate))");
            System.out.println("Table ShoppingList created");
            //                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName,ListDate) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Lists(GName CHAR(20), ListDate DATE, UseID INT, PRIMARY KEY (UseID, GName, ListDate))");
            System.out.println("Table List created");
//                                    "FOREIGN KEY (GName,ListDate) REFERENCES ShoppingList(GName, ListDate) ON DELETE CASCADE, FOREIGN KEY (UseID) REFERENCES Usage(UseID) ON DELETE CASCADE)");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage()+"Error: create table error");
        }
        User u1 = new User("Sam", "123", "23rd W Ave", 2341, "Vancouver","V6S1H6");
        User u2 = new User("Lily", "234","23rd W Ave", 2341, "Vancouver","V6S1H6");
        insertUser(u1);
        Recipe r1 = new Recipe("Red tea", "Milk Tea", 20, 0, 0, 0);
        Recipe r2 = new Recipe("Perl Milk Tea", "Milk Tea", 20, 0, 0, 0);
        insertRecipe(r1);
        insertRecipe(r2);
        insertMakeRecipe("Sam", "Red tea");
        insertSupplier(0,"T&T");
        insertSupplier(1,"Coco");
        DailyReport d1 = new DailyReport("2021-03-28", 20,0,0,0);
        insertDailReport(d1);
    }

    public void insertDailReport(DailyReport d){
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO DailyReport VALUES (?,?,?,?,?)");
            stmt.setObject(1, d.getDate());
            stmt.setInt(2, d.getPearl());
            stmt.setInt(3, d.getJelly());
            stmt.setInt(4, d.getLemon());
            stmt.setInt(5, d.getOrange());
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage()+"fail to insert daily report");
            rollbackConnection();
        }
    }

    public void insertSupplies(int id, String Name){
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplies (SupplierId,GName) VALUES ('%d', '%s')",id,Name);
            stmt1.executeQuery(cmd);
            connection.commit();
            stmt1.close();
            System.out.println("Insert Supplier passed");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplies");
            rollbackConnection();
        }
    }

    public void insertSupplier(int id, String Name){
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplier (SupplierId,CompanyName) VALUES ('%d', '%s')",id,Name);
            stmt1.executeQuery(cmd);
            connection.commit();
            stmt1.close();
            System.out.println("Insert Supplier passed");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplier");
            rollbackConnection();
        }
    }

    public void insertMakeRecipe(String UName, String RName){
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO MakeRecipe (UName,RName) VALUES ('%s', '%s')",UName,RName);
            stmt1.executeQuery(cmd);
            connection.commit();
            stmt1.close();
            System.out.println("Insert make recipe passed");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert make recipe");
            rollbackConnection();
        }
    }

    public boolean selectPassword(String name, String password) {
        boolean result = false;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password FROM Users WHERE UName = '" + name+ "'");
            if(rs.next()) {
                String temp = rs.getString("Password");
                System.out.println(temp);
                result = (temp.equals(password));
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
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getStreet());
            stmt.setInt(4, user.getHouseNumber());
            stmt.setString(6, user.getCode());
            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRecipe(Recipe recipe) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Recipe VALUES (?,?,?,?,?,?)");
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getKind());
            statement.setInt(3, recipe.getPearl());
            statement.setInt(4, recipe.getJelly());
            statement.setInt(5, recipe.getLemon());
            statement.setInt(6, recipe.getOrange());
            statement.executeUpdate();
            connection.commit();
            statement.close();
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

    public Recipe[] selectAllRecipe() {
        ArrayList<Recipe> recipes = new ArrayList<>();
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
                recipes.add(temp);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return recipes.toArray(new Recipe[recipes.size()]);
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
        ArrayList<String> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT r.RName FROM Recipe r, Users u MakeRecipe m WHERE c.RName = r.RName AND c.UName = u.UName AND u.UName = " + uname);
            while (rs1.next()) {
                recipes.add(rs1.getString("RName"));
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return recipes.toArray(new Recipe[recipes.size()]);
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
        Object[] pearlStock = findEarliestAmount("Pearl");
        Object[] jellyStock = findEarliestAmount("Jelly");
        Object[] lemonStock = findEarliestAmount("Lemon");
        Object[] orangeStock = findEarliestAmount("Orange");

        if (!checkAddToList(recipe.getPearl(), (int) pearlStock[0], "Pearl", today) &&
            !checkAddToList(recipe.getJelly(), (int) jellyStock[0], "Jelly", today) &&
            !checkAddToList(recipe.getLemon(), (int) lemonStock[0], "Lemon", today) &&
            !checkAddToList(recipe.getPearl(), (int) orangeStock[0], "Orange", today)) {
            //to Eric:
            Grocery pearl = new Grocery("Pearl", (int) pearlStock[0] - recipe.getPearl(), 5, (Date) pearlStock[1], (Date) pearlStock[1]);
            updateGrocery(pearl);
            Grocery jelly = new Grocery("Jelly", (int) pearlStock[0] - recipe.getJelly(), 5, (Date) jellyStock[1], (Date) jellyStock[1]);
            updateGrocery(jelly);
            Grocery lemon = new Grocery("Lemon", (int) pearlStock[0] - recipe.getLemon(), 5, (Date) lemonStock[1], (Date) lemonStock[1]);
            updateGrocery(lemon);
            Grocery orange = new Grocery("Orange", (int) pearlStock[0] - recipe.getOrange(), 5, (Date) orangeStock[1], (Date) orangeStock[1]);
            updateGrocery(orange);
            //insert usage --- daily report
            System.out.println("Success! Enjoy!");
        } else {
            System.out.println("Missing Ingredients, help you add to today's shopping list");
        }
    }

    public boolean checkAddToList(int needAmount,int haveAmount, String Gname, Date today){
        boolean enough = false;
        if (needAmount <= haveAmount) {
            enough = true;
        } else {
            int shortAmount = needAmount - haveAmount;
            //to Eric
            DailyReports temp = new DailyReports();
            ShoppingList[] glist = temp.selectShoppingListsGrocery(Gname, today);
            if (glist.length == 0) {//today we have not buy this grocery yet
                ShoppingList list = new ShoppingList(Gname, shortAmount, today);
                temp.insertShoppingList(list);
            } else {//update today's existing
                temp.updateShoppingList(today, Gname, shortAmount + glist[0].getAmount());
            }
        }
        return enough;
    }

    public void deleteWithZero() {
        //todo: delete all tuples with 0 amount
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Grocery WHERE amount = 0");
            int rowCount = ps.executeUpdate();
            if (rowCount == 0){
                System.out.println(WARNING_TAG + "No tuple with zero amount");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Object[] findEarliestAmount(String name){ //return amount and date!!
        int amount=0;
        Date date = null;
        return new Object[]{amount, date};
    }

    public void insertGrocery(Grocery grocery) { }

    public void updateGrocery(Grocery grocery) { }

    public Grocery[] selectAllGrocery() { return new Grocery[0]; }

    public int sumGroceryAmount(String name) { return 0; }

    public Grocery[] orderGroceryByDate() {
        return new Grocery[0];
    }

    public Grocery selectGrocery(String name) {
        return new Grocery();
    }

    public void insertUsage(String name, Date date) { }

    public void insertReport() { }

    public void updateReport() { }

    public DailyReport[] selectAllReport() { return new DailyReport[0]; }

    public DailyReport[] selectReportByGname(String gname) { return new DailyReport[0]; }

    public DailyReport[] selectReportWithEvery() { return new DailyReport[0]; }

    public void insertList() { }

    public void updateList() { }

    public ShoppingList[] selectAllList() { return new ShoppingList[0]; }

    public ShoppingList[] selectListByDate(Date date1, Date date2) { return new ShoppingList[0]; }

    public ShoppingList[] selectListByGname(String gname) { return new ShoppingList[0]; }

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