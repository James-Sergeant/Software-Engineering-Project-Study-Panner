package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Milestone;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.ModuleData.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;

public class TasksController {
    private final Controller controller;

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
                    selectTaskUpdate();
                }
            });

        }else{
            selector.setVisible(false);
            button.setVisible(false);
        }
    }


    private void setupMilestoneBox(){
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
                    controller.myTaskAddMilestoneWeighting.setValue(val);
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

            selector.setOnAction(actionEvent -> {
                Task selected = selector.getSelectionModel().getSelectedItem();

                if(selected!=null && !selected.equals(controller.selectedMilestone)){
                    controller.selectedTask = selected;
                }
            });

        }else{
            selector.setVisible(false);
            button.setVisible(false);
        }
    }

    private void setupTaskBox(){
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
                    controller.MyTaskAddTaskWeighting.setValue(val);
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
        } catch (Semester.ProgressOver100Exception e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Semester.NameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
