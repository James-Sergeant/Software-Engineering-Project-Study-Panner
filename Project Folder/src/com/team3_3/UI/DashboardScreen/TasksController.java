package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.*;
import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.UI.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TasksController {
    private final Controller controller;
    private File workFile;

    TasksController(Controller controller){
        this.controller = controller;

    }

    public void update(){
        selectUpdate();
    }


    public void onLoad(){
        //Milestone Adder:
        controller.myTaskAddMilestoneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean set = !controller.addMilestoneBox.visibleProperty().get();
                controller.addMilestoneBox.setVisible(set);
                setupMilestoneBox();
            }
        });

        controller.myTaskAddtaskButton.setOnAction(actionEvent -> {
            boolean set = !controller.addTaskBox.visibleProperty().get();
            controller.addTaskBox.setVisible(set);
            setupTaskBox();
        });


        update();
    }

    //Drop Downs:
    private void selectUpdate(){
        selectModuleUpdate();
        selectAssignmentUpdate();
    }
    private void selectModuleUpdate(){
        controller.myTaskSelectModule.getItems().clear();
        //Get all the modules:
        Collection<Module> modules = controller.user.getCurrentSemester().getModules().values();
        controller.myTaskSelectModule.getItems().addAll(modules);
        //Set default:
        controller.myTaskSelectModule.setValue(controller.selectedModule);

        controller.myTaskSelectModule.setOnAction(actionEvent -> {
            Module selected = controller.myTaskSelectModule.getSelectionModel().getSelectedItem();
            if(selected != null && !selected.equals(controller.selectedModule)){
                controller.selectedModule = selected;
                controller.selectedAssignment = null;
                selectAssignmentUpdate();
            }
        });
    }

    private void selectAssignmentUpdate(){
        ComboBox<Assignment> selector = controller.myTaskSelectAssignement;
        selector.getItems().clear();
        if(controller.selectedModule != null){
            selector.setVisible(true);
            Collection<Assignment> assignments = controller.selectedModule.getAssignments().values();
            selector.getItems().addAll(assignments);
            selector.setValue(controller.selectedAssignment);

            selector.setOnAction(actionEvent -> {
                Assignment selected = selector.getSelectionModel().getSelectedItem();

                if(selected!=null && !selected.equals(controller.selectedAssignment)){
                    controller.selectedAssignment = selected;
                    controller.selectedMilestone = null;
                    selectMilestoneUpdate();
                }
            });

        }else{
            controller.myTaskSelectAssignement.setVisible(false);
        }
    }

    private void selectMilestoneUpdate(){
        ComboBox<Milestone> selector = controller.myTaskSelectMilestone;
        Button button = controller.myTaskAddMilestoneButton;
        selector.getItems().clear();
        if(controller.selectedAssignment != null){
            selector.setVisible(true);
            button.setVisible(true);
            Collection<Milestone> assignments = controller.selectedAssignment.getMilestones().values();
            selector.getItems().addAll(assignments);
            selector.setValue(controller.selectedMilestone);

            selector.setOnAction(actionEvent -> {
                Milestone selected = selector.getSelectionModel().getSelectedItem();
                if(selected!=null && !selected.equals(controller.selectedMilestone)){
                    controller.selectedMilestone = selected;
                    controller.selectedTask = null;
                    selectTaskUpdate();
                }
            });

        }else{
            selector.setVisible(false);
            button.setVisible(false);
        }
    }


    private void setupMilestoneBox(){
        int max = controller.selectedAssignment.getMaximum();
        controller.myTaskAddMilestoneWeighting.setMax(max);
        controller.myTaskAddMilestoneAdd.setOnAction(actionEvent -> {
            addNewMilestone();
        });
        controller.myTaskAddMilestoneWeighting.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String val = Integer.toString((int)controller.myTaskAddMilestoneWeighting.getValue());
                controller.myTaskAddMilestoneWeightingBox.setText(val);
            }
        });
        controller.myTaskAddMilestoneWeightingBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String valString = controller.myTaskAddMilestoneWeightingBox.getText();
                try {
                    int val = Integer.parseInt(valString);
                    if(val > controller.myTaskAddMilestoneWeighting.getMax()){
                        controller.myTaskAddMilestoneWeighting.setValue(controller.myTaskAddMilestoneWeighting.getMax());
                        controller.myTaskAddMilestoneWeightingBox.setText(Integer.toString((int)controller.myTaskAddMilestoneWeighting.getValue()));
                    }
                    else{
                        controller.myTaskAddMilestoneWeighting.setValue(val);
                    }
                }catch (NumberFormatException e){
                    controller.myTaskAddMilestoneWeightingBox.setText(Integer.toString((int)controller.myTaskAddMilestoneWeighting.getValue()));
                }
            }
        });
    }


    private void addNewMilestone(){
        String name = controller.myTaskAddMilestoneName.getText();
        double weighting = controller.myTaskAddMilestoneWeighting.getValue();
        try {
            Milestone milestone = new Milestone(name,(int)weighting);
            controller.selectedAssignment.addMilestone(milestone);
            selectMilestoneUpdate();
            controller.addMilestoneBox.setVisible(false);
            controller.myTaskSelectMilestone.setValue(milestone);
        } catch (Semester.ProgressOver100Exception | Semester.NameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    private void selectTaskUpdate(){
        ComboBox<Task> selector = controller.myTaskSelectTask;
        Button button = controller.myTaskAddtaskButton;
        selector.getItems().clear();

        if(controller.selectedMilestone != null){
            selector.setVisible(true);
            button.setVisible(true);
            Collection<Task> assignments = controller.selectedMilestone.getTasks().values();
            selector.getItems().addAll(assignments);
            selector.setValue(controller.selectedTask);


            addWorkUpdate();
            displayWorkUpdate();
            updateTaskProgress();


            selector.setOnAction(actionEvent -> {
                Task selected = selector.getSelectionModel().getSelectedItem();

                System.out.println(selected);
                System.out.println(controller.selectedTask);
                System.out.println(selected.equals(controller.selectedTask));

                if(!selected.equals(controller.selectedTask)){
                    controller.selectedTask = selected;
                    addWorkUpdate();
                    displayWorkUpdate();
                    updateTaskProgress();
                }
            });
        }else{
            selector.setVisible(false);
            button.setVisible(false);
        }
    }

    private void setupTaskBox(){
        int maxWeight = controller.selectedMilestone.getMaximum();
        controller.MyTaskAddTaskWeighting.setMax(maxWeight);
        controller.MyTaskAddTaskAdd.setOnAction(actionEvent -> {
            addNewTask();
        });
        controller.MyTaskAddTaskWeighting.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String val = Integer.toString((int)controller.MyTaskAddTaskWeighting.getValue());
                controller.MyTaskAddTaskWeightingText.setText(val);
            }
        });
        controller.MyTaskAddTaskWeightingText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String valString = controller.MyTaskAddTaskWeightingText.getText();
                try {
                    int val = Integer.parseInt(valString);
                    if(val > controller.MyTaskAddTaskWeighting.getMax()){
                        controller.MyTaskAddTaskWeighting.setValue(controller.MyTaskAddTaskWeighting.getMax());
                        controller.MyTaskAddTaskWeightingText.setText(Integer.toString((int)controller.MyTaskAddTaskWeighting.getValue()));
                    }
                    else{
                        controller.MyTaskAddTaskWeighting.setValue(val);
                    }
                }catch (NumberFormatException e){
                    controller.MyTaskAddTaskWeightingText.setText(Integer.toString((int)controller.MyTaskAddTaskWeighting.getValue()));
                }
            }
        });
    }
    private void addNewTask(){
        String name = controller.MyTaskAddTaskName.getText();
        double weighting = controller.MyTaskAddTaskWeighting.getValue();
        LocalDate startDateValue = controller.MyTaskAddTaskStartDate.getValue();
        LocalDate endDateValue = controller.MyTaskAddTaskEndDate.getValue();
        try {
            Task task = new Task(name, (int) weighting, startDateValue, endDateValue);
            controller.selectedMilestone.addTask(task);
            controller.addTaskBox.setVisible(false);
            controller.myTaskSelectTask.setValue(task);
            selectTaskUpdate();
            addWorkUpdate();
            displayWorkUpdate();
        } catch (Semester.ProgressOver100Exception e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Semester.NameAlreadyExistsException e) {
            controller.MyTaskAddTaskError.setVisible(true);
        }
    }

    //Add work:
    private void addWorkUpdate(){
        if(controller.selectedTask != null){
            addWorkOnShow();
        }
    }

    private void addWorkOnShow(){
        controller.myTaskAddWorkPane.setVisible(true);
        int maxWeight = controller.selectedTask.getMaximum();
        controller.myTaskAddWorkWeighting.setMax(maxWeight);
        controller.myTaskAddWorkButton.setOnAction(actionEvent -> {
            addNewWork();
        });
        controller.myTaskAddWorkWeighting.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String val = Integer.toString((int)controller.myTaskAddWorkWeighting.getValue());
                controller.myTaskAddWorkWeightingText.setText(val);
            }
        });
        controller.myTaskAddWorkWeightingText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String valString = controller.myTaskAddWorkWeightingText.getText();
                try {
                    int val = Integer.parseInt(valString);
                    if(val > controller.myTaskAddWorkWeighting.getMax()){
                        controller.myTaskAddWorkWeighting.setValue(controller.myTaskAddWorkWeighting.getMax());
                        controller.myTaskAddWorkWeightingText.setText(Integer.toString((int)controller.myTaskAddWorkWeighting.getValue()));
                    }
                    else{
                        controller.myTaskAddWorkWeighting.setValue(val);
                    }
                }catch (NumberFormatException e){
                    controller.myTaskAddWorkWeightingText.setText(Integer.toString((int)controller.myTaskAddWorkWeighting.getValue()));
                }
            }
        });

        controller.myTaskAddWorkAddFile.setOnAction(actionEvent -> {
            workFile = workFile();
        });
    }

    private void addNewWork(){
        String name = controller.myTaskAddWorkName.getText();
        String note = controller.myTaskAddWorkNotes.getText();
        File file = workFile;
        double weighting = controller.myTaskAddWorkWeighting.getValue();
        try {
            Work work = new Work(name,note,(int)weighting, file);
            controller.selectedTask.addWork(work);
            workFile = null;
            displayWorkUpdate();
        } catch (Semester.ProgressOver100Exception e) {
            e.printStackTrace();
        } catch (Semester.NameAlreadyExistsException e) {
            e.printStackTrace();
        }
        updateTaskProgress();
    }

    public File workFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select work file");
        return fileChooser.showOpenDialog(Main.mainStage);
    }
    public void displayWorkUpdate(){
        if(controller.selectedTask!= null && controller.selectedTask.getWork() != null){
            displayWork();
        }
    }

    public void displayWork(){
        controller.myTaskWorkTable.getItems().clear();
        Collection<Work> work = controller.selectedTask.getWork();

        //Table layout:
        controller.myTaskWorkTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.myTaskWorkTableNotes.setCellValueFactory(new PropertyValueFactory<>("note"));
        controller.myTaskWorkTableFile.setCellValueFactory(new PropertyValueFactory<>("button"));
        controller.myTaskWorkTableWeighting.setCellValueFactory(new PropertyValueFactory<>("progressIndicator"));

        controller.myTaskWorkTable.getItems().addAll(work);
    }

    private void updateTaskProgress(){
        if(controller.selectedTask != null){
            int progress = Math.abs(controller.selectedTask.getMaximum() -100);
            System.out.println(progress);
            controller.TaskProgressBar.setProgress((double)progress/100);
        }
    }
}
