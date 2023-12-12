package profile;
import discover.*;
import enter.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import image.ImageEditor;
import log.BaseLogger;

public class PublicProfilePage  implements ActionListener {
	
	JFrame frame = new JFrame();
	JPanel bibPanel = new JPanel();
	
	JTextArea aboutMeBox = new JTextArea(10,20);
	JButton saveButton = new JButton("Save");
	JLabel emailBox = new JLabel();
	JLabel passwordBox = new JLabel();
	JButton editButton = new JButton("Edit");
	JButton discoverPageButton = new JButton("Go to Discover Page");
	JButton signoutButton = new JButton("Sign Out");

	File file = new File("users.txt");
	User user;
	User viewingUser;

	ArrayList<User> userList;
	
	/**
	 * The superclass of PrivateProfilePage.
	 * Creates the main components of the profile page. The subclass PrivateProfilePage overrides some methods to add edit buttons to users own profile page.
	 * This constructor is called by the subclass PrivateProfilePage whenever the user wants to enter to his/her own profile page.
	 * @param username -> the corresponding username of the user.
	 * @param userList -> the ArrayList of users.
	 */
	public PublicProfilePage(String username, ArrayList<User> userList) {
		this.userList = userList;
		this.user = findUser(username);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 750);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(125,225,255));
		title();
		
		bibPanel.setBounds(0, 350, 250, 375);
		bibPanel.setBackground(new Color(75,200,200));
		bibPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		biography();
		editable();
		
		frame.add(bibPanel);
		imageEditVisibility();
		
		frame.setVisible(true);
	}
	
	/**
	 * Creates the main components of the profile page.
	 * This constructor is called whenever a user wants to enter another user's profile page.
	 * @param viewingUser -> the corresponding User object of the user that wants to view another user's profile page.
	 * @param username -> the corresponding username of the user that owns the profile page.
	 * @param userList -> the ArrayList of users.
	 */
	public PublicProfilePage(User viewingUser, String username, ArrayList<User> userList) {
		this.userList = userList;
		this.user = findUser(username);
		this.viewingUser = viewingUser;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 750);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(125,225,255));
		title();
		
		bibPanel.setBounds(0, 350, 250, 375);
		bibPanel.setBackground(new Color(75,200,200));
		bibPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		biography();
		editable();
		
		frame.add(bibPanel);
		imageEditVisibility();
		
		frame.setVisible(true);
	}

	/**
	 * Creates the title segment of the profile page.
	 * This segment contains Button that leads to Discover Page, Button that leads to signing out, The profile picture and the username of the corresponding user.
	 */
	public void title() {
		JLabel photoUsername = new JLabel();
		ImageIcon image = new ImageIcon(user.getProfilePhoto());
		
		photoUsername.setText(user.getUsername());
		photoUsername.setFont(new Font("Luminari", Font.BOLD, 30));
		photoUsername.setForeground(new Color(50,80,225));
		photoUsername.setIcon(resizeImage(image,200, 200));
		photoUsername.setHorizontalTextPosition(JLabel.CENTER);
		photoUsername.setVerticalTextPosition(JLabel.BOTTOM);
		photoUsername.setBounds(275, 50, 300, 300);
		
		discoverPageButton.setBounds(0, 0, 150, 50);
		discoverPageButton.addActionListener(this);
		
		signoutButton.setBounds(600, 0, 150, 50);
		signoutButton.addActionListener(this);
		
		frame.add(signoutButton);
		frame.add(discoverPageButton);
		frame.add(photoUsername);
		
	}
	
	/**
	 * Creates the corresponding user's biography panel.
	 * This panel contains uneditable user information such as name and age.
	 */
	public void biography() {
		JLabel nameBox = new JLabel();
		JLabel ageBox = new JLabel();

		nameBox.setText("Name Surname: " + user.getName());
		ageBox.setText("Age: " + user.getAge() + " ");
		nameBox.setFont(new Font("Monaco", Font.ITALIC, 15));
		ageBox.setFont(new Font("Monaco", Font.ITALIC, 15));
		nameBox.setForeground(Color.white);
		ageBox.setForeground(Color.white);

		bibPanel.add(nameBox);
		bibPanel.add(ageBox);
	}
	
	/**
	 * Creates the corresponding user's biography panel.
	 * This panel contains editable user information such as about me text, email, password.
	 * The information and edit buttons that aren't shared with other users are set non-visible.
	 * This method is overwritten in PrivateProfilePage.
	 */
	protected void editable() {
		emailBox.setText("Email: " + user.getEmail());
		passwordBox.setText("Password: " + user.getPassword());
		emailBox.setFont(new Font("Monaco", Font.ITALIC, 15));
		passwordBox.setFont(new Font("Monaco", Font.ITALIC, 15));
		emailBox.setForeground(Color.white);
		passwordBox.setForeground(Color.white);
		
		aboutMeBox.setText(user.getAboutMe());
		aboutMeBox.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		aboutMeBox.setForeground(Color.white);
		aboutMeBox.setBackground(new Color(125,200,200));
		
		bibPanel.add(aboutMeBox);
		bibPanel.add(saveButton);
		bibPanel.add(emailBox);
		bibPanel.add(passwordBox);
		bibPanel.add(editButton);
		
		aboutMeBox.setEditable(false);
		saveButton.setVisible(false);
		editButton.setVisible(false);
		emailBox.setVisible(false);
		passwordBox.setVisible(false);
	}
	
	/**
	 * This method is called in the constructor.
	 * When called from PublicProfilePage, ImageEditor Class is responsible only for displaying pictures.
	 * This method is overwritten in PrivateProfilePage.
	 */
	protected void imageEditVisibility() {
		new ImageEditor(user, viewingUser, frame, true, userList);
    }

	/**
	 * Finds the user corresponding to the given username.
	 * @param username -> the username of the wanted user.
	 * @return -> the corresponding User object.
	 */
	public User findUser(String username) {
		for (User u: userList) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Resizes the image.
	 * @param icon -> image that is going to be resized.
	 * @param newWidth -> image's new width.
	 * @param newHeight -> image's new height.
	 * @return -> the resized image.
	 */
	public ImageIcon resizeImage(ImageIcon icon, int newWidth, int newHeight) {
		Image originalImage = icon.getImage();
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
	}
	
	/**
	 * When pressed to discover page Button, it sends the user to the Discover Page.
	 * When pressed to signout Button, it logs out.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == discoverPageButton) {
			if (viewingUser != null) {
				new DiscoverPage(viewingUser, userList);
				frame.dispose();
				BaseLogger.info().log("<"+viewingUser.getUsername()+"> returned to PhotoCloud Discover Page.");
			}
			else {
				new DiscoverPage(user, userList);
				frame.dispose();
				BaseLogger.info().log("<"+user.getUsername()+"> went to PhotoCloud Discover Page.");
			}
		}
		if (e.getSource() == signoutButton) {
			new LoginPage();
			frame.dispose();
			if (viewingUser != null) {
				BaseLogger.info().log("<"+viewingUser.getUsername()+"> logged out.");
			}
			else {
				BaseLogger.info().log("<"+user.getUsername()+"> logged out.");
			}
		}
	}
}