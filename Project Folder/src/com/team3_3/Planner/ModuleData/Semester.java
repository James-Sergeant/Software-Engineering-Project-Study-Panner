package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.ModuleData.Assignment.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
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
 * @author  Max James
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

public class Semester implements Serializable
{
    // instance variables
    public final transient int SSN = 1;
    private String semId;
    private HashMap<String, Module> modules = new HashMap<>();
    private Date startDate;
    private Date endDate;

    // constructor
    private Semester (String semId, String startDate, String endDate) throws ParseException
    {
        this.semId = semId;
        this.startDate = returnDate(startDate, "00:00:00");
        this.endDate = returnDate(endDate, "23:59:59");
    }

    // getters
    public Date getStartDate()
    {
        return this.startDate;
    }

    public Date getEndDate()
    {
        return this.endDate;
    }

    // methods
    public static Semester newSemester(String filepath) throws FileNotFoundException, ParseException, DateOutOfBoundsException, ProgressOver100Exception {
        File semFile = new File (filepath);
        Scanner semScanner = new Scanner (semFile);

        String semid = null;
        String start = null;
        String end = null;

        String lastModule = null;

        HashSet<String> modules = new HashSet<>(); // holds all unique modules - strings
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

        HashMap<String, Integer> weightings = new HashMap<>(); // holds weightings for each module

        for (String m : modules) // adds in modules
        {
            weightings.put(m, 0);
            Module d = new Module(m);
            semester.addModule(d);
        }

        for (String test : assignments)
        {
            String[] values = test.split(" // ");

            if (Integer.parseInt(values[4].trim()) + weightings.get(values[0]) > 100)
            {
                throw new ProgressOver100Exception(Integer.parseInt(values[4].trim()) + weightings.get(values[0]));
            }

            if (values[1].equals("COURSEWORK"))
            {
                if (returnDate(values[3], values[5]).before(semester.endDate)&&(semester.startDate.before(returnDate(values[3], values[5]))))
                {
                    weightings.put(values[0], weightings.get(values[0]) + Integer.parseInt(values[4].trim()));
                    Coursework c = new Coursework(values[2].trim(),semester.getModule(values[0]).getName(), values[3].trim(), Integer.parseInt(values[4].trim()), values[5].trim());
                    semester.getModule(values[0]).addAssignment(c);
                }
                else
                {
                    throw new DateOutOfBoundsException(values[3]);
                }
            }
            else if (values[1].equals("EXAM"))
            {
                if (returnDate(values[3], values[5]).before(semester.endDate)&&(semester.startDate.before(returnDate(values[3], values[5]))))
                {
                    weightings.put(values[0], weightings.get(values[0]) + Integer.parseInt(values[4].trim()));
                    Exam e = new Exam(values[2].trim(),semester.getModule(values[0]).getName(), values[3].trim(), Integer.parseInt(values[4].trim()), values[5].trim(), values[6].trim(), Integer.parseInt(values[7].trim()), values[8].trim());
                    semester.getModule(values[0]).addAssignment(e);
                }
                else
                {
                    throw new DateOutOfBoundsException(values[3]);
                }
            }
        }

        return semester;
    }

    public void SemesterInfo() // for testing
    {
        for(String s: modules.keySet())
        {
            System.out.println(s + ":");
            modules.get(s).AssignmentInfo();
        }
    }

    private void addModule(Module module)
    {
        modules.put(module.getName(),module);
    }

    public Module getModule (String name)
    {
        return modules.get(name);
    }

    public HashMap<String,Module> getModules(){return this.modules;}

    public String getSemId()
    {
        return this.semId;
    }

    // static methods
    public static Date returnDate(String date, String time) throws ParseException
    {
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date += " " + returnTime(time).toString();

        return input.parse(date.replace(".", "/"));
    }

    public static Time returnTime(String time)
    {
        String[] words = time.replace(".", ":").split(":");
        int timeMilli = 0;

        for (int i = 1; i<=3; i++)
        {
            if (words.length >= i)
            {
                switch (i)
                {
                    case 1 -> timeMilli = ((Integer.parseInt(words[0].trim()) - 1) * 3600000);
                    case 2 -> timeMilli += ((Integer.parseInt(words[1].trim()) * 60000));
                    case 3 -> timeMilli += ((Integer.parseInt(words[2].trim()) * 1000));
                }
            }
        }

        return new Time(timeMilli);
    }

    // overridden methods
    @Override
    public String toString()
    {
        return "Semester: "+semId+ " Starting: "+startDate+" Ending: "+endDate;
    }

    // exception
    public static class DateOutOfBoundsException extends Exception
    {
        public DateOutOfBoundsException(String date)
        {
            super("Semester: assignment end date (" + date.trim() + ") is after the semester has ended.");
        }
    }

    public static class ProgressOver100Exception extends Exception
    {
        public ProgressOver100Exception (int weighting)
        {
            super("Semester: weighting (" + weighting + ") is too large in comparison to other assignments.");
        }

    }

    public static class NameAlreadyExistsException extends Exception
    {
        public NameAlreadyExistsException (String name)
        {
            super ("Semester: " + name + " already exists.");
        }
    }

    // test harness
    public static void main(String[] args) throws Exception
    {
        Semester n = newSemester("examplehub1.txt");
        System.out.println(n.getSemId() + " | " + n.getStartDate() + " | " + n.getEndDate() + "\n");
        n.SemesterInfo();
    }
}
