import java.io.File;
import java.util.Scanner;
import java.io.*;

public class Operator {
   public Operator (Scanner in, SkyController skyController, Joystick l_Joy, Joystick r_Joy) throws IOException {
      inFile = in;
      skyControllerAux = skyController;
      l_Joystick = l_Joy;
      r_Joystick = r_Joy;
      inFile.nextLine(); // skip description line
      t = inFile.nextFloat();
   }
   public boolean takeAction(float time) throws IOException {
         if (time - 0.6 >= t) {
            estado = skyControllerAux.getdronState();
            t1 = inFile.nextFloat(); //guardamos parametro jih
            t2 = inFile.nextFloat(); //guardamos parametro jiv
            t3 = inFile.nextFloat(); //guardamos parametro jdh
            t4 = inFile.nextFloat(); //guardamos parametro jdv

            if (estado != State.LANDING || estado != State.TAKING_OFF) {
               l_Joystick.setHorPos(t1); // enviamos parametro al joystick
               l_Joystick.setVerPos(t2);
               r_Joystick.setHorPos(t3);
               r_Joystick.setVerPos(t4);
            }

            if (inFile.hasNextFloat()) { //revisamos si hay otro flotante en el archivo
               t = inFile.nextFloat();
            } else {
               return false;
            }
         }

      return true;

   }
   private float t,t1,t2,t3,t4;
   private State estado;
   private Scanner inFile;
   private SkyController skyControllerAux;
   private Joystick l_Joystick, r_Joystick;
}