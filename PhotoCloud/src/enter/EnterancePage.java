package enter;
import discover.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

import discover.User;

public abstract class EnterancePage implements ActionListener {
	
	protected JFrame frame = new JFrame();
	private JTextField textField;
	protected static ArrayList<User> userList = createUserList();
	private static int i=0;
	
	/**
	 * An abstract class that is a superclass of LoginPage and SignupPage.
	 * Creates the main frame in the constructor.
	 */
	public EnterancePage() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 550);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().setBackground(new Color(100,180,255));
		i++;
		addPostToExistingUser();
		
	}
	
	/**
	 * Creates the title of the page.
	 */
	public void title() {
		
		JLabel title = new JLabel("Welcome to PhotoCloud!");
		
		title.setFont(new Font("Luminari", Font.BOLD, 30));
		title.setForeground(new Color(75,75,255));
		
		frame.add(title);
		
	}
	
	/**
	 * Creates a panel that contains a Label and a corresponding Text Field.
	 * @param text -> determines the Label that is next to the Text Field.
	 * @return -> the created Panel.
	 */
	public JPanel textPanel(String text) {
		
		JPanel textPanel = new JPanel();
		JLabel label = new JLabel(text);
		JTextField field = new JTextField();
		
		textPanel.setPreferredSize(new Dimension(500,30));
		textPanel.setLayout(new FlowLayout());
		textPanel.setOpaque(false);
		
		label.setFont(new Font("Monaco", Font.BOLD, 15));
		label.setForeground(new Color(70,70,200));
		textPanel.add(label);
		
		field.setPreferredSize(new Dimension(150,30));
		field.setFont(new Font("Monaco", Font.PLAIN, 15));
		field.setForeground(Color.white);
		field.setBackground(new Color(75,150,255));
		setTextField(field);
		textPanel.add(field);
		
		return textPanel;
	}
	
	/**
	 * Creates a Button.
	 * @param text1 -> determines the name of the Button.
	 * @return -> the created Button.
	 */
	public JButton button(String text1) {
		
		JButton button = new JButton(text1);
		button.setFocusable(false);
		button.setFont(new Font("Monaco", Font.BOLD, 15));
		button.setForeground(new Color(75,75,255));
		
		return button;
	}

	/**
	 * Creates a panel that contains a Label and a Button.
	 * @param text -> determines the Label.
	 * @param button -> determines the Button.
	 * @return -> the created Panel.
	 */
	public JPanel textButtonPanel(String text, JButton button) {
		
		JPanel panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(500,20));
		panel.setLayout(new FlowLayout());
		panel.setOpaque(false);
		
		JLabel label = new JLabel(text);
		label.setFont(new Font("Monaco", Font.ITALIC, 10));
		label.setForeground(new Color(75,75,255));
		
		panel.add(label);
		panel.add(button);
		
		return panel;
	}

	/**
	 * Reads the "users.txt" file and creates an ArrayList of User objects.
	 * This ArrayList will be passed on throughout the classes since it contains unchanging information.
	 * @return -> the created ArrayList.
	 */
	public static ArrayList<User> createUserList() {
		ArrayList<User> list = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("users.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] personInfo = line.split("\\|");
				list.add(new User(personInfo));
			}
			return list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * When the application starts, non of the existing users have posts in their accounts. They have to add/modify/remove posts while the application is running.
	 * This method adds specific posts to specific users, so that the application starts with existing posts.
	 */
	public static void addPostToExistingUser() {
		if (i==1) {
			for (User user: userList) {
				if (user.getUsername().equals("admin")) {
					ImageIcon img1 = new ImageIcon("Administrator1.jpeg");
					HashMap<String, Integer> fM1 = new HashMap<>();
					user.addPost(new Post(user, img1, "Sunset at Rotterdam", "A beautiful view...", fM1, false));
					ImageIcon img12 = new ImageIcon("Admin2.jpeg");
					HashMap<String, Integer> fM12 = new HashMap<>();
					user.addPost(new Post(user, img12, "Puddles", "I love dogs, they are so cute:)", fM12, false));
				}
				if (user.getUsername().equals("erayonder1")) {
					ImageIcon img2 = new ImageIcon("ErayOnder1.jpeg");
					HashMap<String, Integer> fM2 = new HashMap<>();
					fM2.put("Grayscale", 90);
					user.addPost(new Post(user, img2, "Ayi Pub", "I am home:)", fM2, false));
				}
				if (user.getUsername().equals("burki")) {
					ImageIcon img3 = new ImageIcon("BurakKahvecioglu1.jpeg");
					HashMap<String, Integer> fM3 = new HashMap<>();
					fM3.put("Brightness", 40);
					user.addPost(new Post(user, img3, "Rave Face", "Going to a Party", fM3, false));
				}
				if (user.getUsername().equals("goko")) {
					ImageIcon img4 = new ImageIcon("GokmenCetinkaya1.jpeg");
					HashMap<String, Integer> fM4 = new HashMap<>();
					fM4.put("Contrast", 30);
					user.addPost(new Post(user, img4, "Self i", "Hehe", fM4, false));
					ImageIcon img11 = new ImageIcon("GokmenCetinkaya2.jpeg");
					HashMap<String, Integer> fM11 = new HashMap<>();
					user.addPost(new Post(user, img11, "Hehehehe", "I'm sexy and I know it", fM11, false));
				}
				if (user.getUsername().equals("femry")) {
					ImageIcon img5 = new ImageIcon("FarukEmreAygun1.jpeg");
					HashMap<String, Integer> fM5 = new HashMap<>();
					fM5.put("Edge Detection", 12);
					user.addPost(new Post(user, img5, "Yeni Mahalle", "Bullzeyeee", fM5, false));
				}
				if (user.getUsername().equals("silatahtaci")) {
					ImageIcon img6 = new ImageIcon("SilaTahtaci1.jpeg");
					HashMap<String, Integer> fM6 = new HashMap<>();
					fM6.put("Blur", 15);
					user.addPost(new Post(user, img6, "Driving", "hiiiiii", fM6, false));
				}
				if (user.getUsername().equals("berky")) {
					ImageIcon img7 = new ImageIcon("BerkeKaramanli1.jpeg");
					HashMap<String, Integer> fM7 = new HashMap<>();
					fM7.put("Sharpen", 5);
					user.addPost(new Post(user, img7, "Me and the boys", "I think we are drunk.", fM7, false));
				}
				if (user.getUsername().equals("ozgurozmen")) {
					ImageIcon img8 = new ImageIcon("OzgurOzmen1.jpeg");
					HashMap<String, Integer> fM8 = new HashMap<>();
					user.addPost(new Post(user, img8, "Astranout in the Sun", "He is way up high:)", fM8, false));
				}
				if (user.getUsername().equals("berkbora61")) {
					ImageIcon img9 = new ImageIcon("BoraCagilci1.jpeg");
					HashMap<String, Integer> fM9 = new HashMap<>();
					user.addPost(new Post(user, img9, "Bruh", "My best photo.", fM9, false));
				}
				if (user.getUsername().equals("ranayetkin")) {
					ImageIcon img10 = new ImageIcon("RanaYetkin1.jpeg");
					HashMap<String, Integer> fM10 = new HashMap<>();
					user.addPost(new Post(user, img10, "My two babies", "I love themm:)", fM10, false));
				}
			}
		}
	}
	
	/**
	 * Getters and Setters of required instances.
	 */
	public JTextField getTextField() {return textField;}
	public ArrayList<User> getUserList() {return userList;}
	public void setTextField(JTextField textField) {this.textField = textField;}
}