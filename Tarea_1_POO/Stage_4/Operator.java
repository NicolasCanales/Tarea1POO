import java.util.Scanner;
import java.io.*;

public class Operator implements Actionable{
   public Operator (Scanner in, Joysticks joysticks) throws IOException {
      inFile = in;
      this.joyAux = joysticks;
      l_Joystick = joyAux.getLjoy();
      r_Joystick = joyAux.getRjoy();
      inFile.nextLine(); // skip description line
      accionar = inFile.nextLine(); // read a string
      notscape= true;
      if(accionar.equals("button")){  // action for taking off
         joyAux.accionarButton();
      }
      t = inFile.nextFloat();
   }
   public void takeAction(float time) {

         if (time >= t && notscape==true) {
            estado = joyAux.getState();
            t1 = inFile.nextFloat(); //get jih
            t2 = inFile.nextFloat(); //get jiv
            t3 = inFile.nextFloat(); //get jdh
            t4 = inFile.nextFloat(); //get jdv

            if (estado != State.LANDING || estado != State.TAKING_OFF) {
               l_Joystick.setHorPos(t1); //set all positions of joystick
               l_Joystick.setVerPos(t2);
               r_Joystick.setHorPos(t3);
               r_Joystick.setVerPos(t4);
            }
            if (inFile.hasNextFloat()) {   // get and set t
               t = inFile.nextFloat();
            } else {
               joyAux.accionarButton();
               System.out.println("Drone are Landing");
               notscape=false;
            }
         }
   }




   private float t,t1,t2,t3,t4;
   private String accionar; // read the string for push button of landing or teaking off
   private State estado;
   private Scanner inFile;
   private Joystick l_Joystick, r_Joystick;
   private Joysticks joyAux;
   private boolean notscape; // use to stopped reading text
}