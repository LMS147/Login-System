package loginsystem;

import javax.swing.JOptionPane;

public class Login {

    private User registeredUser; // Stores the registered user details

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        return hasUppercase && hasDigit && hasSpecialChar;
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null || cellPhoneNumber.length() != 12) {
            return false;
        }

        if (!cellPhoneNumber.startsWith("+27")) {
            return false;
        }

        for (int i = 3; i < cellPhoneNumber.length(); i++) {
            if (!Character.isDigit(cellPhoneNumber.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain the South African international code.";
        }

        registeredUser = new User(username, password, cellPhoneNumber);
        return "Username and password successfully captured.";
    }

    public boolean returnLoginStatus(String username, String password) {
        if (registeredUser != null && username != null && password != null &&
                registeredUser.getUsername().equals(username) &&
                registeredUser.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null,"Welcome " + username + ", it is great to see you again.");
            return true ;
        } else {
            JOptionPane.showMessageDialog(null,"Username or password incorrect, please try again.");
            return false;
        }
    }
}
