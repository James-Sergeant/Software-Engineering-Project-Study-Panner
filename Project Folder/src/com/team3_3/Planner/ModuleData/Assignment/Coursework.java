package com.team3_3.Planner.ModuleData.Assignment;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * <h2>Refrences: </>
 *  -Offical JavaDoc help page @link https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html
 */
public class Coursework extends Assignment implements Serializable
{
    public final transient int SSN = 1;
    // instance variables
    private Time dueTime; // 24-hour clock

    // constructor
    public Coursework (String name,String module, String date, int weighting,  String dueTime) throws ParseException
    {
        super(name,module, returnDate(date, dueTime), weighting);
        this.dueTime = returnTime(dueTime);
    }

    // getters
    public Time getDueTime()
    {
        return this.dueTime;
    }

    // test harness
    public static void main(String[] args) throws ParseException
    {
        Coursework c = new Coursework("VoIP", "TEST","19/03/2021", 50, "11:30");
        System.out.println(c.getName());
        System.out.println(c.getWeighting());
        System.out.println(c.getDueTime());
    }
}