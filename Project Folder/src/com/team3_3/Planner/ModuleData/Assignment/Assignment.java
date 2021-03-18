package com.team3_3.Planner.ModuleData.Assignment;

import com.team3_3.Planner.ModuleData.Milestone;

import java.sql.Time;
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
    // instance variables
    private String name;
    private Date date;
    private int weighting; // out of 100%
    private HashMap<String, Milestone> milestones = new HashMap<>();

    // constructor
    public Assignment(String name, Date date, int weighting) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.name = name;
        this.weighting = weighting;
        this.date = date;
    }

    // methods
    public void addMilestone(Milestone milestone)
    {
        milestones.put(milestone.getName(),milestone);
    }

    public Milestone getMilestone(String name)
    {
        return milestones.get(name);
    }

    // getters
    public String getName()
    {
        return this.name;
    }

    public Date getDate()
    {
        return this.date;
    }

    public int getWeighting()
    {
        return this.weighting;
    }

    // static methods
    static Date returnDate(String date, String time) throws ParseException
    {
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date += " " + returnTime(time).toString();

        return input.parse(date.replace(".", "/"));
    }

    static Time returnTime(String time)
    {
        String[] words = time.replace(".", ":").split(":");
        int timeMilli = 0;

        for (int i = 1; i<=3; i++)
        {
            if (words.length >= i)
            {
                switch (i)
                {
                    case 1 -> timeMilli = ((Integer.parseInt(words[0]) - 1) * 3600000);
                    case 2 -> timeMilli += ((Integer.parseInt(words[1]) * 60000));
                    case 3 -> timeMilli += ((Integer.parseInt(words[2]) * 1000));
                }
            }
        }

        return new Time(timeMilli);
    }
}
