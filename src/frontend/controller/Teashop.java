package frontend.controller;

import backend.database.DatabaseConnect;
import frontend.model.*;
import frontend.ui.*;

import javax.swing.*;
import java.sql.Date;
import java.util.Arrays;

//ssh -l username -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca

public class Teashop {
	public static User user = null;
	public static DatabaseConnect database;
	public static LoginWindow loginWindow;
	public static RegisterWindow registerWindow;
	public static RecipeWindow recipeWindow;
	public static GroceryWindow groceryWindow;
	public static ReportListWindow reportListWindow;
	
	public void start() {
		database = new DatabaseConnect();
		database.databaseConnect();
		database.setup();
		System.out.println(Arrays.stream(getAllGrocery()).toString());
//		recipeWindow = new RecipeWindow();
//		recipeWindow.showFrame();
	}

	public static void register(User user) {
		database.insertUser(user);
		registerWindow.dispose();
		loginWindow.showFrame();
	}

	public static void login(String name, String password, JPasswordField field) {
		boolean connected = database.selectPassword(name, password);
		if (connected) { loginWindow.dispose(); }
		else { failed(field); }
		//database.checkPassword --use this to login
	}

	public static void failed(JPasswordField field) {
		field.setText("");
	}


//	public static void forgotPassword(String Name, String newPassword, String confirmPassword) {
//		database.changePassword(Name, newPassword, confirmPassword);
//		//registerWindow.dispose();
//		//loginWindow.showFrame();
//	}

	public static void addRecipe(Recipe recipe) {
		database.insertRecipe(recipe);
	}

//	public static Recipe[] getAllRecipe() { // can we change return type Recipe[] to ArrayList<Recipe>?
//		return database.selectAllRecipe();
//	}
	public static void updateRecipe(Recipe recipe) { database.updateRecipe(recipe); }

//	public static void deleteRecipe(String name) { database.deleteRecipe(name); }

	public static Recipe[] getAllRecipe() { return database.selectAllRecipe(); }

	public static void makeRecipe(String name) { database.insertUsage(name, new Date(0));}

	public static Recipe[] getMyRecipe(String name) { return database.selectRecipeByUname(name); }

	public static Recipe getRecipeByName(String name) { return database.selectRecipeByRname(name); }

	public static Recipe[] getRecipeByKind(String kind) { return database.selectRecipeByKind(kind); }

	public static void addGrocery(Grocery grocery) { database.insertGrocery(grocery); }

	public static void updateGrocery(Grocery grocery) { database.updateGrocery(grocery); }

	public static Grocery getGrocery(String name) { return database.selectGrocery(name); }

	public static Grocery[] getAllGrocery() { return database.selectAllGrocery(); }

	public static Grocery[] orderGroceryByDate() { return database.orderGroceryByDate(); }

	public static int getGroceryAmountSum(String name) { return database.sumGroceryAmount(name); }

	public static DailyReport[] getAllReport() { return database.selectAllReport(); }

	public static DailyReport[] getReportByName(String name) { return database.selectReportByGname(name); }

	public static DailyReport[] getReportWithEvery() { return database.selectReportWithEvery(); }

	public static ShoppingList[] getAllList() { return database.selectAllList(); }

	public static ShoppingList[] getListByDate(Date date1, Date date2) { return database.selectListByDate(date1, date2); }

	public static ShoppingList[] getListByName(String name) { return database.selectListByGname(name); }

	public void finish() { database.close(); }

	public static void main(String args[]) {
		Teashop teashop = new Teashop();
		teashop.start();
	}
}
