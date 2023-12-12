package discover;
import profile.*;
import enter.*;
import log.BaseLogger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;

public class DiscoverPage implements ActionListener {
	
	JFrame mainFrame;
	JPanel mainPanel;
	JScrollPane scrollPane;
	
	JLabel title;
	JLabel information;
	JButton signoutButton;
	JRadioButton usernameButton;
	JTextField searchUser;
	JButton searchButton;
	JPanel postPanel;
	
	
	ImageIcon profilePhoto;
	
	JButton deleteImageButton;
	
	JFrame deleteFrame;
	JRadioButton imageToDelete;
	JButton deleteFinal;
	HashMap<JRadioButton, Post> buttonPostMap = new HashMap<>();
	
	User user;
	ArrayList<User> userList;
	ArrayList<Post> totalPostList;
	ArrayList<JRadioButton> buttonList = new ArrayList<>();

	/**
	 * Creates a new frame that consist of all public posts of all users.
	 * @param user -> the user who logged in.
	 * @param userList -> the ArrayList of users.
	 */
	public DiscoverPage(User user, ArrayList<User> userList) {
		this.user = user;
		this.userList = userList;
		
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,600);
		mainFrame.setLayout(null);
		mainFrame.getContentPane().setBackground(new Color(125,225,255));
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(600,4000));
		mainPanel.setBackground(new Color(125,200,255));
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		scrollPane = new JScrollPane(mainPanel);		
		scrollPane.setBounds(100, 100, 600, 1000);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		if (this.user.getUsername().equals("admin")) {
			title(true);
		}
		else {
			title(false);
		}
		addPost();
				
		mainFrame.getContentPane().add(scrollPane);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Creates the title, information label, signout Button, search user text field and button, and adds them to he main frame.
	 * Also creates a delete button for the Administrator.
	 * @param visible -> the boolean which is only true when Administrator is the logged in user. When this parameter is true, the delete Button is set visible.
	 */
	public void title(boolean visible) {
		
		title = new JLabel("PhotoCloud Discover Page");
		title.setFont(new Font("Luminari", Font.BOLD & Font.ITALIC, 30));
		title.setForeground(Color.white);
		title.setBounds(200, 0, 400, 50);
		
		information = new JLabel("Click on the user to see the profile page");
		information.setFont(new Font("Monaco", Font.BOLD & Font.ITALIC, 12));
		information.setForeground(Color.white);
		information.setBounds(100, 75, 350, 30);
		
		signoutButton = new JButton("Sign out");
		signoutButton.setBounds(700, 0, 100, 50);
		signoutButton.addActionListener(this);
		
		searchUser = new JTextField();
		searchUser.setBounds(650, 65, 150, 35);
		searchUser.setText("Enter Username");
		searchButton = new JButton("Search");
		searchButton.setBounds(700,100,100,30);
		searchButton.addActionListener(this);
		
		deleteImageButton = new JButton("Delete Image");
		deleteImageButton.setBounds(0, 0, 150, 50);
		deleteImageButton.addActionListener(this);
		deleteImageButton.setVisible(visible);
		
		mainFrame.add(title);
		mainFrame.add(information);
		mainFrame.add(signoutButton);
		mainFrame.add(deleteImageButton);
		mainFrame.add(searchUser);
		mainFrame.add(searchButton);
	}
	
	/**
	 * Iterate through all the public posts of all users and adds their preview to a panel which will be added to the main Frame.
	 */
	public void addPost() {
		totalPostList = createPostList();
		Collections.shuffle(totalPostList);
		if (totalPostList != null)	{
			for (Post post: totalPostList) {
				postPanel = new JPanel();
				postPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
				postPanel.setOpaque(false);
				
				profilePhoto = new ImageIcon(post.getUser().getProfilePhoto());
				usernameButton = new JRadioButton();
				usernameButton.setText(post.getUser().getUsername());
				usernameButton.setFont(new Font("Luminari", Font.BOLD, 25));
				usernameButton.setForeground(Color.white);
				usernameButton.setIcon(post.resizeImage(profilePhoto, 30, 30));
				usernameButton.setHorizontalTextPosition(JLabel.RIGHT);
				usernameButton.setVerticalTextPosition(JLabel.CENTER);
				usernameButton.addActionListener(this);
				buttonList.add(usernameButton);
				
				postPanel.add(usernameButton);
				post.preview(postPanel, user);
				mainPanel.add(postPanel);
			}
		}
	}
	
	/**
	 * Creates a new frame that contains Radio Buttons of all posts in the discover page, and a delete Button which deletes the selected posts.
	 */
	public void adminExclusive() {
		deleteFrame = new JFrame();
		deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		deleteFrame.setSize(new Dimension(100,300));
		deleteFrame.setLayout(new FlowLayout(FlowLayout.LEADING));
		deleteFrame.getContentPane().setBackground(new Color(125,225,255));
		
		for (Post post : createPostList()) {
			imageToDelete = new JRadioButton();
			imageToDelete.setText(post.getPostName());
			deleteFrame.add(imageToDelete);
			buttonPostMap.put(imageToDelete, post);
		}
		
		deleteFinal = new JButton("Delete");
		deleteFinal.addActionListener(this);
		deleteFrame.add(deleteFinal);
		
		deleteFrame.pack();
		deleteFrame.setVisible(true);
	}
	
	/**
	 * Creates an ArrayList of all the posts of all the users.
	 * @return -> the created ArrayList.
	 */
	public ArrayList<Post> createPostList() {
		ArrayList<Post> list = new ArrayList<>();
		if (userList != null) {
			for (User user: userList) {
				if (user.getPostList() != null) {
					for (Post post: user.getPostList()) {
						if (post.getPriv()==false) {
							list.add(post);
						}
					}
				}
			}
		}
		return list;	
	}
	
	/**
	 * When deleteImage Button is pressed, it calls adminExclusive() method.
	 * When deleteFinal Button is pressed by the administrator, it deletes the selected posts by calling the owner of the post's deletePost() method.
	 * When signout Button is pressed, the user loggs out.
	 * When search Button is pressed, the user goes to searched user's profile page.
	 * When any of the user's profile page is pressed in the discover page, the logged user goes to selected user's profile page.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteImageButton) {
			adminExclusive();
		}
		if (e.getSource() == deleteFinal) {
			for (JRadioButton rb: buttonPostMap.keySet()) {
				if (rb.isSelected()) {
					buttonPostMap.get(rb).getUser().deletePost(rb.getText());
					new DiscoverPage(user, userList);
					mainFrame.dispose();
					deleteFrame.dispose();
					BaseLogger.info().log("Administrator deleted Post Name: <"+rb.getText()+">.");
				}
			}
			
		}
		if (e.getSource() == signoutButton) {
			new LoginPage();
			mainFrame.dispose();
			BaseLogger.info().log("<"+user.getUsername()+"> logged out.");
		}
		if (e.getSource() == searchButton) {
			Boolean exist = false;
			for (User u: userList) {
				if (u.getUsername().equals(searchUser.getText())) {
					exist = true;
					if (searchUser.getText().equals(user.getUsername())) {
						new PrivateProfilePage(searchUser.getText(), userList);
						mainFrame.dispose();
						BaseLogger.info().log("<"+user.getUsername()+"> went to his/her profile page.");
					}
					else {
						new PublicProfilePage(user, searchUser.getText(), userList);
						mainFrame.dispose();
						BaseLogger.info().log("<"+user.getUsername()+"> went to <"+searchUser.getText()+"> profile page.");
						break;
					}
				}
			}
			if (exist == false) {
				JOptionPane.showMessageDialog(null, "User doesn't exist","Sorry", JOptionPane.WARNING_MESSAGE);
				BaseLogger.error().log("<"+user.getUsername()+"> search non-existing user.");
			}
		}
		for (JRadioButton button: buttonList) {
			if (e.getSource() == button) {
				if (button.getText().equals(user.getUsername())) {
					new PrivateProfilePage(button.getText(), userList);
					mainFrame.dispose();
					BaseLogger.info().log("<"+user.getUsername()+"> went to his/her profile page.");
				}
				else {
					new PublicProfilePage(user, button.getText(), userList);
					mainFrame.dispose();
					BaseLogger.info().log("<"+user.getUsername()+"> went to <"+button.getText()+"> profile page.");
				}
			}
		}
	}
}