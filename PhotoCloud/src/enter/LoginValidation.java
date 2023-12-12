package enter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import log.BaseLogger;

public class LoginValidation {
	
	HashMap<String, String> usernamePassword = new HashMap<>();
	private String textMessage;

	/**
	 * Validates the users existence and correctness of the password.
	 * @param fileName -> for getting file which contains user information. 
	 * @param username -> the username of the target user that will be verified.
	 * @param password -> the password of the target user that will be verified.
	 * @return -> If all validation tests are passed, returns true.
	 */
	public boolean validation(String fileName ,String username, String password) {
		txtToMap(fileName);
		Boolean valid = true;
		if (checkExistence(username) == false) {
			valid = false;
			setTextMessage("There is no account that corresponds to this username. Please sign up or try again.");
			BaseLogger.error().log("Invalid User Input: Account doesn't exist.");
		}
		else if (checkCorrectness(username, password) == false) {
			valid = false;
			setTextMessage("Password wrong. Please try again.");
			BaseLogger.error().log("<"+username+"> entered wrong password.");
		}
		return valid;
	}
	
	/**
	 * Checks whether the entered username exists.
	 * @param username -> the username of the target user that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean checkExistence(String username) {
		Boolean found = false;
		for (String uN: usernamePassword.keySet()) {
			if (uN.equals(username)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	/**
	 * Checks whether the entered password corresponds with the entered username.
	 * @param username -> the username of the target user that will be verified.
	 * @param password -> the password of the target user that will be verified.
	 * @return -> If test is passed, returns true.
	 */
	public boolean checkCorrectness(String username, String password) {
		Boolean correct = false;
		for (String uN: usernamePassword.keySet()) {
			if (uN.equals(username)) {
				if (password.equals(usernamePassword.get(uN))) {
					correct = true;
					break;
				}
			}
		}
		return correct;
	}

	/**
	 * Reads the user file and creates a HashMap which has username as keys and password as values.
	 * @param fileName -> the file that contains user information.
	 */
	public void txtToMap(String fileName) {
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] personInfo = line.split("\\|");
				usernamePassword.put(personInfo[3], personInfo[4]);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getters and Setters of required instances.
	 */
	public String getTextMessage() {return textMessage;}
	public void setTextMessage(String textMessage) {this.textMessage = textMessage;}
}