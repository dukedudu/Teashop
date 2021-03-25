package backend.database;

import frontend.model.BranchModel;
import frontend.model.Recipe;
import frontend.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnect() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void setup() {
        dropBranchTableIfExists();
        try {
            Statement stmt = connection.createStatement();
            //stmt.executeUpdate("CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)");
            stmt.executeUpdate("CREATE TABLE User " +
                    "( Id INT PRIMARY KEY, Name CHAR(20) NOT NULL, StreetName CHAR(20), HouseNumber INT, City CHAR(20), PostalCode CHAR(20), Certificate CHAR(20), Budget INT,\n" +
                    "FOREIGN KEY(PostalCode) REFERENCES Address(PostalCode) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY(StreetName) REFERENCES Address(StreetName) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY(House#) REFERENCES Address(House#) ON DELETE CASCADE));\n" );
            stmt.executeUpdate("CREATE TABLE Address(PostalCode CHAR(20),StreetName CHAR(20),House# INT,City CHAR(20),\n" +
                    "PRIMARY KEY(PostalCode, StreetName, House#));\n" );
            stmt.executeUpdate("CREATE TABLES Grocery(\n" +
                    "GName CHAR(20) PRIMARY KEY,Amount INT DEFAULT 0, \n" +
                    "BuyingDate DATE, \n" +
                    "Duration INT,\n" +
                    "FOREIGN KEY BuyingDate REFERENCES GroceryDate(BuyingDate) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY Duration REFERENCES GroceryDate(Duration) ON DELETE CASCADE)" );
            stmt.executeUpdate("CREATE TABLES GroceryDate(\n" +
                    "BuyingDate DATE, \n" +
                    "Duration INT,\n" +
                    "ExpiryDate DATE,\n" +
                    "PRIMARY KEY(BuyingDate, Duration));\n" );
            stmt.executeUpdate("CREATE TABLES Buys(\n" +
                    "UserId INT,\n" +
                    "GName CHAR(20),\n" +
                    "BuyingDate DATE,\n" +
                    "PRIMARY(UserId, GName, BuyingDate),\n" +
                    "FOREIGN KEY(UserId) REFERENCES User(UserId) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY(GName) REFERENCES Grocery(GName) ON DELETE CASCADE,\n" +
                    "FOREIGN KEY(BuyingDate) REFERENCES Grocery(BuyingDate) ON DELETE CASCADE);\n" );
            stmt.executeUpdate("CREATE TABLES Recipe(\n" +
                    "RecipeId INT PRIMARY KEY,\n" +
                    "Name CHAR(20),\n" +
                    "Tea CHAR(20) NOT NULL,\n" +
                    "Calories INT DEFAULT 0,\n" +
                    "Perl INT DEFAULT 0, \n" +
                    "Jelly INT DEFAULT 0, \n" +
                    "Lemon INT DEFAULT 0, \n" +
                    "Orange INT DEFAULT 0);\n" );
            stmt.executeUpdate("CREATE TABLE Usage(\n" +
                    "UseID INT PRIMARY KEY, \n" +
                    "RecipeId INT,\n" +
                    "Date DATE NOT NULL,\n" +
                    "Tea CHAR(20) NOT NULL,\n" +
                    "Calories INT DEFAULT 0,\n" +
                    "Perl INT DEFAULT 0, \n" +
                    "Jelly INT DEFAULT 0, \n" +
                    "Lemon INT DEFAULT 0, \n" +
                    "Orange INT DEFAULT 0,\n" +
                    "FOREIGN KEY RecipeId REFERENCES Recipe(RecipeId) ON DELETE SET NULL);\n" );
            stmt.executeUpdate("CREATE TABLE Generates(\n" +
                    "RecipeId INT,\n" +
                    "UseID INT,\n" +
                    "PRIMARY KEY(RecipeId, UseID),\n" +
                    "FOREIGN KEY RecipeId REFERENCES Recipe ON DELETE CASCADE,\n" +
                    "FOREIGN KEY UseId REFERENCES Usage ON DELETE CASCADE);\n" );
            stmt.executeUpdate("CREATE TABLE DailyReport (\n" +
                    "ReportID INT,\n" +
                    "UseID INT,\n" +
                    "Date DATE NOT NULL,\n" +
                    "UserID INT,\n" +
                    "Perl INT DEFAULT 0,\n" +
                    "Jelly INT DEFAULT 0,\n" +
                    "Lemon INT DEFAULT 0,\n" +
                    "Orange INT DEFAULT 0,\n" +
                    "PRIMARY KEY(UseID, ReportID),\n" +
                    "FOREIGN KEY (UserID) REFERENCES User ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (Date) REFERENCES ReportWeekDay ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (Weekday) REFERENCES ReportWeekDay ON DELETE CASCADE);" );
            stmt.executeUpdate("CREATE TABLE ReportWeekDay (\n" +
                    "Date DATE PRIMARY KEY, \n" +
                    "Weekday INT);\n" );
            stmt.executeUpdate("CREATE TABLE Supplier (\n" +
                    "SupplierId INT PRIMARY KEY,\n" +
                    "CompanyName CHAR(20) NOT NULL UNIQUE);\n" );
            stmt.executeUpdate("CREATE TABLE Supplies (\n" +
                    "SupplierId INT,\n" +
                    "GName CHAR(20),\n" +
                    "PRIMARY KEY (SupplierId, GName),\n" +
                    "FOREIGN KEY (SupplierId) REFERENCES Supplier ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE);\n" );
            stmt.executeUpdate("CREATE TABLE ShoppingList (\n" +
                    "SListId INT PRIMARY KEY,\n" +
                    "GName CHAR(20),\n" +
                    "Amount INT NOT NULL,\n" +
                    "Date DATE,\n" +
                    "UserId INT,\n" +
                    "FOREIGN KEY (UserId) REFERENCES User ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE);\n" );
            stmt.executeUpdate("CREATE TABLE Lists (\n" +
                    "UseID INT,\n" +
                    "GName CHAR(20),\n" +
                    "SListId INT,\n" +
                    "PRIMARY KEY (SListId, UseID, GName),\n" +
                    "FOREIGN KEY (SListId) REFERENCES ShoppingList ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (GName) REFERENCES Grocery ON DELETE CASCADE,\n" +
                    "FOREIGN KEY (UseId) REFERENCES Usage ON DELETE CASCADE);\n" );
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        User u1 = new User("Sam", "123", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 500);
        insertUser(u1);
        User u2 = new User("Lily", "234", "V6S1H6", "23rd W Ave", 2341, "Beverage Maker", 400);
//        BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
//        insertBranch(branch1);
//        BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
//        insertBranch(branch2);
    }

    public void insertBranch(BranchModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)" );
            ps.setInt(1, model.getId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getAddress());
            ps.setString(4, model.getCity());
            if (model.getPhoneNumber() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, model.getPhoneNumber());
            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteBranch(int branchId) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?" );
            ps.setInt(1, branchId);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!" );
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateBranch(int id, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?" );
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Branch " + id + " does not exist!" );
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public BranchModel[] getBranch() {
        ArrayList<BranchModel> result = new ArrayList<BranchModel>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM branch" );

            while (rs.next()) {
                BranchModel model = new BranchModel(rs.getString("branch_addr" ),
                        rs.getString("branch_city" ),
                        rs.getInt("branch_id" ),
                        rs.getString("branch_name" ),
                        rs.getInt("branch_phone" ));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new BranchModel[result.size()]);
    }

    private void dropBranchTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables" );

            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals("branch" )) {
                    stmt.execute("DROP TABLE branch" );
                    break;
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String name, String password) {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ORACLE_URL, name, password);
            connection.setAutoCommit(false);
            System.out.println("Logged in\n" );
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    //login view:
    public boolean checkPassword(String UserId, String enterPassword) {
        /**
         * input: UserId and password from login view
         * return: true if entered password equals with password from DB, false otherwise.
         * */
        String truthPassword = "";
        boolean correct = false;
        ArrayList<Integer> UidPool = new ArrayList<>();
        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT UserId FROM User" );
            while (rs1.next()) {
                UidPool.add(Integer.parseInt(rs1.getString("UserId" )));
            }
            rs1.close();
            stmt1.close();
            if (UidPool.contains(UserId)) {
                Statement stmt2 = connection.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT Password FROM User WHERE UserId=" + UserId);
                truthPassword = rs2.getString("Password" );
                correct = (truthPassword.equals(enterPassword));
                rs2.close();
                stmt2.close();
            } else {
                System.out.println("Uid not exists" );
            }

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return correct;
    }

    public void changePassword(String UserId, String newPassword, String confirmPassword) {
        ArrayList<Integer> UidPool = new ArrayList<>();
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("confirmPassword should be same as newPassword." );
            return;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE User SET Password = ? WHERE UserId = ?" );
            ps.setString(1, UserId);
            ps.setString(2, newPassword);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                //MainMenu.makeWarningDialog(WARNING_TAG + " Resident " + sin + " does not exist!");
                System.out.println(WARNING_TAG + " User does not exist!" );
            }
            connection.commit();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?,?)" );
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getCode());
            statement.setString(4, user.getStreet());
            statement.setInt(5, user.getHouse());
            statement.setString(6, user.getCertificate());
            statement.setInt(7, user.getBudget());
            statement.executeUpdate();
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRecipe(Recipe recipe) {

    }

    public Recipe[] selectAllRecipe() {
        return new Recipe[0];
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
}
