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
            connection = DriverManager.getConnection(ORACLE_URL, "ora_dmy0604", "a44147163");//"ora_sunjingy", "a48346902");
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
            stmt.executeUpdate("CREATE TABLE Users(UName varchar2(20) PRIMARY KEY, Password varchar2(20), StreetName varchar2(20), HouseNumber INT DEFAULT 0, City varchar2(20), " +
                    "PostalCode varchar2(20), Certificate varchar2(20), Budget INT DEFAULT 0)");
            System.out.println("Table User created");
            stmt.executeUpdate("CREATE TABLE Recipe(RName varchar2(20) PRIMARY KEY, Kind varchar2(20), Tea varchar2(20), Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0, Calories INT DEFAULT 0)");
            System.out.println("Table Recipe created");
            //                   "FOREIGN KEY(PostalCode) REFERENCES Address(PostalCode) ON DELETE CASCADE, FOREIGN KEY(StreetName) REFERENCES Address(StreetName) ON DELETE CASCADE, FOREIGN KEY(House#) REFERENCES Address(House#) ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Address(PostalCode varchar2(20),StreetName varchar2(20),House# INT, City varchar2(20), PRIMARY KEY(PostalCode, StreetName, House#))"); //no connection
            System.out.println("Table Address created");
            stmt.executeUpdate("CREATE TABLE Grocery(GName VARCHAR2(20), Amount INT DEFAULT 0, BuyingDate DATE, PRIMARY KEY (GName,BuyingDate), Duration INT)");
            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration))");
            stmt.executeUpdate("CREATE TABLE Buys(UName VARCHAR2(20), GName VARCHAR2(20), BuyingDate DATE, PRIMARY KEY(UName, GName, BuyingDate), " +
                    "FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
            //                    ",Amount INT DEFAULT 0, BuyingDate DATE, Duration INT," +
//                    "FOREIGN KEY(UserId) REFERENCES User(UserId) ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery(GName) ON DELETE CASCADE, FOREIGN KEY(BuyingDate) REFERENCES Grocery(BuyingDate) ON DELETE CASCADESystem.out.println("Table Recipe passed\n");
            stmt.executeUpdate("CREATE TABLE MakeRecipe(UName varchar2(20), RName varchar2(20), PRIMARY KEY(UName,RName), FOREIGN KEY(UName) REFERENCES Users, FOREIGN KEY(RName) REFERENCES Recipe)");
            System.out.println("Table MakeRecipe created");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RName varchar2(20), UsingDate DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE Generates(RName varchar2(20), UseID INT, PRIMARY KEY(RName, UseID), " +
                    "FOREIGN KEY(RName) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE DailyReport(reportDay DATE PRIMARY KEY, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0)");
            //"FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);"
//            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName varchar2(20) NOT NULL)");
            System.out.println("Table Supplier created");
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName varchar2(20), BuyingDate DATE, PRIMARY KEY (SupplierId)," +
                    "FOREIGN KEY(SupplierId) REFERENCES Supplier, FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate))");
            System.out.println("Table Supplies created");
            stmt.executeUpdate("CREATE TABLE ShoppingList(GName CHAR(20), Amount INT NOT NULL, ListDate DATE, PRIMARY KEY (GName,ListDate))");
            System.out.println("Table ShoppingList created");
//                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName,ListDate) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Lists(GName CHAR(20), ListDate DATE, UseID INT, PRIMARY KEY (UseID, GName, ListDate), " +
                    "FOREIGN KEY (GName,ListDate) REFERENCES ShoppingList ON DELETE CASCADE, FOREIGN KEY (UseID) REFERENCES Usage ON DELETE CASCADE)");
            System.out.println("Table List created");
//                    "FOREIGN KEY(SupplierId) REFERENCES Supplier, FOREIGN KEY(GName) REFERENCES Grocery(GName))");
//            stmt.executeUpdate("CREATE TABLE ShoppingList(GName VARCHAR2(20), Amount INT NOT NULL, ListDate DATE, PRIMARY KEY (GName, ListDate)" +
//                    "FOREIGN KEY(UName) REFERENCES Users ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
//            stmt.executeUpdate("CREATE TABLE Lists(UseID INT, GName VARCHAR2(20), SListId INT, PRIMARY KEY (SListId, UseID, GName)," +
//                    "FOREIGN KEY (SListId) REFERENCES ShoppingList ON DELETE CASCADE, FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE, FOREIGN KEY (UseId) REFERENCES Usage ON DELETE CASCADE);");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: create table error");
        }
        User u1 = new User("Sam", "123", "23rd W Ave", 2341, "Vancouver", "V6S1H6", "Manager", 500);
        User u2 = new User("Lily", "234", "23rd W Ave", 2341, "Vancouver", "V6S1H6", "Beverage Maker", 0);
        insertUser(u1);
        Recipe r1 = new Recipe("Red tea", "Red tea", "Milk Tea", 20, 0, 0, 0, 100);
        Recipe r2 = new Recipe("Perl Milk Tea", "Red tea", "Milk Tea", 20, 0, 0, 0, 100);
        insertRecipe(r1);
        insertRecipe(r2);
        insertMakeRecipe("Sam", "Red tea");
        insertSupplier(0, "T&T");
        insertSupplier(1, "Coco");
        DailyReport d1 = new DailyReport("2021-03-28", 20, 0, 0, 0);
        insertDailReport(d1);
        Grocery g1 = new Grocery("Pearl", 0, 20, Date.valueOf("2021-03-29"));
        insertGrocery(g1);
        Grocery g2 = new Grocery("Pearl", 50, 20, Date.valueOf("2021-03-30"));
        insertGrocery(g2);
    }

    public void insertDailReport(DailyReport d) {
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
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "fail to insert daily report");
            rollbackConnection();
        }
    }

    public void insertShoppingList(ShoppingList list){

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
            //rollbackConnection();
        }
    }

    public void insertSupplies(int id, String Name) {
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplies (SupplierId,GName) VALUES ('%d', '%s')", id, Name);
            stmt1.executeQuery(cmd);
            connection.commit();
            stmt1.close();
            System.out.println("Insert Supplier passed");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplies");
            rollbackConnection();
        }
    }

    public void insertSupplier(int id, String Name) {
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO Supplier (SupplierId,CompanyName) VALUES ('%d', '%s')", id, Name);
            stmt1.executeQuery(cmd);
            connection.commit();
            stmt1.close();
            System.out.println("Insert Supplier passed");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "Error: failed to insert supplier");
            rollbackConnection();
        }
    }

    public void insertMakeRecipe(String UName, String RName) {
        try {
            Statement stmt1 = connection.createStatement();
            String cmd = String.format("INSERT INTO MakeRecipe (UName,RName) VALUES ('%s', '%s')", UName, RName);
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
            ResultSet rs = stmt.executeQuery("SELECT Password FROM Users WHERE UName = '" + name + "'");
            if (rs.next()) {
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

    public boolean changePassword(String UName, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("confirmPassword should be same as newPassword.");
            return false;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Users SET Password = ? WHERE UName = ?");
            ps.setString(1, newPassword);
            ps.setString(2, UName);
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " User does not exist!");
                return false;
            }
            connection.commit();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public void insertUser(User user) {
        try {
            //UserInfo(UName varchar2(20) PRIMARY KEY, Password varchar2(20), StreetName varchar2(20), HouseNumber INT DEFAULT 0, City varchar2(20), PostalCode varchar2(20), Certificate varchar2(20), Budget INT DEFAULT 0)");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getStreet());
            stmt.setInt(4, user.getHouseNumber());
            stmt.setString(5, user.getCity());
            stmt.setString(6, user.getCode());
            stmt.setString(7, user.getCertificate());
            stmt.setInt(8, user.getBudget());
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

    public Recipe[] selectRecipeByKind(String kind) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE Kind=" + kind);
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

    public Recipe[] recipeInventor(String uName) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT r.RName, r.Kind, r.Tea, r.pearl, r.jelly, r.lemon, r.orange, r.calories FROM Recipe r, Users u, MakeRecipe m WHERE m.RName = r.RName AND m.UName = u.UName AND u.UName = '" + uName + "'");

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

    public void makeBeverage(String name, Date today) {
        Recipe recipeCandidate = selectRecipeByRname(name); //
        deleteGroceryWithZero();
        Object[] pearlStock = findEariliestGroceryAmount("Pearl");
        Object[] jellyStock = findEariliestGroceryAmount("Jelly");
        Object[] lemonStock = findEariliestGroceryAmount("Lemon");
        Object[] orangeStock = findEariliestGroceryAmount("Orange");

        if (!checkAddToList(recipeCandidate.getPearl(), (int) pearlStock[0], "Pearl", today) &&
                !checkAddToList(recipeCandidate.getJelly(), (int) jellyStock[0], "Jelly", today) &&
                !checkAddToList(recipeCandidate.getLemon(), (int) lemonStock[0], "Lemon", today) &&
                !checkAddToList(recipeCandidate.getPearl(), (int) orangeStock[0], "Orange", today)) {
            //to Eric:
            Grocery pearl = new Grocery("Pearl", (int) pearlStock[0] - recipeCandidate.getPearl(), 5, (Date) pearlStock[1]);
            updateGrocery(pearl);
            Grocery jelly = new Grocery("Jelly", (int) pearlStock[0] - recipeCandidate.getJelly(), 5, (Date) jellyStock[1]);
            updateGrocery(jelly);
            Grocery lemon = new Grocery("Lemon", (int) pearlStock[0] - recipeCandidate.getLemon(), 5, (Date) lemonStock[1]);
            updateGrocery(lemon);
            Grocery orange = new Grocery("Orange", (int) pearlStock[0] - recipeCandidate.getOrange(), 5, (Date) orangeStock[1]);
            updateGrocery(orange);
            //insert usage --- daily report
            System.out.println("Success! Enjoy!");
        } else {
            System.out.println("Missing Ingredients, help you add to today's shopping list");
        }
    }

    public boolean checkAddToList(int needAmount, int haveAmount, String Gname, Date today) {
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

    public void deleteGroceryWithZero() {
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

    public Object[] findEariliestGroceryAmount(String name) { //return amount and date!!
        int amount = 0;
        Date date = null;
        return new Object[]{amount, date};
    }

    public void insertGrocery(Grocery grocery) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Grocery VALUES (?,?,?,?)");
            statement.setString(1, grocery.getName());
            statement.setInt(2, grocery.getAmount());
            statement.setDate(3, grocery.getDate());
            statement.setInt(4, grocery.getDuration());
            statement.executeUpdate();
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateGrocery(Grocery grocery) {
    }

    public int sumGroceryAmount(String name) {
        return 0;
    }

    public Grocery[] orderGroceryByDate() {
        return new Grocery[0];
    }

    public Grocery selectGrocery(String name) {
        return new Grocery();
    }

    public Grocery[] selectAllGrocery() {
        return new Grocery[0];
    }

    public void insertUsage(String name, Date date) {
    }


    //Eric added from DailyReports file

    private DailyReport[] selectReports(String query) {
        ArrayList<DailyReport> result = new ArrayList<DailyReport>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                DailyReport model = new DailyReport(
                        rs.getDate("Date").toString(),
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

    private ShoppingList[] selectLists(String query) {
        ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ShoppingList model = new ShoppingList(
                        rs.getString("GName" ),
                        rs.getInt("Amount" ),
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

    public DailyReport[] selectReportWithEvery() {
        String query = "SELECT * FROM DailyReport D WHERE NOT EXISTS ((SELECT * FROM DailyReport D1) EXCEPT (SELECT * FROM DailyReport D2 WHERE D2.Pearl > 0 AND D2. Jelly > 0 AND D2.Lemon > 0 AND D2. Orange > 0))";
        return selectReports(query);
    }

    public DailyReport[] selectReportByGname(String name) {
        if (name.equals("Pearl")) {
            return selectReports("SELECT * FROM ShoppingList WHERE Pearl > 0");
        }
        else if (name.equals("Jelly")) {
            return selectReports("SELECT * FROM ShoppingList WHERE Jelly > 0");
        }
        else if (name.equals("Lemon")) {
            return selectReports("SELECT * FROM ShoppingList WHERE Lemon > 0");
        }
        else {
            return selectReports("SELECT * FROM ShoppingList WHERE Orange > 0");
        }
    }

    public DailyReport[] selectAllReport() {
        return selectReports("SELECT * FROM DailyReport");
    }

    public ShoppingList[] selectListByDate(Date date1, Date date2) {
        String date = date1.toString();
        return selectLists("SELECT * FROM ShoppingList WHERE ListDate =" + date);
    }

    public ShoppingList[] selectListByGname(String gname) {
        return selectLists("SELECT * FROM ShoppingList WHERE GName = " + gname);
    }

    public ShoppingList[] selectAllList() {
        return selectLists("SELECT * FROM ShoppingList");
    }


    public void updateShoppingList(Date date, String Gname, int amount){

        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE ShoppingList SET Gname=?, Amount=?, Date=? WHERE GName =" + Gname + "AND Date =" + date.toString());
            stmt.setString(1, Gname);
            stmt.setInt(2, amount);
            stmt.setDate(3, date);

            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            //rollbackConnection();
        }
    }

    public void updateDailyReport(Date date, int pearl, int jelly, int lemon, int orange) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE DailyReport SET reportDay=?, Pearl=?, Jelly=?, Lemon=?, Orange=? WHERE reportDay =" + date.toString());
            stmt.setDate(1, date);
            stmt.setInt(2, pearl);
            stmt.setInt(3, jelly);
            stmt.setInt(4, lemon);
            stmt.setInt(5, orange);

            stmt.executeUpdate();
            connection.commit();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            //rollbackConnection();
        }
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
                    case "users": {
                        stmt2.execute("DROP TABLE Users CASCADE CONSTRAINTS");
                        break;
                    }
                    case "recipe": {
                        stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS");
                        break;
                    }
                    case "address": {
                        stmt2.execute("DROP TABLE Address CASCADE CONSTRAINTS");
                        break;
                    }
                    case "grocery": {
                        stmt2.execute("DROP TABLE Grocery CASCADE CONSTRAINTS");
                        break;
                    }
                    case "grocerydate": {
                        stmt2.execute("DROP TABLE GroceryDate CASCADE CONSTRAINTS");
                        break;
                    }
                    case "makerecipe": {
                        stmt2.execute("DROP TABLE MakeRecipe CASCADE CONSTRAINTS");
                        break;
                    }
                    case "buys": {
                        stmt2.execute("DROP TABLE Buys CASCADE CONSTRAINTS");
                        break;
                    }
                    case "usage": {
                        stmt2.execute("DROP TABLE Usage CASCADE CONSTRAINTS");
                        break;
                    }
                    case "generates": {
                        stmt2.execute("DROP TABLE Generates CASCADE CONSTRAINTS");
                        break;
                    }
                    case "dailyreport": {
                        stmt2.execute("DROP TABLE DailyReport CASCADE CONSTRAINTS");
                        break;
                    }
                    case "supplier": {
                        stmt2.execute("DROP TABLE Supplier CASCADE CONSTRAINTS");
                        break;
                    }
                    case "supplies": {
                        stmt2.execute("DROP TABLE Supplies CASCADE CONSTRAINTS");
                        break;
                    }
                    case "shoppinglist": {
                        stmt2.execute("DROP TABLE ShoppingList CASCADE CONSTRAINTS");
                        break;
                    }
                    case "lists": {
                        stmt2.execute("DROP TABLE LISTS CASCADE CONSTRAINTS");
                        break;
                    }
                    default: System.out.println(rs.getString(1).toLowerCase());
                }
                stmt2.close();
            }
            rs.close();
//            Statement stmt2 = connection.createStatement();
//            stmt2.execute("DROP TABLE Users CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Recipe CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Address CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Grocery CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE GroceryDate CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE MakeRecipe CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Buys CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Usage CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Generates CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE DailyReport CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Supplier CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE Supplies CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE ShoppingList CASCADE CONSTRAINTS");
//            stmt2.execute("DROP TABLE LISTS CASCADE CONSTRAINTS");
//            stmt2.close();
        } catch (SQLException e) {
            //MainMenu.makeWarningDialog(EXCEPTION_TAG + " " + e.getMessage());
            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + "drop table error");
        }
    }

}
