package com.team3_3.User;

import java.io.Serializable;

//import static com.team3_3.User.Login.*;


public class User implements Serializable {
    public final transient int SSN = 1;
    private String firstname;
    private String surname;
    private String email;
    private String passwordHash;
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
        System.out.println(Login.getUser("afa19aeu@uea.ac.uk"));
    }
}
