/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own
without any help from anyone else. The effort in the project thus belongs
completely to me. I did not search for a solution, or I did not consult any
program written by others or did not copy any program from other sources. I
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Eray Önder, 79253>
*************************************************************************/

package enter;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Main {
	/**
	 * Creates "users.txt" file if not created already.
	 * This file will contain information of every user.
	 * Calls LoginPage Class to start the PhotoCloud application.
	 */
	public static void main(String[] args) {
				
        String fileName = "users.txt";
        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
        
		new LoginPage();
	}
}