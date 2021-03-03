package com.team3_3.User;

import com.team3_3.utils.ObjectIO;
import com.google.common.hash.Hashing;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <h1>Login Class</h1>
 *<p>This a static class is used handle the user creation and logging in, only one user is loaded into memory at anytime and this
 * only happens when that user has logged in.</p>
 * @author  James Sergeant
 * @version 1.0
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - 01/03/2021: Defined the basic methods for the class - JS
 *   - 01/03/2021: Implemented hashed and salted password. - JS
 */
public abstract class Login {
    private static  HashMap<String, String> USER_PASSWORD_MAP;
    private static User loggedInUser = null;
    private static final String PASSWORD_FILE_LOCATION = "dat/UserPasswords.ser";

    /**
     * Saves the current users and passwords to a file.
     * @return Boolean: True if this is successful.
     */
    public static boolean saveUserPassword(){
        return ObjectIO.saveObject(PASSWORD_FILE_LOCATION, USER_PASSWORD_MAP);
    }

    /**
     * Loads in all of the usernames and passwords
     * @return Boolean: True if succeeded.
     */
    public static boolean loadUserPassword(){
        if(!(new File(PASSWORD_FILE_LOCATION).exists())){
            USER_PASSWORD_MAP = new HashMap<>();
            return saveUserPassword();
        }
        USER_PASSWORD_MAP = ObjectIO.readObject(PASSWORD_FILE_LOCATION);
        return true;
    }

    /**
     * Attempts to log the user in with a username and password.
     * @param email String:  The users email
     * @param password String: The users password.
     * @return Boolean: True if the user was successfully logged in.
     */
    public static boolean logIn(String email, String password) throws User.UserNotFoundException {
        if(isUser(email) && checkPassword(email,password)){
            loggedInUser = User.loadUser(email);
            loggedInUser.toggleLoggedIn();
            return true;
        }
        return false;
    }

    /**
     * Saves the user data and logs out the currently logged in user.
     */
    public static void logOut(){
        User.saveUser(loggedInUser);
        loggedInUser.toggleLoggedIn();
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
        com.team3_3.User.User user = new com.team3_3.User.User(firstname,surname,email);
        User.saveUser(user);
        addUserPassword(email,passwordHashAndSalt(email,password));
    }

    /**
     * Used to check if the users password machetes the stored one.
     * @param email String: users email address.
     * @param password String: users password.
     * @return Boolean: True if the users password matches.
     */
    private static boolean checkPassword(String email, String password){
        final String SALT = hashSHA512(email);
        final String PASSWORD_SALT = password + SALT;
        final String PASSWORD_HASH = hashSHA512(PASSWORD_SALT);
        if(PASSWORD_HASH.equals(USER_PASSWORD_MAP.get(email))){
            return true;
        }
        return false;
    }

    /**
     * Generates the password's hash + salt, the salt is derived from the users emailaddress.
     * @param email String: the users email.
     * @param password Password: the users password.
     * @return String: the hash and salted password.
     */
    private static String passwordHashAndSalt(String email, String password){
        final String SALT = hashSHA512(email);
        final String PASSWORD_SALT = password + SALT;
        return hashSHA512(PASSWORD_SALT);
    }

    /**
     * Utility method that used the SHA512 hashing algorithm.
     * @param string String: the value to be hashed.
     * @return String: The hashed value.
     */
    private static String hashSHA512(String string){
        return Hashing.sha512().hashString(string, StandardCharsets.UTF_8).toString();
    }

    /**
     * Insures the email meets the standard.
     * @link www.emailregex.com
     * @param email the users email address.
     * @throws InvalidEmailAddressException thrown if the condations are not met.
     */
    private static void checkEmail(String email) throws InvalidEmailAddressException{
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
    private static void checkPassword(String password) throws InvalidPasswordException {
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
        return USER_PASSWORD_MAP.containsKey(email);
    }

    /**
     * Checks if the user exists using the users object.
     * @param user com.team3_3.User.com.team3_3.User: the user object.
     * @return Boolean: True if the user exists.
     */
    public static boolean isUser(com.team3_3.User.User user){
        return USER_PASSWORD_MAP.containsValue(user);
    }

    /**
     * @return com.team3_3.User:  The current logged in user.
     */
    public static com.team3_3.User.User getLoggedInUser() {
        return loggedInUser;
    }
    private static void addUserPassword(String email, String passwordHash){
        USER_PASSWORD_MAP.put(email,passwordHash);
    }

    public static void main(String[] args) {
        final String email = "afa17aeu@uea.ac.uk";
        final String password = "TestIng123456#\u2560";

        System.out.println("Create a new user: ");

        //Loads in users
        System.out.println("Test Loading Passwords");
        Login.loadUserPassword();

        try {
            newUser("James","Sergeant",email,password);
        } catch (InvalidEmailAddressException e) {
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (UserExistsException e) {
            e.printStackTrace();
        }

        System.out.println("Test Login: ");
        try {
            System.out.println("Login: "+logIn(email,password));
        } catch (User.UserNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(loggedInUser);

        logOut();

        System.out.println("Test wrong password: ");
        try {
            System.out.println("Login: "+logIn(email,"password"));
        } catch (User.UserNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Test saving Passwords");
        System.out.println(saveUserPassword());

    }
}
