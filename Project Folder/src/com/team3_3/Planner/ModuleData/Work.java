package com.team3_3.Planner.ModuleData;

import java.io.File;
import java.io.Serializable;

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

    public Work (String name, int weighting, File file) throws ProgressOver100Exception {
        this.name = name;
        if (weighting > 100)
        {
            throw new ProgressOver100Exception("The weighting is over 100");
        }
        this.weighting = weighting;
        this.file = file;
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
}