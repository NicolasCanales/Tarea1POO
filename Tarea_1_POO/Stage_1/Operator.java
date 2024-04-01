import java.io.File;
import java.util.Scanner;
import java.io.*;

public class Operator {
   public Operator (Scanner in, Joystick l_Joy, Joystick r_Joy) throws IOException {
      inFile = in;
      l_Joystick = l_Joy;
      r_Joystick = r_Joy;
      inFile.nextLine(); // skip description line
      t = inFile.nextFloat();

   }
   public boolean takeAction(float time) throws IOException {

      float tiempo = (float) (Math.round(time * 100)/100d); // redondeamos el tiempo

      if (tiempo >= t) {
         t1 = inFile.nextFloat();          // setea jih
         t2 = inFile.nextFloat();          // setea jiv
         t3 = inFile.nextFloat();          // setea jdh
         t4 = inFile.nextFloat();          // setea jdv
         if (inFile.hasNextFloat()) {
            t = inFile.nextFloat();        // setea t
         }
         else {
            System.out.println(tiempo + ", " + t1 + ", " + t2 + ", " + t3 + ", " + t4);
            return false;
         }
      }
      System.out.println(tiempo + ", " + t1 + ", " + t2 + ", " + t3 + ", " + t4);
      return true;

   }

   private float t,t1,t2,t3,t4;
   private Scanner inFile;
   private Joystick l_Joystick, r_Joystick;
}