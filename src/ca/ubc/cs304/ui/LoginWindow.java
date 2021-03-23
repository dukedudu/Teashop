package ca.ubc.cs304.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ca.ubc.cs304.database.DatabaseConnect;

public class LoginWindow extends JFrame implements ActionListener {
	private JPanel panel;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel label_name, label_pwd;
	private JTextField field_name;
	private JPasswordField field_pwd;
	private JButton button;
	private String name, password;

	public LoginWindow() {
		super("User Login");
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
		field_pwd.setEchoChar('*');
		button = new JButton("Log In");

		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(10, 10, 5, 0);
		layout.setConstraints(label_name, constraints);
		panel.add(label_name);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(10, 0, 5, 10);
		layout.setConstraints(field_name, constraints);
		panel.add(field_name);

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(0, 10, 10, 0);
		layout.setConstraints(label_pwd, constraints);
		panel.add(label_pwd);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(0, 0, 10, 10);
		layout.setConstraints(field_pwd, constraints);
		panel.add(field_pwd);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 10, 10, 10);
		constraints.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(button, constraints);
		panel.add(button);

		button.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.setLocation(850, 450);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		name = field_name.getText();
		password = String.valueOf(field_pwd.getPassword());
		login(name, password);
	}

	public void login(String name, String password) {
		DatabaseConnect datacase = new DatabaseConnect();
		boolean connected = datacase.login(name, password);
		if (connected) { this.dispose(); }
		else { failed(); }
	}

	public void failed() {
		field_pwd.setText("");
	}
}
