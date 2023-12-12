package enter;
import profile.*;
import discover.*;
import log.BaseLogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class LoginPage extends EnterancePage {
	
	JButton loginButton = button("Log in");
	JButton signupButton = button("Sign up!");
	JTextField username;
	JTextField password;
	LoginValidation validation = new LoginValidation();	
	
	/**
	 * A subclass of EnterancePage. Inherits the Frame and the title() from its superclass.
	 */
	public LoginPage() {
		super();
		title();
		structure();
		frame.setVisible(true);
	}
	
	/**
	 * Creates the structure of the LoginPage. Creates username, password fields using superclass' textPanel() method.
	 */
	public void structure() {
		
		JPanel mainPanel = new JPanel();
		
		mainPanel.setPreferredSize(new Dimension(500, 200));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setOpaque(false);
		
		mainPanel.add(textPanel("Username: "));
		username = getTextField();
		mainPanel.add(textPanel("Password: "));
		password = getTextField();
		
		loginButton.addActionListener(this);
		mainPanel.add(loginButton);
		
		JPanel signupPanel = textButtonPanel("Don't have an account? Sign up right here!!", signupButton);
		signupButton.addActionListener(this);
		mainPanel.add(signupPanel);
		
		frame.add(mainPanel);
	}

	/**
	 * When pressed to login Button, it validates information using validation object from LoginValidation class.
	 * If verifies, it sends the user to his/her PrivateProfilePage.
	 * When pressed to signup Button, it sends the user to SignupPage.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			if (validation.validation("users.txt", username.getText(), password.getText())) {
				JOptionPane.showMessageDialog(null, "Welcome to PhotoCloud!","Heyoo!", JOptionPane.INFORMATION_MESSAGE);
				new PrivateProfilePage(username.getText(), userList);
				frame.dispose();
				BaseLogger.info().log("<"+username.getText()+"> logged in.");
			}
			else {
				JOptionPane.showMessageDialog(null, validation.getTextMessage(),"Invalid Input", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if (e.getSource() == signupButton) {
			new SignupPage();
			frame.dispose();
		}
	}
}