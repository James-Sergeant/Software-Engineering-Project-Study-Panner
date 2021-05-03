package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
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
public class Module implements Serializable
{
    // instance variables
    public final transient int SSN = 1;
    private final String name;
    private HashMap<String,Assignment> assignments=new HashMap<String , Assignment>();
    private double progress;
    private transient ProgressBar progressBar;
    // constructor
    public Module(String name)
    {
        this.name = name;
        this.progressBar = new ProgressBar(0);
    }
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        progressBar = new ProgressBar(progress);
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
    public HashMap<String, Assignment> getAssignments(){
        return assignments;
    }
    public String getName()
    {
        return this.name;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}