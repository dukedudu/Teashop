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
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE User(Name CHAR(20) PRIMARY KEY, StreetName CHAR(20), HouseNumber INT, City CHAR(20), PostalCode CHAR(20), Certificate CHAR(20), Budget INT," +
                    "FOREIGN KEY(PostalCode) REFERENCES Address(PostalCode) ON DELETE CASCADE, FOREIGN KEY(StreetName) REFERENCES Address(StreetName) ON DELETE CASCADE, FOREIGN KEY(House#) REFERENCES Address(House#) ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Address(PostalCode CHAR(20),StreetName CHAR(20),House# INT,City CHAR(20), PRIMARY KEY(PostalCode, StreetName, House#));");
            stmt.executeUpdate("CREATE TABLE Grocery(GName CHAR(20) PRIMARY KEY,Amount INT DEFAULT 0, BuyingDate DATE, Duration INT," +
                    "FOREIGN KEY(BuyingDate) REFERENCES GroceryDate(BuyingDate) ON DELETE CASCADE, FOREIGN KEY(Duration) REFERENCES GroceryDate(Duration) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE GroceryDate(BuyingDate DATE, Duration INT, ExpiryDate DATE, PRIMARY KEY(BuyingDate, Duration));");
            stmt.executeUpdate("CREATE TABLE Buys(UserId INT, GName CHAR(20), BuyingDate DATE, PRIMARY KEY(UserId, GName, BuyingDate)," +
                    "FOREIGN KEY(UserId) REFERENCES User(UserId) ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery(GName) ON DELETE CASCADE, FOREIGN KEY(BuyingDate) REFERENCES Grocery(BuyingDate) ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Recipe(RName CHAR(20) PRIMARY KEY, Kind CHAR(20), Tea CHAR(20) NOT NULL, Calories INT DEFAULT 0, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0);");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RecipeId INT, Date DATE NOT NULL, Tea CHAR(20) NOT NULL, Calories INT DEFAULT 0, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0," +
                    "FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId) ON DELETE SET NULL);\n");
            stmt.executeUpdate("CREATE TABLE Generates(RecipeId INT, UseID INT, PRIMARY KEY(RecipeId, UseID), " +
                    "FOREIGN KEY(RecipeId) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage ON DELETE CASCADE);\n");
            stmt.executeUpdate("CREATE TABLE DailyReport(ReportID INT, UseID INT, Date DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0, PRIMARY KEY(UseID, ReportID)," +
                    "FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName CHAR(20) NOT NULL UNIQUE);");
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName CHAR(20), PRIMARY KEY (SupplierId, GName)," +
                    "FOREIGN KEY(SupplierId) REFERENCES Supplier ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE ShoppingList(SListId INT AUTO_INCREMENT, GName CHAR(20), Amount INT NOT NULL, Date DATE, UserId INT, PRIMARY KEY (SListId)" +
                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Lists(UseID INT, GName CHAR(20), SListId INT, PRIMARY KEY (SListId, UseID, GName)," +
                    "FOREIGN KEY (SListId) REFERENCES ShoppingList ON DELETE CASCADE, FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE, FOREIGN KEY (UseId) REFERENCES Usage ON DELETE CASCADE);");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        User u1 = new User("Sam", "123", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 500);
        insertUser(u1);
        User u2 = new User("Lily", "234", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 400);
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Recipe VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, recipe.getName());
            //statement.setString(2, recipe.getTea());
            statement.setInt(3, recipe.getPearl());
            statement.setInt(4, recipe.getJelly());
            statement.setInt(5, recipe.getLemon());
            statement.setInt(6, recipe.getOrange());
            statement.setInt(7, recipe.getCalories());
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
            PreparedStatement ps = connection.prepareStatement("UPDATE Recipe SET tea=?, kind = ?, pearl=?, jelly=?, lemon=?, orange=?, calories=? WHERE name=?");
            ps.setString(1, recipe.getTea());
            ps.setInt(2, recipe.getPearl());
            ps.setInt(3, recipe.getJelly());
            ps.setInt(4, recipe.getLemon());
            ps.setInt(5, recipe.getOrange());
            ps.setInt(6, recipe.getCalories());
            ps.setString(7, recipe.getName());

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
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe WHERE RName=" + rname);
            result = new Recipe(rs1.getString("name"),
                    rs1.getString("tea"),
                    rs1.getString("Kind"),
                    rs1.getInt("pearl"),
                    rs1.getInt("jelly"),
                    rs1.getInt("lemon"),
                    rs1.getInt("orange"),
                    rs1.getInt("calories")
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
    //    public void changePassword(String UserName, String newPassword, String confirmPassword) {
//        ArrayList<Integer> UidPool = new ArrayList<>();
//        if (!newPassword.equals(confirmPassword)) {
//            System.out.println("confirmPassword should be same as newPassword." );
//            return;
//        }
//        try {
//            PreparedStatement ps = connection.prepareStatement("UPDATE User SET Password = ? WHERE UserId = ?" );
//            ps.setString(1, UserName);
//            ps.setString(2, newPassword);
//
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                //MainMenu.makeWarningDialog(WARNING_TAG + " Resident " + sin + " does not exist!");
//                System.out.println(WARNING_TAG + " User does not exist!" );
//            }
//            connection.commit();
//            ps.close();
//
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
}