package com.team3_3;

import java.io.*;

public abstract class ObjectIO {
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
