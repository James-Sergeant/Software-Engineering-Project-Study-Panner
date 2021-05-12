package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Milestone;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.ModuleData.Task;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.Planner.utils.Email;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jfree.util.Log;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ModulesController {
    public final Controller controller;

    ModulesController(Controller controller){
        this.controller = controller;
    }

    public void loadExt(){
        //Request Extension:
        controller.RequestExtensionButton.setOnAction(actionEvent -> {
            boolean t = controller.extensionRequestPane.isVisible();
            controller.extensionRequestPane.setVisible(!t);
        });


        controller.extensionSub.setOnAction(actionEvent -> {
            extRequest();
        });

        controller.SubmitExtensionButton.setOnAction(actionEvent -> {
            boolean t = controller.extentionApprovalPane.isVisible();
            controller.extentionApprovalPane.setVisible(!t);
        });

        controller.extensionsCodeSub.setOnAction(actionEvent -> {
            extApp();
        });
    }


    public void extRequest(){
        controller.extLabel.setVisible(false);
        Assignment assignment = controller.moduleDeliverableTable.getSelectionModel().getSelectedItem();
        String email = controller.extensionMOEmail.getText();
        LocalDate date = controller.extensionDatePicker.getValue();
        String code = assignment.genExtCode(email,date);
        User user = Login.getLoggedInUser();
        String body = "Hi, student " + user.getFirstname() +" "+user.getSurname()+", "+ user.getEmail()+"\n"+
                      "has request an extension for "+ assignment.getName() + "to date: " + date + ".\n" +
                      "to approve this please give the student this code: " + code;

        System.out.println("Can you see me?");
        try {
            Email.sendEmail(email, "Extension request",body);
            controller.extensionRequestPane.setVisible(false);
            controller.extLabel.setText("Email sent!");
            controller.extLabel.setVisible(true);
        } catch (MessagingException e) {
            controller.extLabel.setText("Email failed!");
            controller.extLabel.setVisible(true);
        }

    }
    public void extApp(){
        controller.extLabel.setVisible(false);
        Assignment assignment = controller.moduleDeliverableTable.getSelectionModel().getSelectedItem();
        String code = controller.extensionCode.getText();
        if(assignment.validCode(code)){
            controller.extLabel.setText("Change Made");
            controller.extLabel.setVisible(true);
            controller.extentionApprovalPane.setVisible(false);
            loadDeliverableTable();
        }else{
            controller.extLabel.setText("Invalid code!");
            controller.extLabel.setVisible(true);
        }
    }


    public void updateModulesPage(){
        loadModulesSelector();
        if(controller.selectedModule != null){
            loadDeliverableTable();
            loadTaskTable();
        }

    }

    private void loadModulesSelector(){
        controller.myModulesSelector.getItems().clear();
        //Gets all of the modules
        Collection<Module> modules = controller.user.getCurrentSemester().getModules().values();
        controller.myModulesSelector.getItems().addAll(modules);
        //Sets to default
        controller.myModulesSelector.setValue(controller.selectedModule);

        //Listener:
        controller.myModulesSelector.setOnAction(actionEvent -> {
            //Sets the selected module to what the user has picked.
            Module selected = controller.myModulesSelector.getSelectionModel().getSelectedItem();
            if(selected!= null && !selected.equals(controller.selectedModule)) {
                controller.selectedModule = selected;
                loadDeliverableTable();
                loadTaskTable();
            }
        });
    }

    private void loadDeliverableTable(){
        loadOverview();
        controller.moduleDeliverableTable.getItems().clear();
        Collection<Assignment> assignments = controller.selectedModule.getAssignments().values();
        controller.moduleDeliverableTableDeliverable.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.moduleDeliverableTableDueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        controller.moduleDeliverableTableProgress.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        for (Assignment assignment: assignments){
            assignment.updateProgress();
        }

        controller.moduleDeliverableTable.getItems().addAll(assignments);
    }

    private ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        Collection<Assignment> assignments = controller.selectedModule.getAssignments().values();
        for (Assignment assignment:
                assignments) {
            if(assignment.getMilestones() != null){
                for (Milestone milestone:
                        assignment.getMilestones().values()) {
                    if(milestone.getTasks() != null){
                        milestone.updateTaskProgress();
                        tasks.addAll(milestone.getTasks().values());
                    }
                }
            }
        }
        return tasks;
    }

    private void loadOverview(){
        //Progress:
        controller.selectedModule.updateProgress();
        double progress = controller.selectedModule.getProgress();
        controller.moduleProgressBar.setProgress(progress);
        //Task:
        ArrayList<Task> tasks = getTasks();
        int com = 0;

        for (Task task:
             tasks) {
            if(task.getFinished()){
                com++;
            }
        }

        controller.numTaskCompleted.setText(String.valueOf(com));
        controller.numTaskRemaining.setText(String.valueOf(tasks.size() - com));
    }


    private void loadTaskTable(){
        controller.moduleUpcomingTaskTable.getItems().clear();
        ArrayList<Task> tasks = getTasks();

        controller.moduleUpcomingTaskTableTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.moduleUpcomingTaskTableDueDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        controller.moduleUpcomingTaskTableProgress.setCellValueFactory(new PropertyValueFactory<>("progressBar"));
        controller.moduleUpcomingTaskTable.getItems().addAll(tasks);
    }




}
