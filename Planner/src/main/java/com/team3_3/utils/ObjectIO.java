package com.team3_3.utils;

import java.io.*;
/**
 * <h1>ObjectIO</h1>
 *<p>A static util class used for loading a saving objects.</p>
 * @author  James Sergeant
 * @version 1.0
 * @since   01/03/2021
 *
 * <h2>Change Log</h2>
 *   - 01/03/2021: saveObject added - JS
 *   - 01/03/2021: loadObject added - JS
 */
public abstract class ObjectIO {
    /**
     * Used to save objects to files.
     * @param location String: Where the file should be stored.
     * @param object Object: The object you wish to save.
     * @return Boolean: True if the object has been saved.
     */
    public static <A> boolean saveObject(String location,A object){
        try {
            File f = new File(location);
            if(!f.exists()){
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(location);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return true;
        }catch(Exception e){
            System.out.println("ERROR SAVING OBJECT\n"+e);
            return false;
        }
    }

    /**
     * Reads in the object from the file.
     * @param location String: the files location.
     * @return Object: the object read in.
     */
    public static <A> A readObject(String location){
        A e = null;
        try {
            FileInputStream fileIn = new FileInputStream(location);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (A) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return e;
    }
}
