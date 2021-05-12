package com.team3_3.UI.CreateAccountScreen;

import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.utils.Email;
import com.team3_3.Planner.utils.Hash;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import java.io.IOException;


public class Controller {

    /////Following controls CreateAccount.fxml\\\\\
    public TextField emailField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField verificationField;
    public PasswordField passwordField1;
    public PasswordField passwordField2;
    public Label errorLabel;

    private String varCode;

    public void signUpAction(ActionEvent actionEvent) {
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        if (varCode.equals(verificationField.getText())) {
            //Checks if the passwords are the same:
            if (password1.equals(password2)) {
                try {
                    Login.newUser(firstName, lastName, email, password1);
                    Main.changeMainScene(actionEvent, "Login.fxml");
                } catch (Login.InvalidEmailAddressException e) {
                    errorLabel.setText("Email is invalid");
                    errorLabel.setVisible(true);
                } catch (Login.InvalidPasswordException e) {
                    errorLabel.setText("This is not a valid password");
                    errorLabel.setVisible(true);
                } catch (Login.UserExistsException e) {
                    errorLabel.setText("You already have an account please login!");
                    errorLabel.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Passwords don't match!");
                errorLabel.setVisible(true);
            }
        }else{
            errorLabel.setText("Invalid Code!");
            errorLabel.setVisible(true);
        }
    }

    public void sendVerificationAction(ActionEvent actionEvent) {
        String email = emailField.getText();
        try {
            Login.checkEmail(email);
            String hash = Hash.SHA1(email + Math.random());
            varCode = hash.substring(0,16);
            try {
                errorLabel.setText("Sending code...");
                errorLabel.setVisible(true);
                Email.sendEmail(email,"My Study Planner Signup", "Code: "+varCode);
                errorLabel.setText("Code sent!");
                errorLabel.setVisible(true);
            } catch (MessagingException e) {
                errorLabel.setText("Unable to send code!");
                errorLabel.setVisible(true);
            }
        } catch (Login.InvalidEmailAddressException e) {
            errorLabel.setText("Email is invalid");
            errorLabel.setVisible(true);
        }
    }

    public void loginBackAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Login.fxml");
    }

}
