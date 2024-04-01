import java.io.*;
import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;

public class Stage3Test  {
   static {
      Locale.setDefault(Locale.US);
      t0=System.currentTimeMillis();  // time in ms since app 1970.
   }
   public static long t0;
   public static void main (String[] arg) throws IOException {
      Scanner in = new Scanner(new File(arg[0])); // entrada.csv
      File f = new File(arg[1]); // drone.csv
      FileWriter w = new FileWriter(f);
      BufferedWriter bw = new BufferedWriter(w);
      PrintWriter wr = new PrintWriter(bw);
      System.out.println("tiempo; x; y; h");
      wr.append("tiempo; x; y; h\n");

      float time, nextPrintTime;
            time=nextPrintTime = getCurrentTime();

      Drone drone = new Drone();
      SkyController skyController = new SkyController(drone);
      Joysticks joysticks = new Joysticks(skyController);
      skyController.setInputDevice(joysticks);     
      Operator operator = new Operator(in, joysticks);

      time = (float) (Math.round((time) * 10)/10d);
      ArrayList<Actionable> actionables = new ArrayList<Actionable>();
      actionables.add(operator);
      actionables.add(skyController);
      actionables.add(drone);

      do {
         for( Actionable device: actionables)
            device.takeAction(time);
         if (time >= nextPrintTime){
            System.out.println(time+ "; "+drone);
            wr.append(String.valueOf(time)).append(";").append(String.valueOf(drone)).append("\n");
            nextPrintTime= (float) (Math.round((nextPrintTime+0.5) * 10)/10d);
         }
         sleepFor(0.1f);  // let 0.1 [s] pass to run at real time.
         time=(float) (Math.round(getCurrentTime()* 10)/10d);
      } while (drone.getState()!=State.LANDED);
      System.out.println(time+ "; "+drone); //coordenadas finales
      wr.append(String.valueOf(time)).append("; ").append(String.valueOf(drone)).append("\n");

      Keyboard keyboard = new Keyboard(skyController);
      skyController.setInputDevice(keyboard); // we switch to another input device
      actionables.remove(operator);  // stop reading automatically from file
      actionables.add(keyboard);  // start reading from keyboard
      System.out.println("Get ready to control the drone. Now you are its pilot.");

      do { // wait until the user hits space key (to take-off)
         for( Actionable device: actionables)
            device.takeAction(time);
         sleepFor(0.1f);  // users need to run at real time (not at simulation time)
         time=(float) (Math.round(getCurrentTime()* 10)/10d);
      } while (drone.getState()== State.LANDED);
      
       nextPrintTime = time+0.5f;
      do {  // user flies the drone until the drone lands.
         for( Actionable device: actionables)
            device.takeAction(time);
         if (time >= nextPrintTime){
            System.out.println((float) (Math.round(time* 10)/10d)+ ";\t"+drone + "; move: " );
            wr.append(String.valueOf((float) (Math.round(time* 10)/10d))).append(";").append(String.valueOf(drone)).append("\n");
            nextPrintTime+=0.5;
         }
         sleepFor(0.1f);
         time=getCurrentTime();
      } while (drone.getState()!=State.LANDED);
      
      System.out.println((float) (Math.round(time* 10)/10d)+ "; "+drone + "; move: ");
      wr.append(String.valueOf(time)).append("; ").append(String.valueOf(drone)).append("\n");
      wr.close();
      bw.close();
   }
   public static float getCurrentTime(){  // time since program started in [s]
      return (float)(System.currentTimeMillis()-t0)/1000.0f;
   }
   public static void sleepFor(float time) { // to let user react
      try {
         Thread.sleep((int)(time*1000));
      } catch (InterruptedException e){
      }
   }
}