package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.ModuleData.Assignment.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

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

public class Semester
{
    // instance variables
    private String semId;
    private HashMap<String, Module> modules = new HashMap<>();
    private Date startDate;
    private Date endDate;

    // constructor
    public Semester (String semId, String startDate, String endDate) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.semId = semId;
        this.startDate = returnDate(startDate, "00:00:00");
        this.endDate = returnDate(endDate, "23:59:59");
    }

    // methods
    static Semester newSemester(String filepath) throws FileNotFoundException, ParseException
    {
        File semFile = new File (filepath);
        Scanner semScanner = new Scanner (semFile);

        String semid = null;
        String start = null;
        String end = null;

        String lastModule = null;

        HashSet<String> modules = new HashSet<>(); // holds all unique modules
        HashSet<String> assignments = new HashSet<>(); // holds all unique assignments - strings

        while (semScanner.hasNextLine())
        {
            String[] values = semScanner.nextLine().split(": ");
            switch (values[0])
            {
                case "SEMID" -> semid = values[1];
                case "START" -> start = values[1];
                case "END" -> end = values[1];
                case "MODULE" ->
                {
                    lastModule = values[1];
                    modules.add(values[1]);
                }
                case "COURSEWORK", "EXAM" -> assignments.add(lastModule + " // " + values[0] + " // " + values[1]);
            }
        }

        semScanner.close();

        Semester semester = new Semester(semid, start, end); // creates semester object

        for (String m : modules) // adds in modules
        {
            Module d = new Module(m);
            semester.addModule(d);
        }

        for (String test : assignments)
        {
            String[] values = test.split(" // ");

            if (values[1].equals("COURSEWORK"))
            {
                Coursework c = new Coursework(values[2], values[3], Integer.parseInt(values[4]), values[5]);
                semester.getModule(values[0]).addAssignment(c);
            }
            else if (values[1].equals("EXAM"))
            {
                Exam e = new Exam(values[2], values[3], Integer.parseInt(values[4]), values[5], values[6], Integer.parseInt(values[7]), values[8]);
                semester.getModule(values[0]).addAssignment(e);
            }
        }

        return semester;
    }

    public void addModule(Module module)
    {
        modules.put(module.getName(),module);
    }

    public Module getModule (String name)
    {
        return modules.get(name);
    }

    public String getSemId()
    {
        return this.semId;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public Date getEndDate()
    {
        return this.endDate;
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

    // test harness
    public static void main(String[] args) throws FileNotFoundException, ParseException
    {
        Semester n = newSemester("examplehub.txt");
        System.out.println(n.getSemId() + " | " + n.getStartDate() + " | " + n.getEndDate());
    }
}
