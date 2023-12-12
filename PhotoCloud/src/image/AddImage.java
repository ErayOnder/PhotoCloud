package image;
import discover.*;
import log.BaseLogger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import profile.PrivateProfilePage;


public class AddImage implements ActionListener, ChangeListener {
	
	JFrame frame = new JFrame();
	JTextField nameField = new JTextField();
	JTextField descriptionField = new JTextField();
	JRadioButton blur = new JRadioButton("Blur");
	JRadioButton sharpen = new JRadioButton("Sharpen");
	JRadioButton grayscale = new JRadioButton("Grayscale");
	JRadioButton edgeDetection = new JRadioButton("Edge Detection");
	JRadioButton brightness = new JRadioButton("Brightness");
	JRadioButton contrast = new JRadioButton("Contrast");
	JCheckBox priv = new JCheckBox();
	private int level;
	Boolean p;

	JButton addButton;
	JButton modifyButton;

	JFrame sliderFrame;
	JPanel sliderPanel;
	JLabel sliderLabel;
	JSlider slider;
	JButton setButton;

	User user;
	String path;
	ImageIcon icon;
	Post post;
	int postID;
	ArrayList<User> userList;
	HashMap<String, Integer> filterMap = new HashMap<>();
	
	/**
	 * Creates a new frame that lets the user select necessary information about image he/she wants to add.
	 * @param user -> the user who will post the new image.
	 * @param path -> the new image's absolute path.
	 * @param userList -> the ArrayList of users.
	 */
	public AddImage(User user, String path, ArrayList<User> userList) {
		this.userList = userList;
		this.user = user;
		this.path = path;
		icon = new ImageIcon(path);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(125,225,255));
		title(setNameText(null));
		details(setDescriptionText(null), "Add!");
		
		frame.setVisible(true);
	}
	
	/**
	 * Creates a new frame that lets the user select/modify necessary information about image he/she wants to modify.
	 * @param user -> the user who will modify the selected image.
	 * @param icon -> the image that will be modified.
	 * @param postName -> the selected post's current name.
	 * @param postDescription -> the selected post's current description.
	 * @param postID -> the selected post's ID.
	 * @param userList -> the ArrayList of users.
	 */
	public AddImage(User user, ImageIcon icon, String postName, String postDescription, int postID,  ArrayList<User> userList) {
		this.user = user;
		this.icon = icon;
		this.postID = postID;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(125,225,255));
		title(setNameText(postName));
		details(setDescriptionText(postDescription), "Modify!");
		
		frame.setVisible(true);
		this.userList = userList;
	}
	
	/**
	 * Creates a panel that contains the selected image and a text field that contains images name.
	 * @param nameFieldText -> the name of the image (null if image is newly add, !null if image is modified).
	 */
	public void title(String nameFieldText) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Name:");
		
		panel.setBounds(25, 0, 200, 400);
		panel.setLayout(new FlowLayout());
		panel.setOpaque(false);
		
		label.setIcon(resizeImage(icon,200,200));
		label.setFont(new Font("Monaco", Font.BOLD, 15));
		label.setForeground(Color.white);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setIconTextGap(10);
		
		nameField.setPreferredSize(new Dimension(200,30));
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameField.setText(nameFieldText);
		
		panel.add(label);
		panel.add(this.nameField);
		frame.add(panel);
	}

	/**
	 * Creates a panel that contains the image's description, Radio Buttons of filters, a CheckBox indicating whether the post will be private or not.
	 * @param descriptionFieldText -> the description of the image (null if image is newly add, !null if image is modified).
	 * @param buttonName -> the name of the button ("Add" if image is newly add, "Modify" if image is modified).
	 */
	private void details(String descriptionFieldText, String buttonName) {
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Description:");
		JLabel label2 = new JLabel("Select a filter:");
		
		panel.setBounds(250, 0, 350, 400);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setOpaque(false);
		
		label1.setFont(new Font("Monaco", Font.BOLD, 15));
		label1.setForeground(Color.white);
		label2.setFont(new Font("Monaco", Font.BOLD, 15));
		label2.setForeground(Color.white);
		
		descriptionField.setPreferredSize(new Dimension(300,100));
		descriptionField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		descriptionField.setText(descriptionFieldText);

		
		panel.add(label1);
		panel.add(descriptionField);
		panel.add(label2);
		
		if (user.getTier().equals("Free")) {
			panel.add(blur);
			blur.addActionListener(this);
			panel.add(sharpen);
			sharpen.addActionListener(this);
		}
		else if (user.getTier().equals("Hobbyist")) {
			panel.add(blur);
			blur.addActionListener(this);
			panel.add(sharpen);
			sharpen.addActionListener(this);
			panel.add(grayscale);
			grayscale.addActionListener(this);
			panel.add(edgeDetection);
			edgeDetection.addActionListener(this);
		}
		else if (user.getTier().equals("Professional")) {
			panel.add(blur);
			blur.addActionListener(this);
			panel.add(sharpen);
			sharpen.addActionListener(this);
			panel.add(grayscale);
			grayscale.addActionListener(this);
			panel.add(edgeDetection);
			edgeDetection.addActionListener(this);
			panel.add(brightness);
			brightness.addActionListener(this);
			panel.add(contrast);
			contrast.addActionListener(this);
		}
		
		priv.setText("Private");
		priv.setFocusable(false);
		panel.add(priv);
		
		if (buttonName.equals("Add!")) {
			addButton = new JButton(buttonName);
			addButton.addActionListener(this);
			panel.add(addButton);
		}
		else if (buttonName.equals("Modify!")) {
			modifyButton = new JButton(buttonName);
			modifyButton.addActionListener(this);
			panel.add(modifyButton);			
		}
		frame.add(panel);
	}
	
	/**
	 * Creates a new frame that contains a Slider and a Button.
	 * This frame appears whenever a filter Radio Button is selected. It is to set the selected filters level.
	 * @param filterName -> the name of the selected filter.
	 * @param init -> the starting point of the filter.
	 * @param end -> the end point of the filter.
	 */
	public void createSlider(String filterName, int init, int end) {
		sliderFrame = new JFrame();
		sliderPanel = new JPanel();
		sliderLabel = new JLabel();
		setButton = new JButton("Set Level");
		slider = new JSlider(init, end, init);
		
		sliderPanel.setBackground(new Color(125,225,255));
		sliderPanel.setPreferredSize(new Dimension(400,200));
		
		slider.setPreferredSize(new Dimension(300,150));
		slider.setPaintTrack(true);
		slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.addChangeListener(this);
		
		setButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==setButton) {
					setLevel(slider.getValue());
					filterMap.put(filterName, level);
					sliderFrame.dispose();
				}
			}
		});
		
		sliderLabel.setText("Level: " + slider.getValue());
		sliderLabel.setFont(new Font("Monaco", Font.PLAIN, 15));
		sliderLabel.setForeground(Color.white);
		
		sliderPanel.add(setButton);
		sliderPanel.add(slider);
		sliderPanel.add(sliderLabel);
		sliderFrame.add(sliderPanel);
		sliderFrame.pack();
		sliderFrame.setVisible(true);
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
	 * Changes the label -which shows the sliders value- as the slider changes.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		sliderLabel.setText("Level: " + slider.getValue());
	}
	
	/**
	 * When any of the filter Radio Buttons are pressed, it calls createsSlider() method to set the level of the filter.
	 * When add Button is pressed, a new Post object is created with the selected information and it is added to user's post ArrayList by calling addPost() method.
	 * When modify Button is pressed, a new Post object is created with an existing ID and it is replaced with the older post that has the same ID by calling modifyPost() method.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == blur) {
			createSlider("Blur",1,100);
		}
		if (e.getSource() == sharpen) {
			createSlider("Sharpen",0,100);
		}
		else if (e.getSource() == grayscale) {
			createSlider("Grayscale",0,100);
		}
		else if (e.getSource() == edgeDetection) {
			createSlider("Edge Detection",0,100);
		}
		else if (e.getSource() == brightness) {
			createSlider("Brightness",0,150);
		}
		else if (e.getSource() == contrast) {
			createSlider("Contrast",0,50);
		}
		if (e.getSource() == addButton) {
			if (priv.isSelected()) {p = true;}
			else {p=false;}
			post = new Post(user, icon, nameField.getText(), descriptionField.getText(), filterMap, p);
			user.addPost(post);
			new PrivateProfilePage(user.getUsername(), userList);
			frame.dispose();
			BaseLogger.info().log("<"+user.getUsername()+"> add new Post. Post ID: <"+post.getPostID()+">.");
		}
		if (e.getSource() == modifyButton) {
			if (priv.isSelected()) {p = true;}
			else {p=false;}
			post = new Post(user, postID, icon, nameField.getText(), descriptionField.getText(), filterMap, p);
			user.modifyPost(post);
			new PrivateProfilePage(user.getUsername(), userList);
			frame.dispose();
			BaseLogger.info().log("<"+user.getUsername()+"> modified Post: <"+post.getPostID()+">.");
		}
	}

	/**
	 * Getters and Setters of required instances.
	 */
	public void setLevel(int level) {this.level = level;}
	private String setNameText(String nameFieldText) {return nameFieldText;}
	private String setDescriptionText(String descriptionFieldText) {return descriptionFieldText;}
}