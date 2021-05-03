package com.team3_3.Planner.ModuleData.Assignment;

import com.team3_3.Planner.ModuleData.Semester;

import java.io.Serializable;
import java.text.ParseException;
import java.sql.Time;
import java.util.Date;

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
public class Exam extends Assignment implements Serializable
{
    public final transient int SSN = 1;
    // instance variables
    private Time startTime;
    private Time endTime;
    private int duration; // in hours
    private String location;

    // constructor
    public Exam (String name, String module, String date, int weighting, String startTime, String endTime, int duration, String location) throws ParseException, Semester.AssignmentWeightingOutOfBoundsException
    {
        super(name,module, returnDate(date, startTime), weighting);
        this.startTime = returnTime(startTime);
        this.endTime = returnTime(endTime);
        this.duration = duration;
        this.location = location;
    }

    // getters
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
    public static void main(String[] args) throws ParseException, Semester.AssignmentWeightingOutOfBoundsException
    {
        Exam e = new Exam("DS&A","TEST" ,"25/3/2021", 30, "11:30", "13:30",  2, "Online");
        System.out.println(e.getName());
        System.out.println(e.getWeighting());
        System.out.println(e.getStartTime());
        System.out.println(e.getEndTime());
        System.out.println(e.getDate());
        System.out.println(e.getLocation());
    }
}
