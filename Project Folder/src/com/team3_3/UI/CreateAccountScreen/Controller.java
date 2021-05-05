package com.team3_3.UI.CreateAccountScreen;

import com.team3_3.Planner.User.Login;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class Controller {

    /////Following controls CreateAccount.fxml\\\\\
    public TextField emailField;
    public TextField firstNameField;
    public TextField lastNameField;
    public PasswordField passwordField1;
    public PasswordField passwordField2;
    public Label errorLabel;

    public void signUpAction(ActionEvent actionEvent) {
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        //Checks if the passwords are the same:
        if(password1.equals(password2)){
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
        }else{
            errorLabel.setText("Passwords don't match!");
            errorLabel.setVisible(true);
        }
    }

    public void sendVerificationAction(ActionEvent actionEvent) {
        System.out.println("insert: sendVerificationAction");
    }

    public void loginBackAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Login.fxml");
    }

}
