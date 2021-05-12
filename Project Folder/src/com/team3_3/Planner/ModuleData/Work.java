package com.team3_3.Planner.ModuleData;

import com.team3_3.UI.Main;
import javafx.application.HostServices;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.awt.*;
import java.io.*;

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
public class Work implements Serializable
{
    public final transient int SSN = 1;
    private String name;
    private int weighting;
    private File file;
    private String note;
    private transient Button button;
    private transient ProgressIndicator progressIndicator;

    public Work (String name,String note, int weighting, File file) throws Semester.ProgressOver100Exception
    {
        this.name = name;
        this.note = note;
        if (weighting > 100)
        {
            throw new Semester.ProgressOver100Exception(weighting);
        }
        this.weighting = weighting;
        this.file = file;
        this.button = new Button();
        buttonSetup();
        this.progressIndicator = new ProgressIndicator((double)weighting/100);
    }
    private void buttonSetup(){
        button.setText("Open File");
        button.setOnAction(actionEvent -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    // overridden/serializable methods
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        ois.defaultReadObject();
        progressIndicator = new ProgressIndicator((double)weighting/100);
        button = new Button();
        buttonSetup();
    }

    public File getFile()
    {
        return this.file;
    }

    public int getWeighting()
    {
        return this.weighting;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNote() {
        return note;
    }

    public Button getButton() {
        return button;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public int getSSN() {
        return SSN;
    }

    @Override
    public String toString() {
        return name;
    }
}