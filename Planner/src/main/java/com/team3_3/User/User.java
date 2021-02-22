package com.team3_3.User;

import com.google.common.hash.Hashing;
import com.team3_3.utils.ObjectIO;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;

public class User implements Serializable {
    public final transient int SSN = 1;
    public static final String USERS_DIR = "dat/userData";
    private String firstname;
    private String surname;
    private String email;
    private boolean loggedIn;


    /**
     * Private constructor for a new user.
     * @param firstname the users first name.
     * @param surname the user surname.
     * @param email the users email address, this has to be unique.
     * @throws Login.UserExistsException is thrown if the user already exists in the system.
     * @throws Login.InvalidEmailAddressException Is thrown if the supplied email dose not match the standard for emails.
     * @link www.emailregex.com
     * @throws Login.InvalidPasswordException The password dose not meet the requirements: Minimum eight characters, at least one letter, one number and one special character
     */
    protected User(String firstname,String surname, String email){
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.loggedIn = false;
    }

    /**
     * A formatted string for the location of the user objects.
     * @param email String: The users email address
     * @return String: the location of the users file.
     */
    private static String userLocation(String email){
        return USERS_DIR +"/"+getUserHash(email);
    }

    /**
     * Gets the hash of the users email, used to identify the users file.
     * @param email String: the users email address.
     * @return String: a 8 char hax string of the crc32 hash of the users email.
     */
    public static String getUserHash(String email){
        return Hashing.crc32().hashString(email, Charset.defaultCharset()).toString();
    }

    /**
     * Checks if the user exists from email address.
     * @param email String: the users email address.
     * @return boolean: True if the user exists.
     */
    public static boolean userExists(String email){
        File test = new File(userLocation(email));
        return test.exists();
    }

    /**
     * Saves the users file.
     * @param user User: the user object to be saved.
     * @return Boolean: True if the user was saved.
     */
    public static boolean saveUser(User user){
        return ObjectIO.saveObject(userLocation(user.getEmail()),user);
    }

    /**
     * Loads in the user object from the file using the users email address.
     * @param email String: the user email.
     * @return User: the user object.
     * @throws UserNotFoundException If there is no user file this will be thrown.
     */
    public static User loadUser(String email) throws UserNotFoundException {
        if(!userExists(email)){throw new UserNotFoundException(email);}
        return ObjectIO.readObject(userLocation(email));
    }

    /**
     * An exception for the case where no user is associated with a file lookup.
     */
    static class UserNotFoundException extends Exception{
        UserNotFoundException(String email){
            super("User: "+email+" was not found!");
        }
    }

    /**
     * Toggles the users login status.
     */
    public void toggleLoggedIn(){
        loggedIn ^= true;
    }

    /**
     * Checks the if the user is logged in.
     * @return Boolean: true if the user is logged in.
     */
    public boolean isLoggedIn(){
        return loggedIn;
    }

    /**
     * Gets the users first name.
     * @return String: firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * gets the users surname
     * @return String: surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the users email address
     * @return String: email
     */
    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "com.team3_3.User{" +
                "SSN=" + SSN +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }

    /**
     * Tests for the class
     * @param args String[]: Command line arguments.
     */
    public static void main(String[] args) {
        try {
            Login.newUser("James", "Sergeant","afa19aeu@uea.ac.uk","Password123456789!");
        } catch (Login.InvalidEmailAddressException e) {
            e.printStackTrace();
        } catch (Login.InvalidPasswordException e) {
            e.printStackTrace();
        } catch (Login.UserExistsException e) {
            e.printStackTrace();
        }
        System.out.println(Login.getLoggedInUser());
    }
}
