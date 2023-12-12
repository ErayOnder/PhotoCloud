package discover;

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
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import filter.Operation;
import log.BaseLogger;

public class Post implements ActionListener {
		
	private User user;
	private User viewingUser;
	private int postID;
	private ImageIcon image;
	private String postName;
	private String postDescription;
	private HashMap<String, Integer> filterMap;
	private Boolean priv;
	private int likes;
	private int dislikes;
	private HashMap<String, String> commentMap;
	
	private static int counter = 0;
	
	JFrame overviewFrame;
	JFrame commentFrame;
	
	JScrollPane scrollPane;
	JPanel mainCommentPanel;
	JPanel commentPanel;
	JLabel commentLabel;
	JTextField existingComment;
	JTextField newComment;

	JButton detailButton = new JButton();
	JRadioButton likeButton;
	JRadioButton dislikeButton;
	JRadioButton commentButton;
	JButton saveComment;
	
	Boolean blurApplied;
	Boolean sharpenApplied;
	Boolean grayscaleApplied;
	Boolean edgeDetectionApplied;
	Boolean brightnessApplied;
	Boolean contrastApplied;
	
	/**
	 * Creates a new Post object.
	 * @param user -> the user who owns the post.
	 * @param image -> the image.
	 * @param postName -> the name of the post.
	 * @param postDescription -> the description of the post.
	 * @param filterMap -> the HashMap which has the selected filter names as keys and their levels as values.
	 * @param priv -> the boolean which is true if the post is private and false if it is public.
	 */
	public Post(User user, ImageIcon image, String postName, String postDescription, HashMap<String, Integer> filterMap, Boolean priv) {
		this.user = user;
		counter ++;
		this.postID = counter;
		this.image = image;
		this.postName = postName;
		this.postDescription = postDescription;
		this.filterMap = filterMap;
		this.priv = priv;
		
		this.likes = 0;
		this.dislikes = 0;
		this.commentMap = new HashMap<>();
		
		this.blurApplied = false;
		this.sharpenApplied = false;
		this.grayscaleApplied = false;
		this.edgeDetectionApplied = false;
		this.brightnessApplied = false;
		this.contrastApplied = false;
	}
	
	/**
	 * Creates a new object with known ID. It is called when a post is modified. The id of the modified post stays same.
	 * @param user -> the user who owns the post.
	 * @param postID -> the ID of the post.
	 * @param image -> the image.
	 * @param postName -> the name of the post.
	 * @param postDescription -> the description of the post.
	 * @param filterMap -> the HashMap which has the selected filter names as keys and their levels as values.
	 * @param priv -> the boolean which is true if the post is private and false if it is public.
	 */
	public Post(User user, int postID, ImageIcon image, String postName, String postDescription, HashMap<String, Integer> filterMap, Boolean priv) {
		this.user = user;
		this.postID = postID;
		this.image = image;
		this.postName = postName;
		this.postDescription = postDescription;
		this.filterMap = filterMap;
		this.priv = priv;
		
		this.likes = 0;
		this.dislikes = 0;
		this.commentMap = new HashMap<>();
		
		this.blurApplied = false;
		this.sharpenApplied = false;
		this.grayscaleApplied = false;
		this.edgeDetectionApplied = false;
		this.brightnessApplied = false;
		this.contrastApplied = false;
	}
	
	/**
	 * If there is a viewing user and the post is private, it doesn't do anything. But otherwise, it creates a panel.
	 * This panel contains the post image, the name of the post and see details Button.
	 * @param panel -> the frame that the created panel will be added to.
	 * @param viewingUser -> the viewer user of the created panel.
	 */
	public void preview(JPanel panel, User viewingUser) {
		this.viewingUser = viewingUser;
		if ((viewingUser != null)&&(priv ==true)){}
		else {
			JPanel previewPanel = new JPanel();
			JLabel label = new JLabel();
			JButton button = new JButton("See Details");
			
			previewPanel.setPreferredSize(new Dimension(400,200));
			previewPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			previewPanel.setOpaque(false);
			
			setFilter();
			label.setIcon(resizeImage(image, 200,200));
			label.setText(postName);
			label.setFont(new Font("Luminari", Font.ITALIC, 15));
			label.setForeground(Color.white);
			label.setVerticalTextPosition(JLabel.CENTER);
			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setPreferredSize(new Dimension(275,200));

			previewPanel.add(label);
			button.addActionListener(this);
			previewPanel.add(button);
			previewPanel.setVisible(true);
			setButton(button);
			
			panel.add(previewPanel);
		}
	}
	
	/**
	 * Creates a new frame that has the post's bigger picture, number of likes and dislikes, comments, the username of the owner of the post and his/her tier.
	 */
	public void overview() {
		overviewFrame = new JFrame();
		overviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		overviewFrame.getContentPane().setBackground(new Color(125,225,255));
		overviewFrame.setSize(new Dimension(700,600));
		
		JPanel overviewPanel = new JPanel();
		JLabel label0 = new JLabel();
		ImageIcon profilePhoto = new ImageIcon(user.getProfilePhoto());
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		likeButton = new JRadioButton();
		ImageIcon likeImage = new ImageIcon("likeButton.png");
		dislikeButton = new JRadioButton();
		ImageIcon dislikeImage = new ImageIcon("dislikeButton.png");
		commentButton = new JRadioButton();
		ImageIcon commentImage = new ImageIcon("commentButton.png");
		

		overviewPanel.setPreferredSize(new Dimension(700,600));
		overviewPanel.setLayout(null);
		overviewPanel.setOpaque(false);
		
		label0.setIcon(resizeImage(profilePhoto, 50, 50));
		label0.setText(user.getUsername() + ": (" + user.getTier() +")");
		label0.setIconTextGap(25);
		label0.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		label0.setForeground(Color.white);
		label0.setBounds(0, 0, 450, 100);

		setFilter();
		label1.setIcon(resizeImage(image, 400, 400));
		label1.setText(postName);
		label1.setFont(new Font("Luminari", Font.ITALIC, 25));
		label1.setForeground(Color.white);
		label1.setVerticalTextPosition(JLabel.BOTTOM);
		label1.setHorizontalTextPosition(JLabel.CENTER);
		label1.setBounds(0, 100, 450, 450);
		
		label2.setText("<html>Photo Description: <br>" + postDescription + "</html>");
		label2.setFont(new Font("Monaco", Font.PLAIN, 15));
		label2.setForeground(Color.white);
		label2.setBounds(450, 100, 250, 100);
		
		likeButton.setText(" Likes: " + Integer.toString(likes));
		likeButton.setFont(new Font("Monaco", Font.PLAIN, 20));
		likeButton.setForeground(Color.white);
		likeButton.setFocusable(false);
		likeButton.setIcon(resizeImage(likeImage, 50, 50));
		likeButton.setBounds(450, 200, 250, 100);
		
		dislikeButton.setText(" Dislikes: " + Integer.toString(dislikes));
		dislikeButton.setFont(new Font("Monaco", Font.PLAIN, 20));
		dislikeButton.setForeground(Color.white);
		dislikeButton.setFocusable(false);
		dislikeButton.setIcon(resizeImage(dislikeImage, 50, 50));
		dislikeButton.setBounds(450, 300, 250, 100);

		commentButton.setText("Comments");
		commentButton.setFont(new Font("Monaco", Font.PLAIN, 20));
		commentButton.setForeground(Color.white);
		commentButton.setFocusable(false);
		commentButton.setIcon(resizeImage(commentImage, 65, 65));
		commentButton.setBounds(440, 400, 250, 100);
		
		overviewPanel.add(label0);
		overviewPanel.add(label1);
		overviewPanel.add(label2);
		likeButton.addActionListener(this);
		overviewPanel.add(likeButton);
		dislikeButton.addActionListener(this);
		overviewPanel.add(dislikeButton);
		commentButton.addActionListener(this);
		overviewPanel.add(commentButton);
		
		overviewFrame.add(overviewPanel);
		overviewFrame.setVisible(true);
	}

	/**
	 * Creates a new frame that has a post's comments and whose comment.
	 * Also the frame contains a text field that is used to add a new comment.
	 */
	public void comment() {
		commentFrame = new JFrame();
		commentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		commentFrame.setPreferredSize(new Dimension(250,400));
		
		mainCommentPanel = new JPanel();
		mainCommentPanel.setPreferredSize(new Dimension(250,1000));
		mainCommentPanel.setBackground(new Color(125,225,255));
		mainCommentPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		scrollPane = new JScrollPane(mainCommentPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		if (commentMap != null) {
			for (String userName : commentMap.keySet()) {
				commentPanel = new JPanel();
				commentPanel.setPreferredSize(new Dimension(250,70));
				commentPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
				commentPanel.setOpaque(false);
				
				commentLabel = new JLabel();
				commentLabel.setText(userName);
				commentLabel.setFont(new Font("Monaco", Font.BOLD, 15));
				commentLabel.setForeground(Color.white);
				
				existingComment = new JTextField();
				existingComment.setPreferredSize(new Dimension(200,30));
				existingComment.setText(commentMap.get(userName));
				existingComment.setEnabled(false);
				
				
				commentPanel.add(commentLabel);
				commentPanel.add(existingComment);
				mainCommentPanel.add(commentPanel);
			}
		}
		
		commentPanel = new JPanel();
		commentPanel.setPreferredSize(new Dimension(250,90));
		commentPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		commentPanel.setOpaque(false);
		
		newComment = new JTextField();
		newComment.setPreferredSize(new Dimension(200,40));
		newComment.setEnabled(true);
		
		saveComment = new JButton();
		saveComment.setText("Save Comment");
		saveComment.addActionListener(this);
		
		commentPanel.add(newComment);
		commentPanel.add(saveComment);
		mainCommentPanel.add(commentPanel);
		
		commentFrame.getContentPane().add(scrollPane);
		commentFrame.pack();
		commentFrame.setVisible(true);
	}
	
	/**
	 * Iterates through the filterMap and applies if the filter isn't already applied.
	 */
	public void setFilter() {
		for (String filter: filterMap.keySet()) {
			if (filter.equals("Blur") && (blurApplied==false)) {
				setImage(new ImageIcon(Operation.blur(image, filterMap.get(filter))));
				blurApplied = true;
			}
			if (filter.equals("Sharpen") && (sharpenApplied==false)) {
				setImage(new ImageIcon(Operation.sharpen(image, filterMap.get(filter))));
				sharpenApplied = true;
			}
			if (filter.equals("Grayscale") && (grayscaleApplied==false)) {
				setImage(new ImageIcon(Operation.grayScale(image, filterMap.get(filter))));
				grayscaleApplied = true;
			}
			if (filter.equals("Edge Detection") && (edgeDetectionApplied==false)) {
				setImage(new ImageIcon(Operation.edgeDetection(image, filterMap.get(filter))));
				edgeDetectionApplied = true;
			}
			if (filter.equals("Brightness") && (brightnessApplied==false)) {
				setImage(new ImageIcon(Operation.brightness(image, filterMap.get(filter))));
				brightnessApplied = true;
			}
			if (filter.equals("Contrast") && (contrastApplied==false)) {
				setImage(new ImageIcon(Operation.contrast(image, filterMap.get(filter))));
				contrastApplied = true;
			}
		}
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
	 * When see details Button is pressed, overview method is called.
	 * When like Button is pressed, overview method is called with updated likes.
	 * When dislike Button is pressed, overview method is called with updated dislikes.
	 * When comment Button is pressed, comment method.
	 * When save comment is pressed, it adds the new comment to the comment HashMap along with the user who made the comment. Then calls comment() method with updated comment HashMap.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == detailButton) {
			overview();
		}
		else if (e.getSource() == likeButton) {
			this.likes++;
			overviewFrame.dispose();
			overview();
			BaseLogger.info().log("Post: <"+postID+"> liked.");
		}
		else if (e.getSource() == dislikeButton) {
			this.dislikes++;
			overviewFrame.dispose();
			overview();
			BaseLogger.info().log("Post: <"+postID+"> disliked.");

		}
		else if (e.getSource() == commentButton) {
			comment();
		}
		else if (e.getSource() == saveComment) {
			commentFrame.dispose();
			if (viewingUser != null) {
				commentMap.put(viewingUser.getUsername(), newComment.getText());
				BaseLogger.info().log("<"+viewingUser.getUsername()+"> commented on <"+user.getUsername()+"> Post: <"+postID+">.");
			}
			else {
				commentMap.put(user.getUsername(), newComment.getText());
				BaseLogger.info().log("<"+user.getUsername()+"> commented on <"+user.getUsername()+"> Post: <"+postID+">.");
			}
			comment();
		}
	}

	/**
	 * Getters and Setters of required instances.
	 */
	public void setButton(JButton selectedButton) {this.detailButton = selectedButton;}
	public void setImage(ImageIcon image) {this.image = image;}
	public User getUser() {return user;}
	public String getPostName() {return postName;}
	public ImageIcon getImage() {return image;}
	public String getPostDescription() {return postDescription;}
	public int getPostID() {return postID;}
	public Boolean getPriv() {return priv;}
}