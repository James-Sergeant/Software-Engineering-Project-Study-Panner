package com.team3_3.Planner.ModuleData.Assignment;

import com.team3_3.Planner.ModuleData.*;
import com.team3_3.Planner.User.Login;
import com.team3_3.Planner.User.User;
import com.team3_3.Planner.utils.Hash;
import javafx.scene.control.ProgressBar;
import org.jfree.util.Log;

import java.io.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private transient ProgressBar progressBar;
    private String extCode;
    private LocalDate extDate;

    // constructor
    public Assignment(String name,String module, Date date, int weighting) throws Semester.ProgressOver100Exception
    {
        this.name = name;
        this.date = date;
        this.module = module;
        this.progressBar = new ProgressBar(progress);
        if (weighting > 100)
        {
            throw new Semester.ProgressOver100Exception(weighting);
        }
        this.weighting = weighting;
    }

    public String genExtCode(String email, LocalDate date){
        extDate = date;
        String hash = Hash.SHA1(email+Math.random());
        String code = hash.substring(0,16);
        this.extCode = code;
        return code;
    }

    public boolean validCode(String code){
        if (code.equals(extCode)){
            System.out.println(extDate.getYear()+" "+extDate.getMonthValue());
            this.date = new Date((extDate.getYear()-1900),extDate.getMonthValue(),extDate.getDayOfMonth());
            User.saveUser(Login.getLoggedInUser());
            return true;
        }
        return false;
    }

    public void updateProgress(){
        for(Milestone milestone: milestones.values()){
            milestone.updateProgress();
            progress = 0;
            progress += ((double) milestone.getProgress()/100)*((double)milestone.getWeighting()/100);
        }
        progressBar.setProgress(progress);
    }

    public double getProgress() {
        return progress;
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

    public HashMap<String, Milestone> getMilestones()
    {
        return this.milestones;
    }

    public boolean getFinished()
    {
        for (Milestone m : milestones.values())
        {
            if (!m.getFinished()) // if milestone isn't finished
            {
                return false;
            }
        }

        return true; // all milestones finished
    }

    public ProgressBar getProgressBar()
    {
        return progressBar;
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
    public void addMilestone(Milestone milestone) throws Semester.ProgressOver100Exception, Semester.NameAlreadyExistsException
    {
        int cumulative = 0;
        for (Milestone ms : milestones.values())
        {
            cumulative += ms.getWeighting();
            if (milestone.getName().equals(ms.getName()))
            {
                throw new Semester.NameAlreadyExistsException(milestone.getName());
            }
        }

        if (cumulative + milestone.getWeighting() > 100)
        {
            throw new Semester.ProgressOver100Exception(milestone.getWeighting());
        }

        milestones.put(milestone.getName(),milestone);

        User.saveUser(Login.getLoggedInUser());
    }

    public int getMaximum()
    {
        int cumulative = 0;
        for (Milestone ms : milestones.values())
        {
            cumulative += ms.getWeighting();
        }
        int maximum = 100 - cumulative;
        if (maximum < 0)
        {
            maximum = 0;
        }

        return maximum;
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

    @Override
    public String toString() {
        return name + "date: " + date;
    }

    // test harness
    /*
    public static void main(String[] args) throws ParseException, Semester.ProgressOver100Exception, Semester.NameAlreadyExistsException
    {
        Work work = new Work("Work 1", 10, new File("File"));
        //work.setFinished();
        //Task task = new Task("Task 1", 20, "10/10/2020", "11/10/2020");
        //Task task2 = new Task("Task 2", 20, "10/10/2020", "13/10/2020");
        task.addWork(work);
        Milestone milestone = new Milestone("Milestone 1", 30);
        milestone.addTask(task);
        milestone.addTask(task2);
        System.out.println(milestone.getEndDate());
        Exam exam = new Exam("Exam", "PROGRAMMING 3", "12/2/2020", 50, "1:30", "2:30", 60, "Exam hall");
        exam.addMilestone(milestone);
        System.out.println(exam.getAssignmentCompletion());
    }

     */
}
