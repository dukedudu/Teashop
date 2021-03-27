package backend.database;

import frontend.model.Grocery;
import frontend.model.Recipe;
import frontend.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";

    private Connection connection = null;

    public void databaseConnect() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            if (connection != null) { connection.close(); }
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
            stmt.executeUpdate("CREATE TABLE Recipe(RecipeId INT PRIMARY KEY, Name CHAR(20), Tea CHAR(20) NOT NULL, Calories INT DEFAULT 0, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0);");
            stmt.executeUpdate("CREATE TABLE Usage(UseID INT PRIMARY KEY, RecipeId INT, Date DATE NOT NULL, Tea CHAR(20) NOT NULL, Calories INT DEFAULT 0, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0," +
                    "FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId) ON DELETE SET NULL);\n");
            stmt.executeUpdate("CREATE TABLE Generates(RecipeId INT, UseID INT, PRIMARY KEY(RecipeId, UseID), " +
                    "FOREIGN KEY(RecipeId) REFERENCES Recipe ON DELETE CASCADE, FOREIGN KEY(UseId) REFERENCES Usage ON DELETE CASCADE);\n");
            stmt.executeUpdate("CREATE TABLE DailyReport(ReportID INT, UseID INT, Date DATE NOT NULL, Pearl INT DEFAULT 0, Jelly INT DEFAULT 0, Lemon INT DEFAULT 0, Orange INT DEFAULT 0, PRIMARY KEY(UseID, ReportID)," +
                    "FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE, FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE, FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE ReportWeekDay(Date DATE PRIMARY KEY, Weekday INT);");
            stmt.executeUpdate("CREATE TABLE Supplier(SupplierId INT PRIMARY KEY, CompanyName CHAR(20) NOT NULL UNIQUE);");
            stmt.executeUpdate("CREATE TABLE Supplies(SupplierId INT, GName CHAR(20), PRIMARY KEY (SupplierId, GName)," +
                    "FOREIGN KEY(SupplierId) REFERENCES Supplier ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);" );
            stmt.executeUpdate("CREATE TABLE ShoppingList(SListId INT PRIMARY KEY, GName CHAR(20), Amount INT NOT NULL, Date DATE, UserId INT," +
                    "FOREIGN KEY(UserId) REFERENCES User ON DELETE CASCADE, FOREIGN KEY(GName) REFERENCES Grocery ON DELETE CASCADE);");
            stmt.executeUpdate("CREATE TABLE Lists(UseID INT, GName CHAR(20), SListId INT, PRIMARY KEY (SListId, UseID, GName)," +
                    "FOREIGN KEY (SListId) REFERENCES ShoppingList ON DELETE CASCADE, FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE, FOREIGN KEY (UseId) REFERENCES Usage ON DELETE CASCADE);" );
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

    public void insertUser(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?,?)" );
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

    public void updateRecipe(Recipe recipe) { }

//    public void deleteRecipe(String name) { }

    public Recipe selectRecipeByName(String name) { return new Recipe(); }

    public Recipe[] selectRecipeByKind(String kind) { return new Recipe[0]; }

    public Recipe[] selectAllRecipe() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Recipe");
            while (rs1.next()) {
                Recipe temp = new Recipe(rs1.getString("name"),
                        rs1.getString("tea"),
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

    public void insertGrocery(Grocery grocery) { }

    public void updateGrocery(Grocery grocery) { }

    public Grocery[] orderGroceryByAmount() { return new Grocery[0]; }

    public Grocery[] orderGroceryByDate() { return new Grocery[0]; }

    public Grocery selectGrocery(String name) { return new Grocery(); }

    public Grocery[] selectAllGrocery() { return new Grocery[0]; }

    public void insertUsage(String name, Date date) {};

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
}