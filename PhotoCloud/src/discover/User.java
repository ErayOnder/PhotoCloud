package discover;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
	private String name;
	private String age;
	private String email;
	private String username;
	private String password;
	private String profilePhoto; 
	private String aboutMe;
	private String tier;
	
	private ArrayList<Post> postList = new ArrayList<>();

	/**
	 * Creates a new user using information that is read from the file.
	 * @param personInfo -> an Array that contains all necessaru user information.
	 */
	public User(String[] personInfo) {
		this.name = personInfo[0];
		this.age = personInfo[1];
		this.email = personInfo[2];
		this.username = personInfo[3];
		this.password = personInfo[4];
		this.profilePhoto = personInfo[5];
		this.aboutMe = personInfo[6];
		this.tier = personInfo[7];		
	}
		
	/**
	 * Adds a post to user's post ArrayList.
	 * @param post -> the post that will be added.
	 */
	public void addPost(Post post) {
		postList.add(post);
	}
	
	/**
	 * Changes the existing post in the post ArrayList with a new one.
	 * @param newPost -> the name of the post that will be modified.
	 */
	public void modifyPost(Post newPost) {
		for (int i = 0; i < postList.size(); i++) {
	           Post oldPost = postList.get(i);
	           if (oldPost.getPostID() == newPost.getPostID()) {
	               postList.set(i, newPost); 
	               break;
	           }
	       }
	}
	
	/**
	 * Deletes a post from user's post ArrayList.
	 * @param postName -> the name of the post that will be deleted.
	 */
	public void deletePost(String postName) {
		Iterator<Post> iterator = postList.iterator();
		while(iterator.hasNext()) {
			Post postObject = iterator.next();
			if (postObject.getPostName().equals(postName)) {
				iterator.remove();
			}
		}
	}
	
	/**
	 * Getters and Setters of required instances.
	 */
	public String getName() {return name;}
	public String getAge() {return age;}
	public String getEmail() {return email;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getProfilePhoto() {return profilePhoto;}
	public String getAboutMe() {return aboutMe;}
	public String getTier() {return tier;}
	public ArrayList<Post> getPostList() {return postList;}
	public void setAboutMe(String aboutMe) {this.aboutMe = aboutMe;}
	public void setEmail(String email) {this.email = email;}
	public void setPassword(String password) {this.password = password;}
}