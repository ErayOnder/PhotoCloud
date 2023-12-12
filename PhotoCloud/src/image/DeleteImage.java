package image;
import discover.*;
import log.BaseLogger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import profile.PrivateProfilePage;


public class DeleteImage implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton button;
	JRadioButton image;
	ArrayList<JRadioButton> buttonList = new ArrayList<>();
	
	User user;
	ArrayList<User> userList;

	/**
	 * The superclass of ModifyImage Class.
	 * Creates a new frame that lets the user select existing images that he/she wants to delete.
	 * @param user -> the user who will do the deleting.
	 * @param userList -> the ArrayList of users.
	 */
	public DeleteImage(User user, ArrayList<User> userList) {
		this.userList = userList;
		this.user = user;
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(100,300));
		frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		frame.getContentPane().setBackground(new Color(125,225,255));
		choice();
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Iterates through all the posts of the user and creates a Radio Button for each of them.
	 */
	public void choice() {
		for (Post post : user.getPostList()) {
			image = new JRadioButton(post.getPostName());
			frame.add(image);
			buttonList.add(image);
		}
		setButtonName("Delete");
		button.addActionListener(this);
		frame.add(button);
	}
	
	/**
	 * Sets the buttons name.
	 * @param name -> the name that the button will have.
	 */
	public void setButtonName(String name) {button = new JButton(name);}

	/**
	 * When Button is pressed, the selected Radio Button's corresponded post will be deleted by calling user objects deletePost() method.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			for (JRadioButton radioButton: buttonList) {
				if (radioButton.isSelected()) {
					user.deletePost(radioButton.getText());
					new PrivateProfilePage(user.getUsername(), userList);
					frame.dispose();
					BaseLogger.info().log("<"+user.getUsername()+"> deleted Post. Post Name: <"+radioButton.getText()+">.");
				}
			}
		}	
	}
}