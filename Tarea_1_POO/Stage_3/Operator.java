import java.util.Scanner;
import java.io.*;

public class Operator implements Actionable{
   public Operator (Scanner in, Joysticks joysticks) throws IOException {
      inFile = in;
      joyAux = joysticks;
      l_Joystick = joyAux.getLjoy();
      r_Joystick = joyAux.getRjoy();
      inFile.nextLine(); // skip description line
      accionar = inFile.nextLine();
      notscape= true;
      if(accionar.equals("button")){
         joyAux.accionarButton();
      }
      t = inFile.nextFloat();
   }
   public void takeAction(float time) {

         if (time >= t && notscape==true) {
            estado = joyAux.getState();
            t1 = inFile.nextFloat();
            t2 = inFile.nextFloat();
            t3 = inFile.nextFloat();
            t4 = inFile.nextFloat();

            if (estado != State.LANDING || estado != State.TAKING_OFF) {
               l_Joystick.setHorPos(t1);
               l_Joystick.setVerPos(t2);
               r_Joystick.setHorPos(t3);
               r_Joystick.setVerPos(t4);
            }
            if (inFile.hasNextFloat()) {
               t = inFile.nextFloat();
            } else {
               joyAux.accionarButton();
               notscape=false;
            }
         }
   }

   private float t,t1,t2,t3,t4;
   private String accionar;
   private State estado;
   private Scanner inFile;
   private Joystick l_Joystick, r_Joystick;
   private Joysticks joyAux;
   private boolean notscape;
}