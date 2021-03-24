package frontend.controller;

import backend.database.DatabaseConnect;
import frontend.model.Recipe;
import frontend.model.User;
import frontend.ui.LoginWindow;
import frontend.ui.RecipeWindow;
import frontend.ui.RegisterWindow;

import javax.swing.*;
import java.util.ArrayList;

//ssh -l username -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca

public class Teashop {
	public static User user = null;
	public static DatabaseConnect database;
	public static LoginWindow loginWindow = null;
	public static RegisterWindow registerWindow = null;
	public static RecipeWindow recipeWindow = null;
	
	public void start() {
		database = new DatabaseConnect();
		recipeWindow = new RecipeWindow();
		recipeWindow.showFrame();
	}

	public static void login(String name, String password, JPasswordField field) {
		boolean connected = database.login(name, password);
		if (connected) { loginWindow.dispose(); }
		else { failed(field); }
	}

	public static void failed(JPasswordField field) {
		field.setText("");
	}

	public static void register(User user) {
		database.insertUser(user);
		registerWindow.dispose();
		loginWindow.showFrame();
	}

	public static Recipe[] getAllRecipe() {
		return database.selectAllRecipe();
	}

	public static void addRecipe(Recipe recipe) {
		database.insertRecipe(recipe);
	}

	public void finish() {

	}

	public static void main(String args[]) {
		Teashop teashop = new Teashop();
		teashop.start();
	}
}
