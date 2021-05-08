package com.team3_3.Planner.ModuleData;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
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
        IntervalCategoryDataset dataset = makeCategoryDataset(module);
        JFreeChart ganttChart = ChartFactory.createGanttChart(module.getName(),"Milestones", "Time", dataset,false,false,false);

        ChartPanel panel = new ChartPanel(ganttChart);
        setContentPane(panel);



    }

    private IntervalCategoryDataset makeCategoryDataset(Module module)
    {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        for (int i = 0; i < module.getAssigments().size(); i++)
        {
            for (int j = 0; j < module.getAssigments().get(i).getMilestones().size(); j++)
            {
                TaskSeries series1 = new TaskSeries(module.getAssigments().get(i).getMilestones().get(j).getName());
                for (int k = 0; k < module.getAssigments().get(i).getMilestones().get(j).getTasks().size(); k++)
                {
                    com.team3_3.Planner.ModuleData.Task tk = module.getAssigments().get(i).getMilestones().get(j).getTasks().get(k);
                    series1.add(new Task(tk.getName(), Date.from(LocalDate.of(tk.getStartDate().getYear(), tk.getStartDate().getMonth(),tk.getStartDate().getDay()).atStartOfDay().toInstant(ZoneOffset.UTC)), Date.from(LocalDate.of(tk.getEndDate().getYear(), tk.getEndDate().getMonth(), tk.getEndDate().getDay()).atStartOfDay().toInstant(ZoneOffset.UTC))
                    ));
                }
                dataset.add(series1);

            }
        }

        //TaskSeries series1 = new TaskSeries(""); series1.add(new Task
        //module.getAssigments().get(1).getMilestones();
        return dataset;
    }



    }