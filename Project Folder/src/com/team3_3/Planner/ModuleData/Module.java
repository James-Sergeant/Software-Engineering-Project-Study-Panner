package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import com.team3_3.Planner.ModuleData.Assignment.Coursework;
import com.team3_3.Planner.ModuleData.Assignment.Exam;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

/**
 * <h1>Example Class</h1>
 *<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
 *  aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
 *  Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
 *  sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
 *
 * @author  {YOUR NAME HERE}
 * @version 1.0
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - {DATE}: {NOTES} - {INITIALS}
 *
 *
 *
 * <h2>Refrences: </>
 *  -Offical JavaDoc help page @link https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html
 */
public class Module implements Serializable, Updatable
{
    // instance variables
    public final transient int SSN = 1;
    private final String name;
    private HashMap<String,Assignment> assignments=new HashMap<>();
    private double progress;
    private transient ProgressBar progressBar;
    // constructor
    public Module(String name)
    {
        this.name = name;
        this.progressBar = new ProgressBar(0);
    }
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        ois.defaultReadObject();
        progressBar = new ProgressBar(progress);
    }

    public double getModuleCompletion()
    {
        double progress = 0; // starts off with 0% completion

        for (Assignment a : assignments.values())
        {
            if (a.getFinished()) // if the assignment is finished
            {
                progress += (a.getWeighting()*0.01);
            }
        }

        return progress;
    }

    @Override
    public void update()
    {
        this.progress = getModuleCompletion();
        this.progressBar.setProgress(progress);
    }

    // methods
    public void addAssignment(Assignment assignment)
    {
        assignments.put(assignment.getName(),assignment);
    }

    // testing
    public void AssignmentInfo()
    {
        for (String s: assignments.keySet())
        {
            System.out.println(getAssignment(s).getClass().getSimpleName() +": "+getAssignment(s).getName()+ " ("+getAssignment(s).getDate()+")");
        }
        System.out.println();
    }

    // getters
    public Assignment getAssignment(String name)
    {
        return assignments.get(name);
    }
    public HashMap<String, Assignment> getAssignments()
    {
        return assignments;
    }
    public String getName()
    {
        return this.name;
    }

    public ProgressBar getProgressBar()
    {
        return progressBar;
    }

    public static void main(String[] args) throws ParseException, Semester.ProgressOver100Exception
    {
        Coursework coursework = new Coursework("Coursework", "PROGRAMMING 3", "11/2/2020", 10, "1:30");
        Exam exam = new Exam("Exam", "PROGRAMMING 3", "12/2/2020", 50, "1:30", "2:30", 60, "Exam hall");
        Module mod = new Module("PROGRAMMING 3");
        mod.addAssignment(coursework);
        mod.addAssignment(exam);

        System.out.println(mod.getModuleCompletion());
    }
}