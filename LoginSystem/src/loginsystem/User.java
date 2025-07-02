
package loginsystem;
import javax.swing.JOptionPane;

public class User {
    
     private String username; // User's username
    private String password; // User's password
    private String cellPhoneNumber; // User's cell phone number

    // Constructor to initialize user details
    public User(String username, String password, String cellPhoneNumber) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Getter for cell phone number
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
}
