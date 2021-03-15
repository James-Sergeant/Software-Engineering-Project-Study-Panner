package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.ModuleData.Assignment.Assignment;

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
public class Module
{
    private String name;

    private HashMap<String , Assignment > assignments=new HashMap<String , Assignment>();

    public Module(String name)
    {
        this.name = name;
    }
    public void addAssignment(Assignment assignment)
    {
        assignments.put(this.name,assignment);
    }

    public Assignment getAssignment(String name)
    {
        return assignments.get(name);
    }

    public String getName()
    {
        return this.name;
    }





}
