package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * <h1>Dashboard Controller</h1>
 *<p>This class handles events on the dashboard.</p>
 * @author  Tom Rutherford.
 * @version 1.0
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - 29/04/2021: Added in the addSemester functionality to the method, and the semester file handler method.
 *   - 03/05/2021: Added Settings Page, general UI improvements
 */

public class Controller {

    /////Following controls Dashboard.fxml\\\\\
    public Label invalidFileLabel;
    public ComboBox<Semester> mySemesterSelector;
    public TableView<Module> mySemesterModuleTable;
    public TableColumn<Module, String> mySemesterModuleTableModule;
    public TableColumn<Module, ProgressBar> mySemesterModuleTableProgress;
    public TableView<Assignment> mySemesterDeadlineTable;
    public TableColumn<Assignment,String> mySemesterDeadlineTableAssignment;
    public TableColumn<Assignment,String> mySemesterDeadlineTableModule;
    public TableColumn<Assignment,Date> mySemesterDeadlineTableDeadline;
    public TableColumn<Assignment,ProgressBar> mySemesterDeadlineTableProgression;
    public Button mySemesterAddSemester;
    public Button editAccountButton;


    public Text userEmail;
    public Text userFullName;
    public Text userStatus;



    //setting theme variables
    public ToggleGroup themesToggleGroup;

    public ToggleButton matrixThemeToggle;
    public ToggleButton lightThemeToggle;
    public ToggleButton darkThemeToggle;
    public Pane myModulesPane;
    public Pane myTasksPane;
    public Pane settingsPane;
    public Pane mySemesterPane;
    public Rectangle settingsDivider;
    public Rectangle mySemesterDivider;
    public Rectangle myModulesDivider;
    public Rectangle myTasksDivider;

    private static final String[] pages = {"settings", "mySemester", "myModules", "myTasks", "myAccountSettings"};
    public Text themesText;
    public ImageView imageLight;
    public ImageView imageMatrix;
    public Line settingsBreaker1;
    public Line settingsBreaker2;
    public Pane mainPane;
    public ImageView imageDark;

    ////////used for account settings page/////////

    public TextField verificationCodeEntry;
    public TextField emailEntry;
    public TextField firstNameEntry;
    public TextField lastNameEntry;
    public TextField oldPasswordEntry;
    public TextField newPasswordEntry1;
    public TextField newPasswordEntry2;
    public Button saveAccountButton;
    public Button sendVerificationButton;
    public Rectangle myAccountSettingsDivider;

    private Semester selectedSemester;
    private final User user = Login.getLoggedInUser();

    public Controller(){
        selectedSemester = user.getCurrentSemester();
    }

    /**
     need to grab user info for who's currently logged in.
     */

    public void loadUser(){
        String email = user.getEmail();
        String status = "Student";
        String fullName = user.getFirstname() + " " + user.getSurname();
        //System.out.println(email + status + fullName);
        userEmail.setText(email);
        userFullName.setText(fullName);
        userStatus.setText(status);

        //for AccountSettings page
        emailEntry.setText(email);
        firstNameEntry.setText(user.getFirstname());
        lastNameEntry.setText(user.getSurname());
    }

    public void clearDashboardAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Dashboard.fxml");
    }


    public void settingsAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        loadUser();
        Main.dashboardLoad(actionEvent, "settings");
    }

    // My Semester Page:
    public void mySemesterAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "mySemester");
        if(selectedSemester != null){
            loadSemestersSelector();
            loadModulesOverview();
            loadTaskOverview();
        }

    }

    // Semester selector controls:
    private void loadSemestersSelector(){
        mySemesterSelector.getItems().clear();
        HashMap<String, Semester> semestersMap = user.getUSER_SEMESTER_MAP();
        mySemesterSelector.getItems().addAll(semestersMap.values());
        mySemesterSelector.setValue(user.getCurrentSemester());
        selectedSemester =user.getCurrentSemester();

        //Listener:
        mySemesterSelector.setOnAction((event)->{
            Semester selected = mySemesterSelector.getSelectionModel().getSelectedItem();
            if(selected != null && !selected.getSemId().equals(selectedSemester.getSemId())){
                selectedSemester = selected;
                loadModulesOverview();
                loadTaskOverview();
            }
        });
    }

    // Module Overview:
    private void loadModulesOverview(){
        mySemesterModuleTable.getItems().clear();
        HashMap<String, Module> modules = selectedSemester.getModules();
        mySemesterModuleTableModule.setCellValueFactory(new PropertyValueFactory<>("name"));
        mySemesterModuleTableProgress.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        mySemesterModuleTable.getItems().addAll(modules.values());

    }

    // Tasks Overview
    private void loadTaskOverview(){
        mySemesterDeadlineTable.getItems().clear();
        ArrayList<Assignment> assignments = new ArrayList<>();
        //Get all the current assignments.
        for (Module module:
             selectedSemester.getModules().values()) {
            for(Assignment assignment: module.getAssignments().values()){
                assignments.add(assignment);
            }
        }

        //Define table layout.
        mySemesterDeadlineTableAssignment.setCellValueFactory(new PropertyValueFactory<>("name"));
        mySemesterDeadlineTableModule.setCellValueFactory(new PropertyValueFactory<>("module"));
        mySemesterDeadlineTableDeadline.setCellValueFactory(new PropertyValueFactory<>("date"));
        mySemesterDeadlineTableProgression.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        mySemesterDeadlineTable.getItems().addAll(assignments);
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
        loadSemestersSelector();
        loadModulesOverview();
        loadTaskOverview();
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


    ///////following controls Settings Pane/////////


    public void editAccountButtonAction(ActionEvent actionEvent) {
        Main.dashboardLoad(actionEvent, "myAccountSettings");
    }


    public void matrixThemeToggleAction(ActionEvent actionEvent) {
        System.out.println("matrix theme");
        toggleTheme(actionEvent, "Matrix");
    }

    public void lightThemeToggleAction(ActionEvent actionEvent) {
        System.out.println("light theme");
        toggleTheme(actionEvent, "Light");
    }

    public void darkThemeToggleAction(ActionEvent actionEvent) {
        System.out.println("dark theme");
        toggleTheme(actionEvent, "Dark");
    }

    private void toggleTheme(ActionEvent actionEvent, String theme){
        String firstColour;
        String secondColour;
        String thirdColour;

        Scene scene = ((Node) actionEvent.getSource()).getScene();

        switch (theme) {
            case "Matrix":
                firstColour = "#204829";
                secondColour = "#22b455";
                thirdColour = "#020204";
                break;
            case "Light":
                firstColour = "BLACK";
                secondColour = "#3d84ff";
                thirdColour = "#dbdbdb";
                break;
            case "Dark":
                firstColour = "#ffffff";
                secondColour = "#1DB954";
                thirdColour = "#404040";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + theme);
        }

        //setting all images to invisible
        imageLight.setVisible(false);
        imageMatrix.setVisible(false);
        imageDark.setVisible(false);

        for (String page : pages){
            Rectangle divider = (Rectangle) scene.lookup("#" + page + "Divider");
            Pane screen = (Pane) scene.lookup("#" + page + "Pane");
            ImageView image = (ImageView) scene.lookup("#image" + theme);
            divider.setStyle("-fx-stroke: " + secondColour);
            screen.setStyle("-fx-background-color: " + thirdColour);
            image.setVisible(true);

        }

        mainPane.setStyle("-fx-background-color: " + thirdColour);

        themesText.setStyle("-fx-fill: " + firstColour);
        userEmail.setStyle("-fx-fill: " + firstColour);
        userFullName.setStyle("-fx-fill: " + firstColour);
        userStatus.setStyle("-fx-fill: " + firstColour);

        settingsBreaker1.setStyle("-fx-stroke: " + firstColour);
        settingsBreaker2.setStyle("-fx-stroke: " + firstColour);

        matrixThemeToggle.setStyle("-fx-background-color: " + secondColour);
        lightThemeToggle.setStyle("-fx-background-color: " + secondColour);
        darkThemeToggle.setStyle("-fx-background-color: " + secondColour);
        editAccountButton.setStyle("-fx-background-color: " + secondColour);


    }

    public void saveAccountButtonAction(ActionEvent actionEvent) {
        System.out.println("need to implement functionality");
    }

    public void sendVerificationButtonAction(ActionEvent actionEvent) {
        System.out.println("need to implement functionality");
    }
}
