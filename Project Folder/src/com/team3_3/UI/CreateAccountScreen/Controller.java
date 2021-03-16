package com.team3_3.UI.CreateAccountScreen;

import com.team3_3.UI.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller {

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

}
