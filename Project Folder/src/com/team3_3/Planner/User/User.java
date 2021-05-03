package com.team3_3.Planner.User;


import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.utils.Hash;
import com.team3_3.Planner.utils.ObjectIO;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * <h1>User Class</h1>
 *<p>This class is used to store a user and its associated data. The User class and all its composed classes are
 * serializable so that the user and its data can be saved.</p>
 * @author  James Sergeant
 * @version 1.3
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - 01/03/2021: Defined the basic user object and methods that comprise it - JS
 *   - 01/03/2021: Allowed the User to be saved so that it can be stored. - JS
 *   - 03/03/2021: Allowed the User to removed. - JS
 *   - 29/04/2021: Added in a hash map to store the user's semester's, and the get/set functions associated with it.
 */
public class User implements Serializable {
    public final transient int SSN = 1;
    public static final String USERS_DIR = "dat/userData";
    private String firstname;
    private String surname;
    private String email;
    private boolean loggedIn;
    private final HashMap<String, Semester> USER_SEMESTER_MAP;
    private Semester currentSemester;


    /**
     * Protected constructor as creation of new users need special behaviour, this is handled by the Login class.
     * @param firstname the users first name.
     * @param surname the user surname.
     * @param email the users email address, this has to be unique.
     * @throws .Login.UserExistsException is thrown if the user already exists in the system.
     * @throws .Login.InvalidEmailAddressException Is thrown if the supplied email dose not match the standard for emails.
     * @link www.emailregex.com
     * @throws .Login.InvalidPasswordException The password dose not meet the requirements: Minimum eight characters, at least one letter, one number and one special character
     */
    protected User(String firstname,String surname, String email){
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.loggedIn = false;
        this.USER_SEMESTER_MAP = new HashMap<>();
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
        return Hash.SHA1(email);
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
     * Used to remove a users file from the file system.
     * @param email String: The users email address.
     * @return Boolean: true if the user was removed.
     */
    public static boolean deleteUser(String email){
        File userFile = new File(userLocation(email));
        if(userFile.delete()){
            return true;
        }return false;
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
     * Adds the given semester to the user, and saves the users data.
     * @param semID: The semester id.
     * @param semester: The semester object.
     * @throws SemesterAlreadyExits: Thrown if the user already has this semester.
     */
    public void addSemester(String semID, Semester semester) throws SemesterAlreadyExits {
        if(USER_SEMESTER_MAP.containsKey(semID)){
            throw new SemesterAlreadyExits(semID);
        } else{
            USER_SEMESTER_MAP.put(semID,semester);
            currentSemester = semester;
            saveUser(this);
        }
    }

    /**
     * Removes a semester from the user and saves the users data.
     * @param semID: The ID for the semester.
     */
    public void removeSemester(String semID){
        USER_SEMESTER_MAP.remove(semID);
        saveUser(this);
    }

    /**
     * @return The user's semester hash map.
     */
    public HashMap<String, Semester> getUSER_SEMESTER_MAP(){
        return USER_SEMESTER_MAP;
    }

    /**
     * An exception for the case where a user has already added a semester.
     */
    public static class SemesterAlreadyExits extends Exception{
        public SemesterAlreadyExits(String semID){
            super("Semester ID: "+semID+" already added to this user.");
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

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
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
        } catch (Login.InvalidEmailAddressException | Login.InvalidPasswordException | Login.UserExistsException e) {
            e.printStackTrace();
        }
        System.out.println(Login.getLoggedInUser());
    }
}
