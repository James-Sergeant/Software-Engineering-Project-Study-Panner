package com.team3_3.UI;

//import com.google.common.hash.Hashing;

import com.team3_3.Planner.User.Login;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //keep track of inaccessible stages (Modality.APPLICATION_MODAL event)
    private static Stage mainStage;

    //hold program's associated fxml file names for loading
    private static final String[] pages = {"settings", "mySemester", "myModules", "myTasks"};


    public static void dashboardLoad(ActionEvent actionEvent, String pageName) {
        //getting scene object
        Scene scene = ((Node) actionEvent.getSource()).getScene();

        //clearing all page dividers and screens dynamically
        for (String page : pages){
            Rectangle divider = (Rectangle) scene.lookup("#"+page+"Divider");
            Pane screen = (Pane) scene.lookup("#"+page+"Pane");
            //System.out.println("#"+page+"Pane");
            divider.setVisible(false);
            screen.setVisible(false);
        }

        //making selected page divider and screen visible
        Rectangle divider = (Rectangle) scene.lookup("#"+pageName+"Divider");
        Pane screen = (Pane) scene.lookup("#"+pageName+"Pane");
        divider.setVisible(true);
        screen.setVisible(true);

    }

    public static void signOut() {
        Stage window = new Stage();

        //blocking user interaction with other windows, until pop-up is closed.
        window.initModality(Modality.APPLICATION_MODAL);
        //configuring pop-up window
        window.setTitle("Warning");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Are you sure you want to sign out?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //creating actionEvent for yesButton - close mainStage and open new mainStage (login screen)
        yesButton.setOnAction(actionEvent -> {
            try {
                mainStage.close();
                window.setTitle("My Study Planner");
                changeMainScene(actionEvent, "Login.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //no button closes pop-up and returns to mainStage
        noButton.setOnAction(e -> window.close());

        //VBox used as a container to hold widgets
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        //new scene generated to populate window
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load in users map:
        Login.loadUserPassword();
        //loading in base starting fxml file - login screen
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("My Study Planner");
        //setting dimensions of primaryStage
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void changeMainScene(ActionEvent actionEvent, String fileName) throws IOException {
        //loading new fxml file dynamically
        Parent methodParent = FXMLLoader.load(Main.class.getResource(fileName));
        Scene methodScene = new Scene(methodParent);

        //get stage information from selected widget/node
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //holding current mainStage
        mainStage = window;

        window.setScene(methodScene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
