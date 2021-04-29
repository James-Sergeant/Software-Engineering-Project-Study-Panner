package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.User.Login;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Controller {

    /////Following controls Dashboard.fxml\\\\\
    public Label invalidFileLabel;

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
        Login.logOut();
        Main.signOut();
    }

    /////Following controls Dashboard.fxml - mySemester\\\\\

    public void addSemesterAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select hub file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file =fileChooser.showOpenDialog(Main.mainStage);
        String path = file.getPath();
        try {
            Semester semester = Semester.newSemester(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            invalidFileLabel.setText("This is not a valid file");
            invalidFileLabel.setVisible(true);
        } catch (Semester.DateOutOfBoundsException e) {
            invalidFileLabel.setText("The date on this file is invalid");
            invalidFileLabel.setVisible(true);
        }
        System.out.println("insert: addSemesterAction");
    }

}
