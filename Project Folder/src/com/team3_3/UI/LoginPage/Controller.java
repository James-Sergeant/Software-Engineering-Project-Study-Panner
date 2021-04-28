package com.team3_3.UI.LoginPage;

import com.team3_3.Planner.User.Login;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    //Following controls Login.fxml

    public Button loginButton;
    public Button forgottenButton;
    public Label invalidLabel;
    public Button createAccountButton;
    public TextField usernameField;
    public PasswordField passwordField;

    public void loginAction(ActionEvent actionEvent) throws Exception {
        //extracting user-entered information from the login screen
        String email = usernameField.getText();
        String password = passwordField.getText();

        //checking user's login details
        //linker error - java.lang.NullPointerException: Cannot invoke "java.util.HashMap.containsKey(Object)" because "com.team3_3.Planner.User.Login.USER_PASSWORD_MAP" is null
        if(Login.logIn(email, password)){
            Main.changeMainScene(actionEvent, "Dashbaord.fxml");
        }
        else{
            invalidLabel.setVisible(true);
        }

    }

    public void forgottenAction(ActionEvent actionEvent) {
        /**
         * Method has to generate pseudo-random password
         * Send email to user containing new password
         * Then, once successful, change user's old password to new generated password.
         */
        System.out.println("insert: forgottenAction");
    }

    public void createAccountAction(ActionEvent actionEvent) throws IOException {
        usernameField.clear(); passwordField.clear();
        Main.changeMainScene(actionEvent, "CreateAccount.fxml");
    }

}
