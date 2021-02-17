import java.io.*;

public abstract class ObjectIO {
    public static <A> void saveObject(String location,A object){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(location);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        }catch(Exception e){
            System.out.println("ERROR SAVING DECK OBJECT\n"+e);
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
