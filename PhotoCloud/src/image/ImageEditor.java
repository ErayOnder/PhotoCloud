package image;
import discover.*;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ImageEditor implements ActionListener {
	
	JFrame frame;
	JPanel imagePanel = new JPanel();
	JButton addButton = new JButton("Add Image");
	JButton deleteButton = new JButton("Delete Image");
	JButton modifyButton = new JButton("Modify Image");
	JScrollPane scrollPane;
	
	User user;
	User viewingUser;
	String photoPath;
	
	ArrayList<User> userList;
	
	/**
	 * Creates a panel that contains images and if called from PrivateProfilePage, add/delete/modify buttons.
	 * @param user -> the user who owns the images.
	 * @param viewingUser -> the user who views the images (if the user views his/her own images, this object is null).
	 * @param frame -> the profile page frame that the created panel is added eventually.
	 * @param display -> the boolean flag that is true when the class is called from PublicProfilePage, and false when called from PrivateProfilePage.
	 * @param userList -> the ArrayList of users.
	 */
	public ImageEditor(User user, User viewingUser, JFrame frame, boolean display, ArrayList<User> userList) {
		this.userList = userList;
		this.frame = frame;
		this.user = user;
		this.viewingUser = viewingUser;

		imagePanel.setBackground(new Color(75,200,200));
		imagePanel.setPreferredSize(new Dimension(400,2000));
		imagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		scrollPane = new JScrollPane(imagePanel);
		scrollPane.setBounds(350,350,400,375);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		images(display);
		
		frame.getContentPane().add(scrollPane);
	}
	
	/**
	 * Displays the preview images that are in user's post ArrayList. If display is false, sets add/delete/modify buttons visible.
	 * @param display -> the boolean flag that is true when the class is called from PublicProfilePage, and false when called from PrivateProfilePage.
	 */
	private void images(Boolean display) {
		imagePanel.add(addButton);
		addButton.addActionListener(this);
		imagePanel.add(deleteButton);
		deleteButton.addActionListener(this);
		imagePanel.add(modifyButton);
		modifyButton.addActionListener(this);
		
		if (display) {
			addButton.setVisible(false);
			deleteButton.setVisible(false);
			modifyButton.setVisible(false);			
		}	
		else {
			addButton.setVisible(true);
			deleteButton.setVisible(true);
			modifyButton.setVisible(true);		
		}
		
		if (user.getPostList() != null) {
			for (Post post: user.getPostList()) {
				post.preview(imagePanel, viewingUser);
			}
		}
	}

	/**
	 * When pressed to add Button, asks user to select a file and calls AddImage Class.
	 * When pressed to delete Button, calls DeleteImage Class.
	 * When pressed to modifyButton, calls ModifyImage Class.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				photoPath = fileChooser.getSelectedFile().getAbsolutePath();
				new AddImage(user, photoPath, userList);
			}
		}
		else if (e.getSource() == deleteButton) {
			new DeleteImage(user, userList);
		}
		else if (e.getSource() == modifyButton) {
			new ModifyImage(user, userList);
		}
	}	
}