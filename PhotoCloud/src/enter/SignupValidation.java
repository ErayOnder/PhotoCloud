package enter;

import java.util.ArrayList;

import discover.*;
import log.BaseLogger;

public class SignupValidation {
	
	private String textMessage;
	private ArrayList<User> userList = EnterancePage.createUserList();
	
	/**
	 * Validates the users information.
	 * @param name -> the name of the target user that will be verified.
	 * @param surname -> the surname of the target user that will be verified.
	 * @param age -> the age of the target user that will be verified.
	 * @param email -> the email of the target user that will be verified.
	 * @param username -> the username of the target user that will be verified.
	 * @param password -> the password of the target user that will be verified.
	 * @return -> If all validation tests are passed, returns true.
	 */
	public boolean validation(String name, String surname, String age, String email, String username,String password) {
		Boolean valid = true;
		if (nameValidation(name) == false) {
			setTextMessage("Name cannot contain special characters.");
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid name entered.");
		}
		if (nameValidation(surname) == false) {
			setTextMessage("Surname cannot contains special characters.");
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid surname entered.");
		}
		if (nameValidation(username) == false) {
			if (textMessage == null) {
				setTextMessage("Username cannot contains special characters.");
			}
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid username entered.");
		}
		if (ageValidation(age) == false) {
			setTextMessage("Sorry, you are too young to use social media. Read books first.");
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid age entered.");
		}
		if (emailValidation(email) == false) {
			if (textMessage == null) {
				setTextMessage("Entered email does not correspond with required email.");
			}
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid email entered.");
		}
		if (passwordValidation(password) == false) {
			setTextMessage("Entered password lenght does not correspond with required password lenght.");
			valid = false;
			BaseLogger.error().log("Invalid User Input: Invalid password entered.");
		}
		return valid;
	}
	
	/**
	 * Validation method for name, surname, username.
	 * It returns false if the text contains any special characters.
	 * @param text -> the text that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean nameValidation(String text) {
		Boolean valid = true;
		String specialCharacters = "ığüşöç";
		String lowerText = text.toLowerCase();
		for (char ch: specialCharacters.toCharArray()) {
			if (lowerText.contains(Character.toString(ch))) {
				valid = false;
				break;
			}
		}
		for (User user: userList) {
			if (user.getUsername().equals(text)) {
				setTextMessage("Username already exist. Please pick another.");
				valid = false;
			}
		}
		return valid;
	}
	
	/**
	 * Validation for age.
	 * It returns false if age is not a integer or if it is less than 12.
	 * @param text -> the text that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean ageValidation(String text) {
		Boolean valid = true;
		try {
			int number = Integer.parseInt(text);
			if (number < 12) {
				valid = false;
			}
		} catch(NumberFormatException e) {
			System.out.println("Invalid input. The age entered is not a number.");
			valid = false;
		}
		return valid;
	}
	
	/**
	 * Validation for email.
	 * It returns false if the email doesn't end with "@ku.edu.tr"
	 * @param text -> the text that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean emailValidation(String text) {
		Boolean valid = true;
		try{
			if (text.substring(text.length()-10).equals("@ku.edu.tr") == false) {
				valid = false;
			}
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds.");
		}
		for (User user: userList) {
			if (user.getEmail().equals(text)) {
				setTextMessage("Email already exist. Please pick another.");
				valid = false;
			}
		}
		return valid;
	}
	
	/**
	 * Validation for password.
	 * It returns false if the password length isn't between 8 and 24.
	 * @param text -> the text that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean passwordValidation(String text) {
		Boolean valid = true;
		if ((text.length()<8) || (text.length()>24)) {
			valid = false;
		}
		return valid;
	}

	/**
	 * Getters and Setters of required instances.
	 */
	public String getTextMessage() {return textMessage;}
	public void setTextMessage(String textMessage) {this.textMessage = textMessage;}
}