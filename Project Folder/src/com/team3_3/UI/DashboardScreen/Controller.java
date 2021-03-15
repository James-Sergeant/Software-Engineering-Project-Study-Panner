package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.User.Login;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller {

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
        Login.logOut();
        Main.signOut();
    }

    /////Following controls Dashboard.fxml - mySemester\\\\\

    public void addSemesterAction(ActionEvent actionEvent) {
        System.out.println("insert: addSemesterAction");
    }

}
