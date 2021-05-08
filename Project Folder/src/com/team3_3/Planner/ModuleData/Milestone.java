
package com.team3_3.Planner.ModuleData;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<Task> taskStore = new ArrayList<>(;)

    // milestone date - calculated by the latest end date of task within tasks
    // date - has to be before end date of assignment

    // constructor
    public Milestone (String name, int weighting) throws ProgressOver100Exception {
        this.name = name;
        if (weighting > 100)
        {
            throw new ProgressOver100Exception("The weighting is over 100");
        }
        this.weighting = weighting;
    }

    // methods
    public void addTask(Task task) throws NameAlreadyExistsException {
        for (int i = 0; i < taskStore.size(); i++)
        {
            if (taskStore.get(i).getName() != null && taskStore.get(i).getName().equals(task.getName()))
            {

                throw new NameAlreadyExistsException("A task with this name already exists");
            }
        }

        taskStore.add(task);
        tasks.put(task.getName(), task);
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

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return name;
    }
}
