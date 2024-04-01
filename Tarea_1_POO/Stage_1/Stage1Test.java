import java.io.*;
import java.util.Scanner;
import java.util.Locale;

public class Stage1Test  {
   public static void main (String[] arg) throws IOException {
      Locale.setDefault(Locale.US);  // to read number in US format, like 1.5 (not like 1,5)
      Scanner in = new Scanner(new File(arg[0]));
      System.out.println("t, jih, jiv, jdh, jdv");

      float time = 0.0f;
      Joystick l_Joy = new Joystick();
      Joystick r_Joy = new Joystick();
      Operator operator = new Operator(in, l_Joy, r_Joy);

      while (operator.takeAction(time)){
         time += 0.100000;
      }
   }
}