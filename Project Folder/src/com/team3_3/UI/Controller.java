package com.team3_3.UI;

import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;

import com.team3_3.Planner.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;

import java.io.IOException;
import java.util.EventObject;


public class Controller {

    //Following controls Login.fxml

    public Button loginButton;
    public Button forgottenButton;
    public Label invalidLabel;
    public Button createAccountButton;
    public TextField usernameField;
    public PasswordField passwordField;

    public void loginAction(ActionEvent actionEvent) throws Exception {

        Main.changeMainScene(actionEvent, "Dashbaord.fxml");

        /* following code needs to be implemented for use, null pointer exception issue

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

         */
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

    /////Following controls CreateAccount.fxml\\\\\

    public void signUpAction(ActionEvent actionEvent) {
        System.out.println("insert: signUpAction");
    }

    public void sendVerificationAction(ActionEvent actionEvent) {
        System.out.println("insert: sendVerificationAction");
    }

    public void loginBackAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Login.fxml");
    }

    /////Following controls Dashboard.fxml\\\\\

    public void clearDashboardAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Dashbaord.fxml");
    }

    public void myAccountAction(ActionEvent actionEvent) {
        System.out.println("insert: myAccountAction");
    }

    public void settingsAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "settings");
    }

    public void mySemesterAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "mySemester");
    }

    public void myModulesAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "myModules");
    }

    public void myTasksAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "myTasks");
    }


    public void accountSettingsAction(ActionEvent actionEvent) {
        System.out.println("insert: accountSettingsAction");
    }

    public void signOutAction() {
        Main.signOut();
    }
}
