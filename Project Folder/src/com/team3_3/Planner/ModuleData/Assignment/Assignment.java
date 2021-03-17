package com.team3_3.Planner.ModuleData.Assignment;

import com.team3_3.Planner.ModuleData.Milestone;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * <h2>References: </>
 *  -Official JavaDoc help page @link https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html
 */
public abstract class Assignment
{
    private String name;
    private Date deadline;
    private int weighting;
    private HashMap<String, Milestone> milestones;

    Assignment(String name, String deadline, int weighting) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.name = name;
        this.deadline = sdf.parse(deadline.replace(".", "/"));
        this.weighting = weighting;
    }

    public String getName()
    {
        return this.name;
    }

    public int getWeighting()
    {
        return this.weighting;
    }
}
