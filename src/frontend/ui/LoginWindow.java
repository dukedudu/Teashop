package frontend.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import frontend.controller.Teashop;
import frontend.model.User;

public class LoginWindow extends JFrame implements ActionListener {
	public static User user;
	private JPanel panel;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel label_name, label_pwd;
	private JTextField field_name;
	private JPasswordField field_pwd;
	private JButton button_login, button_register;
	private String name, password;

	public LoginWindow() {
		super("User Login");
	}

	public void showFrame() throws IOException {
		user = new User();
		panel = new JPanel();
		this.setContentPane(panel);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();

		label_name = new JLabel("Name: ");
		field_name = new JTextField(10);
		label_pwd = new JLabel("Password: ");
		field_pwd = new JPasswordField(10);
		field_pwd.setEchoChar('*');
		button_login = new JButton("Log In");
		button_register = new JButton("Register");

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

		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(5, 20, 10, 10);
		constraints.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(button_login, constraints);
		panel.add(button_login);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 10, 10, 10);
		constraints.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(button_register, constraints);
		panel.add(button_register);

		button_login.addActionListener(this);
		button_register.addActionListener(this);

		panel.setBackground(Color.WHITE);
		Image image = ImageIO.read(this.getClass().getResource("/resources/icon.png"));
		this.setIconImage(new ImageIcon(image).getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_login) {
			user.setName(field_name.getText());
			user.setPassword(String.valueOf(field_pwd.getPassword()));
			try {
				Teashop.login(user, field_pwd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			this.dispose();
			RegisterWindow registerWindow = new RegisterWindow();
			try {
				registerWindow.showFrame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static User getUser() { return user; }
}
