package User;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Login {
    public static HashMap<String, User> userHashMap = new HashMap<>();
    public static HashMap<String, String> userPassword = new HashMap<>();
    public static User loggedInUser = null;

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public static boolean logIn(String email, String password){
        if(isUser(email) && checkPassword(email,password)){
            loggedInUser = getUser(email);
            loggedInUser.toggleLoggedIn();
            return true;
        }
        return false;
    }
    public static boolean logOut(User user){
        if(user.isLoggedIn()){
            user.toggleLoggedIn();
            loggedInUser = null;
            return true;
        }
        return false;
    }
    /**
     * Creates a new user; this will also insure the user dose not already exist and the email is valid.
     * @param firstname the users first name.
     * @param surname the user surname.
     * @param email the users email address, this has to be unique.
     * @param password this is the users password. It must be at least 8 characters long, including one special charter.
     * @throws UserExistsException is thrown if the user already exists in the system.
     * @throws InvalidEmailAddressException Is thrown if the supplied email dose not match the standard for emails.
     * @link www.emailregex.com
     * @throws InvalidPasswordException The password dose not meet the requirements: Minimum eight characters, at least one letter, one number and one special character
     */
    public static void newUser(String firstname,String surname, String email,String password) throws InvalidEmailAddressException, InvalidPasswordException, UserExistsException {
        userExists(email);
        checkEmail(email);
        checkPassword(password);
        User user = new User(firstname,surname,email);
        addUserHashMap(user);
        addUserPassword(email,passwordHashAndSalt(email,password));
    }
    private static boolean checkPassword(String email, String password){
        final String SALT = hashSHA512(email);
        final String PASSWORD_SALT = password + SALT;
        final String PASSWORD_HASH = hashSHA512(PASSWORD_SALT);
        if(PASSWORD_HASH.equals(userPassword.get(email))){
            return true;
        }
        return false;
    }
    private static String passwordHashAndSalt(String email, String password){
        final String SALT = hashSHA512(email);
        final String PASSWORD_SALT = password + SALT;
        return hashSHA512(PASSWORD_SALT);
    }
    private static String hashSHA512(String string){
        return Hashing.sha512().hashString(string, StandardCharsets.UTF_8).toString();
    }

    /**
     * Insures the email meets the standard.
     * @link www.emailregex.com
     * @param email the users email address.
     * @throws InvalidEmailAddressException thrown if the condations are not met.
     */
    public static void checkEmail(String email) throws InvalidEmailAddressException{
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        if(!matchFound) {
            throw new InvalidEmailAddressException(email);
        }
    }

    /**
     * Insures the password meets the requirements: Minimum eight characters, at least one letter, one number and one special character
     * @param password the users password.
     * @throws InvalidPasswordException thrown if these conditions are not met.
     */
    public static void checkPassword(String password) throws InvalidPasswordException {
        //Minimum eight characters, at least one letter, one number and one special character
        //^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\S+$).{8,}$
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$",Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidPasswordException();
        }
    }

    /**
     * Ensures the user dose not already exist.
     * @param email the users email address.
     * @throws UserExistsException thrown if the users email is already registered.
     */
    public static void userExists(String email) throws UserExistsException{
        if(isUser(email)){throw new UserExistsException(email);}
    }

    /**
     * Implements the password exception.
     */
    static class InvalidPasswordException extends Exception{
        InvalidPasswordException(){super("Passwords must contain: Minimum eight characters, at least one letter, one number and one special character:");}
    }

    /**
     * Implements the email exception.
     */
    static class InvalidEmailAddressException extends Exception{
        InvalidEmailAddressException(String email){
            super("Not a valid email address: "+email);
        }
    }

    /**
     * Implements the user exists exception.
     */
    static class UserExistsException extends Exception{
        UserExistsException(String email){
            super(email+"Is already associated with a user");
        }
    }

    /**
     * Checks if the user exists using the email.
     * @param email String: users email address
     * @return Boolean: returns true if the user exists.
     */
    public static boolean isUser(String email){
        return userHashMap.containsKey(email);
    }

    /**
     * Checks if the user exists using the users object.
     * @param user User.User: the user object.
     * @return Boolean: True if the user exists.
     */
    public static boolean isUser(User user){
        return userHashMap.containsValue(user);
    }

    /**
     * adds the user to the map of all users.
     * @param user User.User: The user object.
     * @return Boolean: true if the user is added.
     */
    public static boolean addUserHashMap(User user){
        if(isUser(user.getEmail())){return false;}
        userHashMap.put(user.getEmail(),user);
        return true;
    }
    /**
     * A map of all of the users
     * @return HashMap\<String,User.User>: A HashMap of the users with the email as the key.
     */
    public static HashMap<String, User> getUserHashMap() {
        return Login.userHashMap;
    }

    /**
     * @return User:  The current logged in user.
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    private static void addUserPassword(String email, String passwordHash){
        userPassword.put(email,passwordHash);
    }

    public static User getUser(String email){
        return userHashMap.get(email);
    }

    public static void main(String[] args) {
        final String email = "afa19aeu@uea.ac.uk";
        final String password = "TestIng123456#\u2560";
        System.out.println("Create a new user: ");

        try {
            newUser("James","Sergenat",email,password);
        } catch (InvalidEmailAddressException e) {
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
        System.out.println(getUser(email));

        System.out.println("Test Login: ");
        System.out.println("Login: "+logIn(email,password));
        System.out.println(loggedInUser);
        logOut(loggedInUser);

        System.out.println("Test wrong password: ");
        System.out.println("Login: "+logIn(email,"password"));

    }
}
