package enter;
import discover.*;
import profile.*;

import log.BaseLogger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;

import javax.swing.*;

public class SignupPage extends EnterancePage {
	
	JPanel mainPanel = new JPanel();
	JButton signupButton = button("Sign up!");
	JButton backButton = button("Back");
	JButton photoButton = button("Select Photo");
	JTextField name;
	JTextField surname;
	JTextField age;
	JTextField email;
	JTextField username;
	JTextField password;
	JTextField aboutMe;
	JRadioButton free = new JRadioButton("Free");
	JRadioButton hobbyist = new JRadioButton("Hobbyist");
	JRadioButton profesional = new JRadioButton("Professional");
	
	SignupValidation validation = new SignupValidation();
	String path = "Default_PP.png";
	String tier;
	String[] userInfo;
	User user;

	/**
	 *  A subclass of EnterancePage. Inherits the Frame and the title() from its superclass.
	 */
	public SignupPage() {
		super();
		title();
		structure();
		JOptionPane.showMessageDialog(null, "Your Name, Surname and Username can't contain special characters."
				+ "\nYou must be older than 12 years old.\nYour Email should end with @ku.edu.tr."
				+ "\nYour Password lenght should be minimum 8 and maximum 24."
				,"Information", JOptionPane.INFORMATION_MESSAGE);
		frame.setVisible(true);
	}
	
	/**
	 * Creates the structure of the LoginPage. Creates information fields for the user using superclass' textPanel() method, selecTier() method, textButtonPanel() method.
	 */
	public void structure() {

		mainPanel.setPreferredSize(new Dimension(500,450));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setOpaque(false);
		
		mainPanel.add(textPanel("Name: "));
		name = getTextField();
		mainPanel.add(textPanel("Surname: "));
		surname = getTextField();
		mainPanel.add(textPanel("Age: "));
		age = getTextField();
		mainPanel.add(textPanel("Email: "));
		email = getTextField();
		mainPanel.add(textPanel("Username: "));
		username = getTextField();
		mainPanel.add(textPanel("Password: "));
		password = getTextField();
		mainPanel.add(textPanel("-Optional- About Me: "));
		aboutMe = getTextField();
		selectTier();
		photoButton.addActionListener(this);
		mainPanel.add(textButtonPanel("-Optional- Profile Photo (upload .png)", photoButton));
		signupButton.addActionListener(this);
		mainPanel.add(signupButton);
		backButton.addActionListener(this);
		mainPanel.add(backButton);
		
		frame.add(mainPanel);
	}
	
	/**
	 * Creates three Radio Button's for each tier.
	 */
	public void selectTier() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Select a tier: ");
		
		panel.setPreferredSize(new Dimension(500,30));
		panel.setLayout(new FlowLayout());
		panel.setOpaque(false);
		
		label.setFont(new Font("Monaco", Font.BOLD, 15));
		label.setForeground(new Color(70,70,200));
		panel.add(label);
		
		ButtonGroup group = new ButtonGroup();
		group.add(free);
		group.add(hobbyist);
		group.add(profesional);
		
		panel.add(free);
		panel.add(hobbyist);
		panel.add(profesional);
		
		mainPanel.add(panel);
	}
	
	/**
	 * When pressed to photo Button, asks to select a profile photo using JFileChooser.
	 * When pressed to signup Button, it validates information using validation object from SignupValidation class.
	 * If all tests are passed, writes the new User's information to "users.txt" file, adds the user to the userlist.
	 * Then sends the user to his/her PrivateProfilePage.
	 * When pressed to back Button, it sends the user back to LoginPage.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == photoButton) {
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				path = fileChooser.getSelectedFile().getAbsolutePath();
			}
		}
		else if (e.getSource() == signupButton) {
			if (validation.validation(name.getText(), surname.getText(), age.getText(), email.getText(), username.getText(), password.getText())) {
				JOptionPane.showMessageDialog(null, "You have successfully created an account!","Congratulations", JOptionPane.INFORMATION_MESSAGE);
				if (free.isSelected()) {
					tier = free.getText();
				}
				else if (hobbyist.isSelected()) {
					tier = hobbyist.getText();
				}
				else if (profesional.isSelected()) {
					tier = profesional.getText();
				}
				String fileName = "users.txt";
				boolean fileExists = new File(fileName).exists();
				try{
					Formatter output = new Formatter(new FileOutputStream(fileName, true));
					if (fileExists == false) {
						File file = new File(fileName);
						file.createNewFile();
					}
					output.format("%s %s|%s|%s|%s|%s|%s|%s|%s%n", 
							name.getText(), surname.getText(), age.getText(), email.getText(), username.getText(), password.getText(), path, aboutMe.getText(), tier);
					output.close();
				} catch(SecurityException | FormatterClosedException | IOException ex) {
					ex.printStackTrace();
				}
				userInfo = new String[]{name.getText() + " " + surname.getText(), age.getText(), email.getText(), username.getText(), password.getText(), path, aboutMe.getText(), tier};
				user = new User(userInfo);
				userList.add(user);				
				new PrivateProfilePage(username.getText(), userList);
				frame.dispose();
				BaseLogger.info().log("New User created. Username <"+username.getText()+">.");
			}
			else {
				JOptionPane.showMessageDialog(null, validation.getTextMessage(),"Invalid Input", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if (e.getSource() == backButton) {
			new LoginPage();
			frame.dispose();
		}
	}
}