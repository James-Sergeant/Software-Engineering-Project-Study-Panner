package com.team3_3.Planner.ModuleData;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

public class GanttChart extends JFrame
{
    public GanttChart(Module module)
    {
        super(module.getName());
        IntervalCategoryDataset data = makeCategoryDataset(module);
        JFreeChart ganttChart = ChartFactory.createGanttChart(module.getName(),"Objectives", "Date", data,true,false,false);

        ChartPanel panel = new ChartPanel(ganttChart);
        setContentPane(panel);
    }

    private IntervalCategoryDataset makeCategoryDataset (Module module)
    {
        TaskSeriesCollection data = new TaskSeriesCollection();
        TaskSeries milestones = new TaskSeries(/*a.getName() */ "milestones"); // all milestones within an assignment

        TaskSeries tasks = new TaskSeries(/*m.getName() +*/ " tasks"); // all tasks within a milestone

        for (Assignment a : module.getAssignments().values()) // for each assignment within a module
        {
            //TaskSeries milestones = new TaskSeries(/*a.getName() */ "milestones"); // all milestones within an assignment


            for (Milestone m : a.getMilestones().values()) // for each milestone within an assignment
            {
                //milestones.add(new Task (m.getName(), Date.from(LocalDate.of(m.getStartDate().getYear(), m.getStartDate().getMonth(), m.getStartDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(LocalDate.of(m.getEndDate().getYear(), m.getEndDate().getMonth(), m.getEndDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)))); // create milestone for gantt
                //TaskSeries tasks = new TaskSeries(m.getName() + " tasks"); // all tasks within a milestone

                for (com.team3_3.Planner.ModuleData.Task t : m.getTasks().values()) // all tasks within a milestone
                {
                    tasks.add(new Task (m.getName() +" "+ t.getName(), Date.from(LocalDate.of(t.getStartDate().getYear(), t.getStartDate().getMonth(), t.getStartDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(LocalDate.of(t.getEndDate().getYear(), t.getEndDate().getMonth(), t.getEndDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)))); // create task for gantt
                }

                //data.add (tasks); // adds all tasks within a milestone
            }

            //data.add (milestones); // adds all milestones
            for (Milestone m : a.getMilestones().values()) // for each milestone within an assignment
            {
                milestones.add(new Task (m.getName(), Date.from(LocalDate.of(m.getStartDate().getYear(), m.getStartDate().getMonth(), m.getStartDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(LocalDate.of(m.getEndDate().getYear(), m.getEndDate().getMonth(), m.getEndDate().getDayOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC)))); // create milestone for gantt

                //data.add (milestones); // adds all milestones

            }

        }
        data.add (tasks); // adds all tasks within a milestone
        data.add (milestones); // adds all milestones

        return data;
    }
}