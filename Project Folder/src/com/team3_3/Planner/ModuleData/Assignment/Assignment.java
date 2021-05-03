package com.team3_3.Planner.ModuleData.Assignment;

import com.team3_3.Planner.ModuleData.Milestone;
import com.team3_3.Planner.ModuleData.Semester;
import com.team3_3.Planner.ModuleData.Updatable;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
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
public abstract class Assignment implements Serializable, Updatable
{
    // serializable
    public final transient int SSN = 1;

    // instance variables
    private String name;
    private String module;
    private Date date;
    private int weighting; // out of 100%
    private HashMap<String, Milestone> milestones = new HashMap<>();
    private double progress = 0;
    private boolean finished = false; // assignment not finished by default
    private transient ProgressBar progressBar;

    // constructor
    public Assignment(String name,String module, Date date, int weighting) throws Semester.AssignmentWeightingOutOfBoundsException
    {
        this.name = name;
        this.date = date;
        this.module = module;
        this.progressBar = new ProgressBar(progress);
        if (weighting > 100)
        {
            throw new Semester.AssignmentWeightingOutOfBoundsException(weighting);
        }
        this.weighting = weighting;
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

    public String getModule() {
        return module;
    }

    public boolean getFinished()
    {
        return finished;
    }

    public ProgressBar getProgressBar()
    {
        return progressBar;
    }

    // setters
    public void setFinished() // works like a lightswitch - either finished or not finished
    {
        this.finished = !finished;
    }

    // overridden/serializable methods
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        ois.defaultReadObject();
        progressBar = new ProgressBar(progress);
    }

    @Override
    public void update()
    {
        this.progress = getAssignmentCompletion();
        this.progressBar.setProgress(progress);
    }

    // methods
    public void addMilestone(Milestone milestone) throws MilestoneWeightingOutOfBoundsException
    {
        int cumulative = 0;
        for (Milestone ms : milestones.values())
        {
            cumulative += ms.getWeighting();
            System.out.println(cumulative);
        }

        if (cumulative + milestone.getWeighting() > 100)
        {
            throw new MilestoneWeightingOutOfBoundsException(cumulative + milestone.getWeighting());
        }

        milestones.put(milestone.getName(),milestone);
    }

    public Milestone getMilestone(String name)
    {
        return milestones.get(name);
    }

    public double getAssignmentCompletion()
    {
        double progress = 0; // starts off with 0% completion

        for (Milestone a : milestones.values())
        {
            if (a.getFinished()) // if the assignment is finished
            {
                progress += (a.getWeighting()*0.01);
            }
        }

        return progress;
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
                    case 1 -> timeMilli = ((Integer.parseInt(words[0]) - 1) * 3600000);
                    case 2 -> timeMilli += ((Integer.parseInt(words[1]) * 60000));
                    case 3 -> timeMilli += ((Integer.parseInt(words[2]) * 1000));
                }
            }
        }

        return new Time(timeMilli);
    }

    public static class MilestoneWeightingOutOfBoundsException extends Exception
    {
        public MilestoneWeightingOutOfBoundsException (int weighting)
        {
            super("Semester: milestone weighting (" + weighting + ") is too large in comparison to other milestones.");
        }
    }

    // test harness
    public static void main(String[] args) throws ParseException, MilestoneWeightingOutOfBoundsException, Semester.AssignmentWeightingOutOfBoundsException {
        // Coursework coursework = new Coursework("Coursework", "PROGRAMMING 3", "11/2/2020", 10, "1:30");
        Exam exam = new Exam("Exam", "PROGRAMMING 3", "12/2/2020", 50, "1:30", "2:30", 60, "Exam hall");
        Milestone milestone1 = new Milestone("Milestone 1", 50);
        // milestone1.setFinished();
        Milestone milestone2 = new Milestone("Milestone 2", 50);
        // milestone2.setFinished();
        exam.addMilestone(milestone1);
        exam.addMilestone(milestone2);

        System.out.println(exam.getAssignmentCompletion());
    }
}
