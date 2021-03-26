package frontend.controller;

import backend.database.DatabaseConnect;
import frontend.model.Grocery;
import frontend.model.Recipe;
import frontend.model.User;
import frontend.ui.*;

import javax.swing.*;
import java.util.Date;

//ssh -l username -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca

public class Teashop {
	public static User user = null;
	public static DatabaseConnect database;
	public static LoginWindow loginWindow;
	public static RegisterWindow registerWindow;
	public static RecipeWindow recipeWindow;
	public static GroceryWindow groceryWindow;
	
	public void start() {
		database = new DatabaseConnect();
		database.setup();
		recipeWindow = new RecipeWindow();
		recipeWindow.showFrame();
	}

	public static void register(User user) {
		database.insertUser(user);
		registerWindow.dispose();
		loginWindow.showFrame();
	}

	public static void login(String name, String password, JPasswordField field) {
		boolean connected = database.login(name, password);
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

<<<<<<< HEAD
//	public static Recipe[] getAllRecipe() { // can we change return type Recipe[] to ArrayList<Recipe>?
//		return database.selectAllRecipe();
//	}
=======
	public static void updateRecipe(Recipe recipe) { database.updateRecipe(recipe); }

//	public static void deleteRecipe(String name) { database.deleteRecipe(name); }

	public static Recipe getRecipeByName(String name) { return database.selectRecipeByName(name); }

	public static Recipe[] getRecipeByKind(String kind) { return database.selectRecipeByKind(kind); }

	public static Recipe[] getAllRecipe() {
		return database.selectAllRecipe();
	}
>>>>>>> 7cf1208207a4bfef3cfee96c85340be9b5049893

	public static void makeRecipe(String name) { database.insertUsage(name, (java.sql.Date) new Date());}

	public static void addGrocery(Grocery grocery) { database.insertGrocery(grocery); }

	public static void updateGrocery(Grocery grocery) { database.updateGrocery(grocery); }

	public static Grocery[] orderGroceryByAmount() { return database.orderGroceryByAmount(); }

	public static Grocery[] orderGroceryByDate() { return database.orderGroceryByDate(); }

	public static Grocery getGrocery(String name) { return database.selectGrocery(name); }

	public static Grocery[] getAllGrocery() { return database.selectAllGrocery(); }

	public void finish() { database.close(); }

	public static void main(String args[]) {
		Teashop teashop = new Teashop();
		teashop.start();
	}
}
