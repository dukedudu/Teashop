package frontend.controller;

import backend.database.DatabaseConnect;
import frontend.model.*;
import frontend.ui.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;

//ssh -l sunjingy -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca

public class Teashop {
	public static User user = null;
	public static DatabaseConnect database;
	public static LoginWindow loginWindow;
	public static RegisterWindow registerWindow;
	public static RecipeWindow recipeWindow;
	
	public void start() throws IOException {
		database = new DatabaseConnect();
		database.databaseConnect();
		//database.setup();
		loginWindow = new LoginWindow();
		recipeWindow = new RecipeWindow();
		registerWindow = new RegisterWindow();
		loginWindow.showFrame();
	}

	public void test(){
		database = new DatabaseConnect();
		database.databaseConnect();
		database.setup();

		System.out.println("test1: select password");
		System.out.println("Expected true. Actual " + database.selectPassword(new User("Sam", "123", "", 1, "", "")));
		System.out.println("Expected false. Actual " + database.selectPassword(new User("Sam", "234", "", 1, "", "")));

		System.out.println("test2: insert user (already test on database.setup) PASSED");

		System.out.println("test4: Select recipe by name");
		Recipe r = getRecipeByName("Pearl Milk Tea");
		System.out.println("Expected Pearl Milk Tea, Actual " + r.getName());

		System.out.println("test5: Select recipe by Uname");
		Recipe r3 = new Recipe("Orange tea", 0, 0, 0, 50);
		Recipe r4 = new Recipe("Lemon tea", 50, 0, 30, 0);
		database.insertRecipe(r3);
		database.insertRecipe(r4);
		Recipe[] t5Results = getMyRecipe("Sam");
		System.out.println("Expected Red tea, Orange tea and lemon tea.");
		for (Recipe tmp:t5Results){
			System.out.println("Actual "+tmp.getName());
		}

		System.out.println("test6: Insert Grocery and delete zero");
		database.deleteWithZero();

		System.out.println("test7: findEarliestAmount");
		Grocery g1 = new Grocery("Pearl",40,Date.valueOf("2021-08-11"), 20);
		database.insertGrocery(g1);
		Grocery g2 = new Grocery("Jelly",50,Date.valueOf("2021-01-11"), 20);
		database.insertGrocery(g2);
		Grocery g3 = new Grocery("Orange",40,Date.valueOf("2021-03-11"), 20);
		database.insertGrocery(g3);
		Grocery g4 = new Grocery("Lemon",200,Date.valueOf("2021-06-11"), 20);
		database.insertGrocery(g4);
		System.out.println("insert ok");
		System.out.println("Expected 2021-03-30, Actual "
				+Grocery.subtractDays(database.findEarliestAmount("Pearl").getExpiryDate(),database.findEarliestAmount("Pearl").getDuration()));
		System.out.println("Expected 2021-01-11, Actual "
				+Grocery.subtractDays(database.findEarliestAmount("Jelly").getExpiryDate(),database.findEarliestAmount("Jelly").getDuration()));
		System.out.println("Expected 2021-03-11, Actual "
				+Grocery.subtractDays(database.findEarliestAmount("Orange").getExpiryDate(),database.findEarliestAmount("Orange").getDuration()));
		System.out.println("Expected 2021-06-11, Actual "
				+Grocery.subtractDays(database.findEarliestAmount("Lemon").getExpiryDate(),database.findEarliestAmount("Lemon").getDuration()));

		System.out.println("test 8: grocery sum");
		database.sumGroceryAmount("Pearl");

		System.out.println("test 9: nested aggr");
		System.out.println(database.recommendKind("Pearl"));

		System.out.println("test 10: make beverage");
		database.makeRecipe("Pearl Milk Tea", Date.valueOf("2021-3-31")); // enough case
		database.makeRecipe("Pearl Milk Tea", Date.valueOf("2021-3-31")); // enough case
		database.makeRecipe("Pearl Milk Tea", Date.valueOf("2021-3-31")); // enough case
		database.makeRecipe("Pearl Milk Tea", Date.valueOf("2021-3-31")); // enough case
		database.makeRecipe("Pearl Milk Tea", Date.valueOf("2021-3-31")); // not enough case
		System.out.println(database.recommendKind("Pearl"));

		System.out.println("test 11: division");
		DailyReport d1 = new DailyReport(Date.valueOf("2021-4-1"),20,0,0,0);
		DailyReport d2 = new DailyReport(Date.valueOf("2021-4-2"),20,20,0,0);
		DailyReport dAll = new DailyReport(Date.valueOf("2021-4-3"),20,20,20,20);
		database.insertDailyReport(d1);
		database.insertDailyReport(d2);
		database.insertDailyReport(dAll);
		DailyReport[] dd1 = database.selectReportWithEvery();
		for(DailyReport d: dd1){
			System.out.println(d.getDate().toString());
		}
	}

	public static void register(User user) throws IOException {
		database.insertUser(user);
		registerWindow.dispose();
		loginWindow = new LoginWindow();
		loginWindow.showFrame();
	}

	public static void login(User user, JPasswordField field) throws IOException {
		boolean correct = database.selectPassword(user);
		if (correct) {
			loginWindow.dispose();
			recipeWindow = new RecipeWindow();
			recipeWindow.showFrame();
		}
		else { failed(field); }
	}

	public static void failed(JPasswordField field) { field.setText(""); }

	public static void addRecipe(Recipe recipe) { database.insertRecipe(recipe); }

	public static void updateRecipe(Recipe recipe) { database.updateRecipe(recipe); }

	public static Recipe[] getAllRecipe() { return database.selectAllRecipe(); }

	public static boolean makeRecipe(Recipe recipe, Date date) { return database.makeRecipe(recipe.getName(), date);}

	public static Recipe[] getMyRecipe(String name) { return database.selectRecipeByUname(name);}

	public static Recipe getRecipeByName(String name) { return database.selectRecipeByRname(name); }

	public static String getRecommendedKind(String gname) { return database.recommendKind(gname); }

	public static void addGrocery(Grocery grocery) { database.insertGrocery(grocery); }

	public static void updateGrocery(Grocery grocery) { database.updateGrocery(grocery); }

	public static Grocery getGrocery(String name, Date date) { return database.selectGrocery(name, date); }

	public static Grocery[] getAllGrocery() { return database.selectAllGrocery(); }

	public static Grocery[] orderGroceryByDate() { return database.orderGroceryByDate(); }

	public static Grocery[] getGrocerySum(String name) { return database.sumGroceryAmount(name); }

	public static DailyReport[] getAllReport() { return database.selectAllReport(); }

	public static DailyReport[] getReportByName(String name) { return database.selectReportByGname(name); }

	public static DailyReport[] getReportWithEvery() { return database.selectReportWithEvery(); }

	public static ShoppingList[] getAllList() { return database.selectAllList(); }

	public static ShoppingList[] getListByDate(Date date1, Date date2) { return database.selectListByDate(date1, date2); }

	public static ShoppingList[] getListByName(String name) { return database.selectListByGname(name); }

	public void finish() { database.close(); }

	public static void main(String args[]) throws IOException {
		Teashop teashop = new Teashop();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("nimbusBase", new Color(100,175,47,255));
		} catch(Exception ignored){ }
		teashop.start();
		//teashop.test();
	}
}
