import java.io.*;
import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;

public class AcrobaticDrones  {
   static {
      Locale.setDefault(Locale.US);
      t0=System.currentTimeMillis();  // time in ms since app 1970.
   }
   public static long t0;
   public static void main (String[] arg) throws IOException {
      Scanner in = new Scanner(new File(arg[0])); //  "entrada.csv"
      File f = new File(arg[1]); //exit file for drone1
      File f2 = new File(arg[2]); //exit file for drone2
      FileWriter w = new FileWriter(f);   //create a filewriter for each file
      FileWriter w2 = new FileWriter(f2);
      BufferedWriter bw = new BufferedWriter(w);  //create a buffer for each file
      BufferedWriter bw2 = new BufferedWriter(w2);
      PrintWriter wr = new PrintWriter(bw);   //create a PrintWriter for each file
      PrintWriter wr2 = new PrintWriter(bw2);
      wr.append("tiempo" + "; " + "x; y; h\n");
      wr2.append("tiempo" + "; " + "x; y; h\n");

      float time, nextPrintTime;
      time=nextPrintTime = getCurrentTime();
      time = (float) (Math.round((time) * 10)/10d); // round the time
      Drone drone1 = new Drone(); //create drones
      Drone drone2 = new Drone();
      SkyController skyController = new SkyController(drone1); //create Skycontrollers
      SkyController skyController2 = new SkyController(drone2);
      Joysticks joysticks = new Joysticks(skyController);  // assigned skycontroller a joystick
      skyController.setInputDevice(joysticks);   //set joysticks to InputDevice for first skycontroller
      Operator operator = new Operator(in, joysticks);
      Keyboard keyboard = new Keyboard(skyController2);
      skyController2.setInputDevice(keyboard); //set keyboard to InputDevice for skycontroller2

      ArrayList<Actionable> actionables = new ArrayList<Actionable>();
      actionables.add(operator);
      actionables.add(skyController);
      actionables.add(skyController2);
      actionables.add(drone1);
      actionables.add(drone2);
      actionables.add(keyboard);

      System.out.println("Get ready to control the drone. Now you are its pilot.");
      do {
         for( Actionable device: actionables)  // execute all takeAction
            device.takeAction(time);
         if (time >= nextPrintTime){
            System.out.println("dron1: "+time+ ",\t"+drone1);
            System.out.println("dron2: "+ time+ ";\t"+drone2 + "; move: " );
            wr.append(String.valueOf(time)).append(";").append(String.valueOf(drone1)).append("\n"); // write in the file
            wr2.append(String.valueOf(time)).append(";").append(String.valueOf(drone2)).append("\n");
            System.out.print("\n");
            nextPrintTime= (float) (Math.round((nextPrintTime+0.5) * 10)/10d);
         }
         sleepFor(0.1f);  // let 0.1 [s] pass to run at real time.
         time=(float) (Math.round(getCurrentTime()* 10)/10d);
      } while (drone1.getState()!=State.LANDED || drone2.getState()!=State.LANDED);

      System.out.println("dron1: "+time+ "; "+drone1); //coordenadas finales dron1
      System.out.println("dron2: "+ time+ "; "+drone2+ "; move: " ); // coordenadas finales dron2
      wr.append(String.valueOf(time)).append(";").append(String.valueOf(drone1)).append("\n");
      wr2.append(String.valueOf(time)).append(";").append(String.valueOf(drone2)).append("\n");
      wr2.close();
      bw2.close(); //close file Writers
      wr.close();
      bw.close();


   }
   public static float getCurrentTime(){  // time since program started in [s]
      return (float)(System.currentTimeMillis()-t0)/1000.0f;
   }
   public static void sleepFor(float time) { // to let user react
      try {
         Thread.sleep((int)(time*1000));
      } catch (InterruptedException ignored){
      }
   }
}