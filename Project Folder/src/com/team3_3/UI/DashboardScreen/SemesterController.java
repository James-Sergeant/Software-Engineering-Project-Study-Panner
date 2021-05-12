package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class SemesterController {
    private final Controller controller;

    SemesterController(Controller controller){
        this.controller = controller;
    }

    public void updateSemesterPage(){
        loadSemestersSelector();
        loadModulesOverview();
        loadTaskOverview();
    }

    // Semester selector controls:
    public void loadSemestersSelector(){
        controller.mySemesterSelector.getItems().clear();
        HashMap<String, Semester> semestersMap = controller.user.getUSER_SEMESTER_MAP();
        controller.mySemesterSelector.getItems().addAll(semestersMap.values());
        controller.mySemesterSelector.setValue(controller.user.getCurrentSemester());
        controller.selectedSemester =controller.user.getCurrentSemester();

        //Listener:
        controller.mySemesterSelector.setOnAction((event)->{
            Semester selected = controller.mySemesterSelector.getSelectionModel().getSelectedItem();
            if(selected != null && !selected.getSemId().equals(controller.selectedSemester.getSemId())){
                controller.selectedSemester = selected;
                loadModulesOverview();
                loadTaskOverview();
            }
        });
    }

    // Module Overview:
    public void loadModulesOverview(){
        controller.mySemesterModuleTable.getItems().clear();
        HashMap<String, Module> modules = controller.selectedSemester.getModules();
        controller.mySemesterModuleTableModule.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.mySemesterModuleTableProgress.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        for (Module module: modules.values()){
            module.updateProgress();
        }

        controller.mySemesterModuleTable.getItems().addAll(modules.values());

    }

    // Tasks Overview
    public void loadTaskOverview(){
        controller.mySemesterDeadlineTable.getItems().clear();
        ArrayList<Assignment> assignments = new ArrayList<>();
        //Get all the current assignments.
        for (Module module:
                controller.selectedSemester.getModules().values()) {
            for(Assignment assignment: module.getAssignments().values()){
                assignments.add(assignment);
            }
        }

        //Define table layout.
        controller.mySemesterDeadlineTableAssignment.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.mySemesterDeadlineTableModule.setCellValueFactory(new PropertyValueFactory<>("module"));
        controller.mySemesterDeadlineTableDeadline.setCellValueFactory(new PropertyValueFactory<>("date"));
        controller.mySemesterDeadlineTableProgression.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        for(Assignment assignment: assignments){
            assignment.updateProgress();
        }

        controller.mySemesterDeadlineTable.getItems().addAll(assignments);
    }

    /**
     * Adds the semester to the user if is valid.
     * @param actionEvent
     */
    public void addSemesterAction(ActionEvent actionEvent) {
        Semester semester;
        //Reset the label:
        controller.invalidFileLabel.setVisible(false);

        //If the semester file is valid, add it to the user:
        if((semester = semesterFileHandler()) != null){
            try {
                Login.getLoggedInUser().addSemester(semester.getSemId(),semester);
            } catch (User.SemesterAlreadyExits semesterAlreadyExits) {
                controller.invalidFileLabel.setText("You already have added this semester.");
                controller.invalidFileLabel.setVisible(true);
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
    public Semester semesterFileHandler(){
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
            controller.invalidFileLabel.setText("This is not a valid file");
            controller.invalidFileLabel.setVisible(true);
        } catch (Semester.DateOutOfBoundsException e) {
            controller. invalidFileLabel.setText("The date on this file is invalid");
            controller.invalidFileLabel.setVisible(true);
        } catch (Semester.ProgressOver100Exception e) {
            controller.invalidFileLabel.setText("The progress is too large");
            controller.invalidFileLabel.setVisible(true);
        }
        return null;
    }
}
