package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.*;
import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
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



    //Other
    public final User user = Login.getLoggedInUser();

    public Controller(){
        selectedSemester = user.getCurrentSemester();
        semesterController = new SemesterController(this);
        modulesController = new ModulesController(this);
        tasksController = new TasksController(this);
    }

    public void clearDashboardAction(ActionEvent actionEvent) throws IOException {
        Main.changeMainScene(actionEvent, "Dashboard.fxml");
    }

    public void myAccountAction(ActionEvent actionEvent) {
        System.out.println("insert: myAccountAction");
    }

    public void settingsAction(ActionEvent actionEvent) throws IOException, InterruptedException {
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
        GanttChart ganttchart = new GanttChart(selectedModule);
        ganttchart.setSize(400,268);
        ganttchart.setLocationRelativeTo(null);
        ganttchart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ganttchart.setVisible(true);

    });
    }

    /////Following controls Dashboard.fxml - mySemester\\\\\





}
