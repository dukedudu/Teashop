package backend.database;

import java.sql.*;
import java.util.ArrayList;

import frontend.model.BranchModel;
import frontend.model.User;

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
			stmt.executeUpdate("CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)");
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
		insertBranch(branch1);
		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
		insertBranch(branch2);
	}

	public void insertBranch(BranchModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
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
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
			ps.setInt(1, branchId);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
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
			PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
			ps.setString(1, name);
			ps.setInt(2, id);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
			
			while(rs.next()) {
				BranchModel model = new BranchModel(rs.getString("branch_addr"),
													rs.getString("branch_city"),
													rs.getInt("branch_id"),
													rs.getString("branch_name"),
													rs.getInt("branch_phone"));
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
			ResultSet rs = stmt.executeQuery("select table_name from user_tables");

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("branch")) {
					stmt.execute("DROP TABLE branch");
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
			if (connection != null) { connection.close(); }
			connection = DriverManager.getConnection(ORACLE_URL, name, password);
			connection.setAutoCommit(false);
			System.out.println("Logged in\n");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	//login view:
	public boolean checkPassword(String UserId, String enterPassword){
		/**
		 * input: UserId and password from login view
		 * return: true if entered password equals with password from DB, false otherwise.
		 * */
		String truthPassword = "";
		boolean correct = false;
		ArrayList<Integer> UidPool = new ArrayList<>();
		try {
			Statement stmt1 = connection.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT UserId FROM User");
			while(rs1.next()){
				UidPool.add(Integer.parseInt(rs1.getString("UserId")));
			}
			rs1.close();
			stmt1.close();
			if(UidPool.contains(UserId)){
				Statement stmt2 = connection.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT Password FROM User WHERE UserId="+UserId);
				truthPassword = rs2.getString("Password");
				correct = (truthPassword.equals(enterPassword));
				rs2.close();
				stmt2.close();
			}
			else{
				System.out.println("Uid not exists");
			}

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return correct;
	}

	public void changePassword(String UserId, String newPassword, String confirmPassword){
		ArrayList<Integer> UidPool = new ArrayList<>();
		if (!newPassword.equals(confirmPassword)){
			System.out.println("confirmPassword should be same as newPassword.");
			return;
		}
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE User SET Password = ? WHERE UserId = ?");
			ps.setString(1,UserId);
			ps.setString(2,newPassword);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				//MainMenu.makeWarningDialog(WARNING_TAG + " Resident " + sin + " does not exist!");
				System.out.println(WARNING_TAG + " User does not exist!");
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
			PreparedStatement statement = connection.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?,?,?,?,?,?)");

			statement.setInt(1, user.getId());
			statement.setString(2, user.getName());
			if (!user.getStreet().isEmpty()) { statement.setString(3, user.getStreet()); }
			else { statement.setNull(3, Types.CHAR); }
			if (user.getHouse() == 0) { statement.setInt(4, user.getHouse()); }
			else { statement.setNull(4, Types.INTEGER); }
			if (!user.getCity().isEmpty()) { statement.setString(5, user.getCity()); }
			else { statement.setNull(5, Types.CHAR); }
			if (!user.getCode().isEmpty()) { statement.setString(6, user.getCode()); }
			else { statement.setNull(6, Types.CHAR); }
			if (user.getBudget() == 0) { statement.setInt(7, user.getBudget()); }
			else { statement.setNull(7, Types.INTEGER); }
			if (!user.getCertificate().isEmpty()) { statement.setString(8, user.getCertificate()); }
			else { statement.setNull(8, Types.CHAR); }

			statement.executeUpdate();
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) { connection.close(); }
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
