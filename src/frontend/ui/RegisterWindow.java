package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterWindow extends JFrame implements ActionListener {
	private User user;
	private JPanel panel;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel label_name, label_pwd, label_street, label_house, label_code, label_budget, label_ctf;
	private JTextField field_name, field_street, field_house, field_code, field_budget, field_ctf;
	private JPasswordField field_pwd;
	private JButton button;

	public RegisterWindow() {
		super("User Register");
	}

	public void showFrame() {
		panel = new JPanel();
		this.setContentPane(panel);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();

		label_name = new JLabel("Name: ");
		field_name = new JTextField(10);
		label_pwd = new JLabel("Password: ");
		field_pwd = new JPasswordField(10);
		label_street = new JLabel("Street Number: ");
		field_street = new JTextField(10);
		label_house = new JLabel("House Number: ");
		field_house = new JTextField(10);
		label_code = new JLabel("Postal Code: ");
		field_code = new JTextField(10);
		field_pwd.setEchoChar('*');
		button = new JButton("Register");

		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 10, 5, 0);
		layout.setConstraints(label_name, constraints);
		panel.add(label_name);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 0, 5, 10);
		layout.setConstraints(field_name, constraints);
		panel.add(field_name);

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 10, 10, 0);
		layout.setConstraints(label_pwd, constraints);
		panel.add(label_pwd);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 0, 10, 10);
		layout.setConstraints(field_pwd, constraints);
		panel.add(field_pwd);

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 10, 5, 0);
		layout.setConstraints(label_street, constraints);
		panel.add(label_street);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 0, 5, 10);
		layout.setConstraints(field_street, constraints);
		panel.add(field_street);

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 10, 5, 0);
		layout.setConstraints(label_house, constraints);
		panel.add(label_house);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 0, 5, 10);
		layout.setConstraints(field_house, constraints);
		panel.add(field_house);

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 10, 5, 0);
		layout.setConstraints(label_code, constraints);
		panel.add(label_code);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 0, 5, 10);
		layout.setConstraints(field_code, constraints);
		panel.add(field_code);

//		constraints.gridwidth = GridBagConstraints.RELATIVE;
//		constraints.insets = new Insets(5, 10, 5, 0);
//		layout.setConstraints(label_budget, constraints);
//		panel.add(label_code);
//
//		constraints.gridwidth = GridBagConstraints.REMAINDER;
//		constraints.insets = new Insets(5, 0, 5, 10);
//		layout.setConstraints(field_budget, constraints);
//		panel.add(field_code);
//
//		constraints.gridwidth = GridBagConstraints.RELATIVE;
//		constraints.insets = new Insets(5, 10, 5, 0);
//		layout.setConstraints(label_ctf, constraints);
//		panel.add(label_code);
//
//		constraints.gridwidth = GridBagConstraints.REMAINDER;
//		constraints.insets = new Insets(5, 0, 5, 10);
//		layout.setConstraints(field_ctf, constraints);
//		panel.add(field_code);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(button, constraints);
		panel.add(button);

		button.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setLocation(850, 450);
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		user = new User();
		user.setName(field_name.getText());
		user.setPassword(String.valueOf(field_pwd.getPassword()));
		user.setStreet(field_street.getText());
		user.setHouseNumber(Integer.parseInt(field_house.getText()));
		user.setCode(field_code.getText());
//		user.setBudget(Integer.parseInt(field_budget.getText()));
//		user.setCode(field_code.getText());
		Teashop.register(user);
		this.dispose();
		RecipeWindow recipeWindow = new RecipeWindow();
		recipeWindow.showFrame();
	}
}
