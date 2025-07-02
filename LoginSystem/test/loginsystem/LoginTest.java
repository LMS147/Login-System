package loginsystem;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testCheckUserName_ValidFormat() {
        Login login = new Login();
        assertTrue(login.checkUserName("abc_1"));
    }

    @Test
    public void testCheckUserName_InvalidFormat() {
        Login login = new Login();
        assertFalse(login.checkUserName("abcdef"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Pass@1234"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27831234567"));
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0831234567"));
    }

    @Test
    public void testRegisterUser_Success() {
        Login login = new Login();
        String result = login.registerUser("abc_1", "Pass@1234", "+27831234567");
        assertEquals("Username and password successfully captured.", result);
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        Login login = new Login();
        String result = login.registerUser("abcdef", "Pass@1234", "+27831234567");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testRegisterUser_InvalidPassword() {
        Login login = new Login();
        String result = login.registerUser("abc_1", "password", "+27831234567");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testRegisterUser_InvalidPhone() {
        Login login = new Login();
        String result = login.registerUser("abc_1", "Pass@1234", "12345");
        assertEquals("Cell phone number incorrectly formatted or does not contain the South African international code.", result);
    }

    @Test
    public void testReturnLoginStatus_Success() {
        Login login = new Login();
        login.registerUser("abc_1", "Pass@1234", "+27831234567");
        boolean result = login.returnLoginStatus("abc_1", "Pass@1234");
        assertEquals("Welcome abc_1, it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        Login login = new Login();
        login.registerUser("abc_1", "Pass@1234", "+27831234567");
        boolean result = login.returnLoginStatus("abc_1", "WrongPass!");
        assertEquals("Username or password incorrect, please try again.", result);
    }

    @Test
    public void testReturnLoginStatus_NullInputs() {
        Login login = new Login();
        login.registerUser("abc_1", "Pass@1234", "+27831234567");
        boolean result = login.returnLoginStatus(null, null);
        assertEquals("Username or password incorrect, please try again.", result);
    }

    @Test
    public void testCheckUserName_EmptyString() {
        Login login = new Login();
        assertFalse(login.checkUserName(""));
    }
}
