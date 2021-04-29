package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * <h1>Dashboard Controller</h1>
 *<p>This class handles events on the dashboard.</p>
 * @author  Tom Rutherford.
 * @version 1.0
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - 29/04/2021: Added in the addSemester functionality to the method, and the semester file handler method.
 */

public class Controller {

    /////Following controls Dashboard.fxml\\\\\
    public Label invalidFileLabel;

    public void clearDashboardAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Dashboard.fxml");
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

    /**
     * Adds the semester to the user if is valid.
     * @param actionEvent
     */
    public void addSemesterAction(ActionEvent actionEvent) {
        Semester semester;
        //Reset the label:
        invalidFileLabel.setVisible(false);

        //If the semester file is valid, add it to the user:
        if((semester = semesterFileHandler()) != null){
            try {
                Login.getLoggedInUser().addSemester(semester.getSemId(),semester);
            } catch (User.SemesterAlreadyExits semesterAlreadyExits) {
                invalidFileLabel.setText("You already have added this semester.");
                invalidFileLabel.setVisible(true);
            }
        }
        System.out.println(Login.getLoggedInUser().getUSER_SEMESTER_MAP());
        System.out.println("insert: display data.");
    }

    /**
     * Shows the file explore and handles any errors that can occur.
     * @return
     */
    private Semester semesterFileHandler(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select hub file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file =fileChooser.showOpenDialog(Main.mainStage);
        String path = file.getPath();
        try {
            Semester semester = Semester.newSemester(path);
            return semester;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            invalidFileLabel.setText("This is not a valid file");
            invalidFileLabel.setVisible(true);
        } catch (Semester.DateOutOfBoundsException e) {
            invalidFileLabel.setText("The date on this file is invalid");
            invalidFileLabel.setVisible(true);
        }
        return null;
    }

}
