import java.io.*;
import java.util.Scanner;
import java.util.Locale;

public class Stage2Test  {
   public static void main (String[] arg) throws IOException {
      Locale.setDefault(Locale.US);  // to read number in US format, like 1.5 (not like 1,5)
      Scanner in = new Scanner(new File(arg[0])); //entrada.csv

      float time = 0.0f;
      Joystick lStick = new Joystick();
      Joystick rStick = new Joystick();
      Drone drone = new Drone();
      SkyController skyController = new SkyController(drone,lStick,rStick);
      Operator operator = new Operator(in,skyController,lStick,rStick);
      System.out.println("time; x; y; h");
      skyController.pushTakeOff_Land(); // to take-off

      while(operator.takeAction(time)) {
         skyController.takeAction(time);
         drone.takeAction(time);
         System.out.println(time+ "; "+skyController.getInfo());
         time = (float) (Math.round((time+ 0.1) * 10)/10d); // aproximamos time a la decima
      }
      skyController.pushTakeOff_Land(); // to land

      while (drone.getHeight()>0) {
         drone.takeAction(time);
         System.out.println(time+ "; "+skyController.getInfo());
         time = (float) (Math.round((time+ 0.1) * 10)/10d);
      }
   }
}


