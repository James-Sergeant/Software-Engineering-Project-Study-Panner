package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
 * <h2>Refrences: </>
 *  -Offical JavaDoc help page @link https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html
 */

public class Milestone implements Serializable
{
    // instance variables
    public final transient int SSN = 1;
    private String name;
    private int weighting;
    private HashMap<String, Task> tasks = new HashMap<>();
    int progress = 0;

    // constructor
    public Milestone (String name, int weighting) throws Semester.ProgressOver100Exception
    {
        this.name = name;
        this.weighting = weighting;
        if (weighting > 100)
        {
            throw new Semester.ProgressOver100Exception(weighting);
        }
    }

    public HashMap<String, Task> getTasks()
    {
        return this.tasks;
    }

    // methods
    public void addTask(Task task) throws Semester.NameAlreadyExistsException, Semester.ProgressOver100Exception
    {
        int cumulative = 0;
        for (Task t : this.tasks.values()) // checking tasks doesn't have same name
        {
            cumulative += t.getWeighting();
            if (task.getName().equals(t.getName()))
            {
                throw new Semester.NameAlreadyExistsException(task.getName());
            }
        }

        if (cumulative + task.getWeighting() > 100)
        {
            throw new Semester.ProgressOver100Exception(task.getWeighting());
        }

        tasks.put(task.getName(), task);
        User.saveUser(Login.getLoggedInUser());
    }

    public void updateProgress(){
        for(Task task: tasks.values()){
            task.updateProgress();
            progress = 0;
            progress += (task.getProgress() * ((double) task.getWeighting()/100));
        }
    }

    public int getProgress() {
        return progress;
    }

    public void updateTaskProgress(){
        for(Task task: tasks.values()){
            task.updateProgress();
        }
    }

    public boolean getFinished()
    {
        for (Task t : tasks.values())
        {
            if (!t.getFinished()) // if task isn't finished
            {
                return false;
            }
        }
        return true; // if all tasks finished
    }

    public int getMaximum()
    {
        int cumulative = 0;
        for (Task t : tasks.values())
        {
            cumulative += t.getWeighting();
        }
        int maximum = 100 - cumulative;
        if (maximum < 0)
        {
            maximum = 0;
        }

        return maximum;
    }

    public LocalDate getStartDate()
    {
        Task task = (Task) tasks.values().toArray()[0]; // first element
        LocalDate date = task.getStartDate(); // change to first element in tasks

        for (Task t : tasks.values())
        {
            if (t.getStartDate().isBefore(date))
            {
                date = t.getStartDate();
            }
        }

        if (tasks.isEmpty())
        {
            date = null;
        }

        return date;
    }

    public LocalDate getEndDate()
    {
        Task task = (Task) tasks.values().toArray()[0]; // first element
        LocalDate date = task.getEndDate(); // change to first element in tasks

        for (Task t : tasks.values())
        {
            if (t.getEndDate().isAfter(date))
            {
                date = t.getEndDate();
            }
        }

        if (tasks.isEmpty())
        {
            date = null;
        }

        return date;
    }

    @Override
    public String toString() {
        return name;
    }

    // getters
    public int getWeighting()
    {
        return this.weighting;
    }
    public String getName()
    {
        return this.name;
    }
}
