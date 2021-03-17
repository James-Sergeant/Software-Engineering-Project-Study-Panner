package com.team3_3.Planner.ModuleData.Assignment;

import java.text.ParseException;
import java.sql.Time;

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
public class Exam extends Assignment
{
    private Time startTime;
    private Time endTime;
    private int duration; // in minutes
    private String location;

    Exam (String name, String deadline, int weighting, Time startTime, Time endTime, int duration, String location) throws ParseException
    {
        super(name, deadline, weighting);
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.location = location;
    }

    public Time getStartTime()
    {
        return this.startTime;
    }

    public Time getEndTime()
    {
        return this.endTime;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public String getLocation()
    {
        return this.location;
    }

    // test harness
    public static void main(String[] args)
    {
    }
}
