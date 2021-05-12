package com.team3_3.Planner.ModuleData;

import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

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

public class Task implements Serializable
{
    public final transient int SSN = 1;
    private String name;
    private int weighting;
    private LocalDate startDate;
    private LocalDate endDate;
    private HashSet<Work> work = new HashSet<>();
    private double progress = 0;
    private transient ProgressBar progressBar;

    public Task (String name, int weighting, LocalDate startDate, LocalDate endDate) throws Semester.ProgressOver100Exception, ParseException
    {
        this.name = name;
        if (weighting > 100)
        {
            throw new Semester.ProgressOver100Exception(weighting);
        }
        this.weighting = weighting;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressBar = new ProgressBar(0);
    }
    // overridden/serializable methods
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        ois.defaultReadObject();
        progressBar = new ProgressBar((100 - (double)getMaximum())/100);
    }

    public void updateProgress(){
        progress = (100 - (double)getMaximum());
        progressBar.setProgress((100 - (double)getMaximum())/100);
    }

    public void addWork (Work work) throws Semester.NameAlreadyExistsException, Semester.ProgressOver100Exception
    {
        int cumulative = 0;
        for (Work w : this.work) // checking work doesn't have same name
        {
            cumulative += w.getWeighting();
            if (work.getName().equals(w.getName()))
            {
                throw new Semester.NameAlreadyExistsException(work.getName());
            }
        }

        if (cumulative + work.getWeighting() > 100)
        {
            throw new Semester.ProgressOver100Exception(work.getWeighting());
        }

        this.work.add(work);
        User.saveUser(Login.getLoggedInUser());
    }

    public int getWeighting()
    {
        return this.weighting;
    }

    public String getName()
    {
        return this.name;
    }

    public LocalDate getStartDate()
    {
        return this.startDate;
    }

    public LocalDate getEndDate()
    {
        return this.endDate;
    }

    public boolean getFinished() // returns if task is finished
    {
        if (getMaximum() == 0)
        {
            return true; // all work is finished
        }
        return false; // work still needs to be completed
    }

    public int getMaximum()
    {
        int cumulative = 0;
        for (Work w : work)
        {
            cumulative += w.getWeighting();
        }

        int maximum = 100 - cumulative;
        if (maximum < 0)
        {
            maximum = 0;
        }

        return maximum;
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
    @Override
    public String toString() {
        return name;
    }

    public HashSet<Work> getWork() {
        return work;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public double getProgress() {
        return progress;
    }
}