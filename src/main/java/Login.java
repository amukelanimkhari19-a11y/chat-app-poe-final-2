import java.util.regex.Pattern;

/**
 * Login.java
 * Handles registration and login validation for PROG POE Part 1
 * (Registration and login feature).
 *
 * Reference (regex-based validation approach):
 * Oracle. n.d. Class Pattern. Available at:
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 * [Accessed 27 September 2026].
 */
public class Login {

    // South African international cell format: "+27" followed by up to 10 digits.
    private static final String CELL_REGEX = "^\\+27\\d{1,10}$";

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Saved once registration succeeds, used to verify a later login attempt.
    private String storedUsername;
    private String storedPassword;
    private boolean registered = false;

    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    /** Username must contain an underscore and be no more than five characters long. */
    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    /** Password must be 8+ characters, with a capital letter, a number, and a special character. */
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        return hasUpper && hasDigit && hasSpecial;
    }

    /** Cell number must contain the SA international code (+27) and be correctly formatted. */
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber != null && Pattern.matches(CELL_REGEX, cellPhoneNumber);
    }

    /** Registers the user if both the username and password are valid. */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, and a "
                    + "special character.";
        }
        storedUsername = username;
        storedPassword = password;
        registered = true;
        return "Username successfully captured. Password successfully captured. "
                + "User registered successfully.";
    }

    /** Handles the separate cell-number check/message required by the brief. */
    public String registerCellPhoneNumber() {
        return checkCellPhoneNumber()
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.";
    }

    /** Verifies entered credentials against the stored (registered) ones. */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return registered
                && storedUsername.equals(enteredUsername)
                && storedPassword.equals(enteredPassword);
    }

    /** Returns the correct message for a login attempt's outcome. */
    public String returnLoginStatus(boolean loginSuccessful) {
        return loginSuccessful
                ? "Welcome " + firstName + ", " + lastName + " it is great to see you."
                : "Username or password incorrect, please try again.";
    }
}
