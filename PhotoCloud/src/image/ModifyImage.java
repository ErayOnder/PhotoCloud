package image;
import discover.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;


public class ModifyImage extends DeleteImage {
	
	JButton modifyButton = new JButton("Modify!");
	AddImage addImagePage;
	ImageIcon icon;
	String description;
	int postID;
	
	/**
	 * A subclass of DeleteImage Class so that this class can inherit the DeleteImage class's frame structure.
	 * Creates a new frame that lets the user select existing images that he/she wants to modify.
	 * @param user -> the user who will do the deleting.
	 * @param userList -> the ArrayList of users.
	 */
	public ModifyImage(User user, ArrayList<User> userList) {
		super(user, userList);
	}
	
	/**
	 * Overwrites the choice method that is called in the constructor.
	 * Iterates through all the posts of the user and creates a Radio Button for each of them and make these buttons a Button Group.
	 * Sets the button name to "Modify".
	 */
	public void choice() {
		ButtonGroup group = new ButtonGroup();
		for (Post post : user.getPostList()) {
			image = new JRadioButton(post.getPostName());
			group.add(image);
			frame.add(image);
			buttonList.add(image);
		}
		setButtonName("Modify");
		button.addActionListener(this);
		frame.add(button);	
	}
	
	/**
	 * Finds the post that will be modified by iterating through user's post ArrayList.
	 * Calls AddImage Class to open a frame that will let the user modify the post.
	 * @param postName -> the post's name which will be modified.
	 * @param user -> the user who owns the post.
	 */
	public void modifyPage(String postName, User user) {
		for (Post post: user.getPostList()) {
			if (post.getPostName().equals(postName)) {
				icon = post.getImage();
				description = post.getPostDescription();
				postID = post.getPostID();
				break;
			}
		}
		new AddImage(user, icon, postName, description, postID, userList);
	}
	
	/**
	 * When Button is pressed, the selected Radio Button's corresponded post will be modified by calling modifyPage() method.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			for (JRadioButton radioButton: buttonList) {
				if (radioButton.isSelected()) {
					modifyPage(radioButton.getText(), user);
					frame.dispose();
				}
			}
		}
	}
}