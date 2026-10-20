import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Login validLogin;

    @BeforeEach
    void setUp() {
        validLogin = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
    }

    // ----- Username -----
    @Test
    void testUserNameCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    void testUserNameIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    // ----- Password -----
    @Test
    void testPasswordMeetsComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testPasswordDoesNotMeetComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    // ----- Cell phone number -----
    @Test
    void testCellPhoneNumberCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    void testCellPhoneNumberIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    // ----- Login -----
    @Test
    void testLoginSuccessful() {
        validLogin.registerUser();
        assertTrue(validLogin.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void testLoginFailed() {
        validLogin.registerUser();
        assertFalse(validLogin.loginUser("kyl_1", "wrongPassword"));
    }

    // ----- registerUser() messages -----
    @Test
    void testRegisterUserUsernameIncorrect() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
            "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.",
            login.registerUser());
    }

    @Test
    void testRegisterUserPasswordIncorrect() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals(
            "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, and a "
                + "special character.",
            login.registerUser());
    }
}
