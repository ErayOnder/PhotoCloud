package profile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import discover.User;
import image.ImageEditor;
import log.BaseLogger;

public class PrivateProfilePage extends PublicProfilePage {
	
	String username;
	
	JFrame editFrame;
	JPanel panel;
	JTextField emailField;
	JTextField passwordField;
	JButton saveChanges;

	/**
	 * A subclass of PublicProfilePage, it inherits the constructor with overwritten methods such as editable(), imageEditVisibility();
	 * @param username -> the corresponding username of the user.
	 * @param userList -> the ArrayList of users.
	 */
	public PrivateProfilePage(String username, ArrayList<User> userList) {
		super(username, userList);
		this.username = username;
	}
	
	/**
	 * It overwrites the editable() method which is in the superclass.
	 * It sets the private user information and edit button's visible.
	 */
	public void editable() {
		super.editable();
		aboutMeBox.setEditable(true);
		emailBox.setVisible(true);
		passwordBox.setVisible(true);
		
		saveButton.setVisible(true);
		saveButton.addActionListener(this);
		
		editButton.setVisible(true);
		editButton.addActionListener(this);
	}
	
	/**
	 * It overwrites the imageEditVisibility() method which is in the superclass.
	 * When called from PrivateProfilePicture, ImageEditor Class is also responsible for adding, removing, modifying.
	 */
	protected void imageEditVisibility() {
		new ImageEditor(user, viewingUser, frame, false, userList);
    }
	
	/**
	 * Creates new frame that contains text fields who wants to edit their email and password.
	 * This method is called when the edit Button is pressed.
	 */
	public void editButton() {
		editFrame = new JFrame();
		editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editFrame.getContentPane().setBackground(new Color(125,225,255));
		
		panel = new JPanel();
		JLabel emailLabel = new JLabel("New Email: ");
		emailField = new JTextField();
		JLabel passwordLabel = new JLabel("New Password: ");
		passwordField = new JTextField();
		saveChanges = new JButton("Save");
		
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(200,175));
		
		emailLabel.setFont(new Font("Monaco", Font.BOLD, 15));
		passwordLabel.setFont(new Font("Monaco", Font.BOLD, 15));
		emailLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		
		emailField.setPreferredSize(new Dimension(150,30));
		passwordField.setPreferredSize(new Dimension(150,30));
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setText(user.getEmail());
		passwordField.setText(user.getPassword());
		
		panel.add(emailLabel);
		panel.add(emailField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		saveChanges.addActionListener(this);
		panel.add(saveChanges);
		
		editFrame.add(panel);
		editFrame.pack();
		editFrame.setVisible(true);
	}
	
	/**
	 * This file changes and replaces the wanted user information in the "users.txt" file.
	 * @param newText -> the new text that is wanted to be the new piece of information.
	 * @param oldTextIndex -> the index number of the users information in the "users.txt" that requires change. 
	 */
	public void changeFile(String newText, int oldTextIndex) {
		String fileName = "users.txt";
		File file = new File(fileName);
		File tempFile = new File("temp_file.txt");
		
		try (Scanner scanner = new Scanner(file)) {
			Formatter output = new Formatter(tempFile);
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] personInfo = line.split("\\|");
				if (personInfo[3].equals(username)) {
					personInfo[oldTextIndex] = newText;
					String newLine = String.join("|", personInfo);
					output.format("%s%n", newLine);	
				}
				else {
					output.format("%s%n", line);
				}
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file.exists()) {
			file.delete();
		}
		if (tempFile.renameTo(file)) {
			System.out.println("File updated.");
		}
		else {
			System.out.println("File couldn't be updated.");
		}		
	}
	
	/**
	 * When pressed to the save Button, changesFile is called to change the "users.txt" with new information about About Me box.
	 * When pressed to edit Button, it calls editButton() method;
	 * When pressed to the save changes Button, changesFile is called to change the "users.txt" with new information about email and password.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == saveButton) {
			changeFile(aboutMeBox.getText(), 6);
			user.setAboutMe(aboutMeBox.getText());
			BaseLogger.info().log("<"+user.getUsername()+"> editted his/her About Me information.");
		}
		else if (e.getSource() == editButton) {
			frame.dispose();
			editButton();
			}
		else if (e.getSource() == saveChanges) {
			changeFile(emailField.getText(), 2);
			changeFile(passwordField.getText(), 4);
			editFrame.dispose();
			user.setEmail(emailField.getText());
			user.setPassword(passwordField.getText());
			new PrivateProfilePage(username, userList);
			BaseLogger.info().log("<"+user.getUsername()+"> editted his/her email/password information.");
		}
	}
}