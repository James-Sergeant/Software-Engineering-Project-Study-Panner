package com.team3_3.UI.DashboardScreen;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Milestone;
import com.team3_3.Planner.ModuleData.Module;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.ModuleData.Task;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ModulesController {
    public final Controller controller;

    ModulesController(Controller controller){
        this.controller = controller;
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
        controller.moduleDeliverableTable.getItems().clear();
        Collection<Assignment> assignments = controller.selectedModule.getAssignments().values();
        controller.moduleDeliverableTableDeliverable.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.moduleDeliverableTableDueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        controller.moduleDeliverableTableProgress.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        controller.moduleDeliverableTable.getItems().addAll(assignments);
    }

    private void loadTaskTable(){
        controller.moduleUpcomingTaskTable.getItems().clear();
        ArrayList<Task> tasks = new ArrayList<>();
        Collection<Assignment> assignments = controller.selectedModule.getAssignments().values();
        for (Assignment assignment:
             assignments) {
            if(assignment.getMilestones() != null){
                for (Milestone milestone:
                        assignment.getMilestones().values()) {
                    if(milestone.getTasks() != null){
                        tasks.addAll(milestone.getTasks().values());
                    }
                }
            }
        }

    }




}
