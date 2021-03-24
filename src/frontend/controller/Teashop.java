package frontend.controller;

import frontend.ui.LoginWindow;
import frontend.ui.RecipeWindow;
import frontend.ui.RegisterWindow;
//ssh -l username -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca
public class Teashop {
	private LoginWindow loginWindow = null;
	private RegisterWindow registerWindow = null;
	private RecipeWindow recipeWindow = null;
	
	private void start() {
		recipeWindow = new RecipeWindow();
		recipeWindow.showFrame();
	}

	private void finish() {

	}

	public static void main(String args[]) {
		Teashop teashop = new Teashop();
		teashop.start();
	}
}
