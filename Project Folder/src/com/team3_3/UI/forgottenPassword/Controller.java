package com.team3_3.UI.forgottenPassword;

import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.utils.Email;
import com.team3_3.Planner.utils.Hash;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import java.io.IOException;

public class Controller {

    public TextField email;
    public Button sendCode;
    public TextField code;
    public TextField passwordField;
    public TextField passwordField1;
    public Button loginButton;
    public Label infoLabel;

    private String sentCode;


    public void sendCode(){
        String emailS = email.getText();
        String hash = Hash.SHA1(emailS + Math.random());
        sentCode = hash.substring(0,16);
        System.out.println(sentCode);
        try {
            Email.sendEmail(emailS, "Change Password Request.","Change code: "+sentCode);
            infoLabel.setText("Code sent!");
            infoLabel.setVisible(true);
        } catch (MessagingException e) {
            infoLabel.setText("Failed to send!");
            infoLabel.setVisible(true);
        }

    }
    public void changePassword(ActionEvent event){
        System.out.println(sentCode);
        System.out.println(code.getText());
        System.out.println(sentCode.equals(code.getText()));
        if(sentCode.equals(code.getText())){
            if(passwordField.getText().equals(passwordField1.getText())){
                try {
                    Login.changePassword(email.getText(),passwordField.getText());
                    sentCode = null;
                    Main.changeMainScene(event,"Login.fxml");
                } catch (Login.InvalidPasswordException w) {
                    infoLabel.setText("Invalid Password!");
                    infoLabel.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            infoLabel.setText("Code's don't match");
            infoLabel.setVisible(true);
        }
    }

    public void back(ActionEvent actionEvent){
        try {
            Main.changeMainScene(actionEvent,"Login.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
