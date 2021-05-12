package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.*;
import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.UI.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import javax.swing.*;
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
 */

public class Controller {

    /////Following controls Dashboard.fxml\\\\\
    //Semester Page
    public Semester selectedSemester;
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
    private SemesterController semesterController;
    public Button mySemesterAddSemester;

    //Module Page
    private ModulesController modulesController;
    public Module selectedModule;
    public ComboBox<Module> myModulesSelector;
    public TableView<Assignment> moduleDeliverableTable;
    public TableColumn<Assignment,String> moduleDeliverableTableDeliverable;
    public TableColumn<Assignment, Date> moduleDeliverableTableDueDate;
    public TableColumn<Assignment, ProgressBar> moduleDeliverableTableProgress;
    public TableView<Task> moduleUpcomingTaskTable;
    public TableColumn<Task, String> moduleUpcomingTaskTableTask;
    public TableColumn<Task, Date> moduleUpcomingTaskTableDueDate;
    public TableColumn<Task, ProgressBar> moduleUpcomingTaskTableProgress;
    public Label numTaskCompleted;
    public Label numTaskRemaining;
    public ProgressBar moduleProgressBar;

    //Tasks page
    private TasksController tasksController;
    public Assignment selectedAssignment;
    public Milestone selectedMilestone;
    public Task selectedTask;
    //Drop Downs:
    public ComboBox<Module> myTaskSelectModule;
    public ComboBox<Assignment> myTaskSelectAssignement;
    public ComboBox<Milestone> myTaskSelectMilestone;
    public ComboBox<Task> myTaskSelectTask;
    //Add Milestone:
    public Button myTaskAddMilestoneButton;
    public Pane addMilestoneBox;
    public TextField myTaskAddMilestoneName;
    public Slider myTaskAddMilestoneWeighting;
    public TextField myTaskAddMilestoneWeightingBox;
    public Label myTaskAddMilestoneError;
    public Button myTaskAddMilestoneAdd;
    //Add Task:
    public Button myTaskAddtaskButton;
    public Pane addTaskBox;
    public Label MyTaskAddTaskError;
    public TextField MyTaskAddTaskName;
    public Slider MyTaskAddTaskWeighting;
    public TextField MyTaskAddTaskWeightingText;
    public DatePicker MyTaskAddTaskStartDate;
    public DatePicker MyTaskAddTaskEndDate;
    public Button MyTaskAddTaskAdd;
    //Add work:
    public TextField myTaskAddWorkName;
    public TextArea myTaskAddWorkNotes;
    public Button myTaskAddWorkAddFile;
    public Slider myTaskAddWorkWeighting;
    public TextField myTaskAddWorkWeightingText;
    public Button myTaskAddWorkButton;
    public Pane myTaskAddWorkPane;
    //Display work:
    public TableView<Work> myTaskWorkTable;
    public TableColumn<Work, String> myTaskWorkTableName;
    public TableColumn<Work, String> myTaskWorkTableNotes;
    public TableColumn<Work, Button> myTaskWorkTableFile;
    public TableColumn<Work, ProgressIndicator> myTaskWorkTableWeighting;
    //Task Progress Bar:
    public ProgressBar TaskProgressBar;
    //Change deadline:
    public Button RequestExtensionButton;
    public Pane extensionRequestPane;
    public Button SubmitExtensionButton;
    public DatePicker extensionDatePicker;
    public TextField extensionMOEmail;
    public Button extensionSub;
    public TextField extensionCode;
    public Button extensionsCodeSub;
    public Pane extentionApprovalPane;
    public Label extLabel;





    ////////used for account settings page/////////

    public Button editAccountButton;


    public Text userEmail;
    public Text userFullName;
    public Text userStatus;

    private static final String[] pages = {"settings", "mySemester", "myModules", "myTasks", "myAccountSettings"};
    public Text themesText;
    public ImageView imageLight;
    public ImageView imageMatrix;
    public Line settingsBreaker1;
    public Line settingsBreaker2;
    public Pane mainPane;
    public ImageView imageDark;

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





    //Other
    public final User user = Login.getLoggedInUser();

    public Controller(){
        selectedSemester = user.getCurrentSemester();
        semesterController = new SemesterController(this);
        modulesController = new ModulesController(this);
        tasksController = new TasksController(this);
    }

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

    public void myAccountAction(ActionEvent actionEvent) {
        System.out.println("insert: myAccountAction");
    }

    public void settingsAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        loadUser();
        Main.dashboardLoad(actionEvent, "settings");
    }

    // My Semester Page:
    public void mySemesterAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        //Add semester button event handler.
        mySemesterAddSemester.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                semesterController.addSemesterAction(actionEvent);
            }
        });

        Main.dashboardLoad(actionEvent, "mySemester");
        if(selectedSemester != null){
            semesterController.updateSemesterPage();
        }
    }


    // My Modules Page:
    public void myModulesAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "myModules");
        if(user.getCurrentSemester() != null){
            modulesController.updateModulesPage();
            modulesController.loadExt();
        }
    }


    public void myTasksAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        Main.dashboardLoad(actionEvent, "myTasks");
        tasksController.onLoad();
    }


    public void accountSettingsAction(ActionEvent actionEvent) {
        System.out.println("insert: accountSettingsAction");
    }

    public void signOutAction() {
        Main.signOut();
    }

    public void showGanttChart(ActionEvent actionEvent) { SwingUtilities.invokeLater(() ->
    {
        try
        {
            GanttChart ganttchart = new GanttChart(selectedModule);
            ganttchart.setSize(400, 268);
            ganttchart.setLocationRelativeTo(null);
            //ganttchart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ganttchart.setVisible(true);
        }
        catch (NullPointerException e)
        {
            Platform.runLater(() -> {
            Alert moduleNullError = new Alert(Alert.AlertType.ERROR);
            moduleNullError.setHeaderText("No Module Present or Selected");
            moduleNullError.setContentText("Please select a module or load in the semester file");
            moduleNullError.showAndWait(); });
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Platform.runLater(() -> {
                Alert moduleNullError = new Alert(Alert.AlertType.ERROR);
                moduleNullError.setHeaderText("No tasks added");
                moduleNullError.setContentText("Please add tasks for the milestones");
                moduleNullError.showAndWait(); });
        }
    });
    }



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

    /////Following controls Dashboard.fxml - myAccountSettings\\\\\





}
