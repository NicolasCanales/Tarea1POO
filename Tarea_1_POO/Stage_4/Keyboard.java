import java.util.Scanner;
import java.io.InputStream;
import java.io.IOException;

public class Keyboard extends InputDevice implements Actionable {
   public Keyboard(SkyController controller) {
      super(controller);
      rPos=vPos=fPos=sPos=0; // initial position
      in = System.in;
   }
   public float getForwardPos() {
      return fPos;
   }
   public float getSidewaysPos(){
      return sPos;
   }
   public float getVerticalPos(){
      return vPos;
   }
   public float getRotationPos(){
      return rPos;
   }
   public void takeAction(float time) {
      int c;
      try {
         if (in.available()>0) {// there are bytes to read without being blocked
            c=in.read();
            switch (c) {
               case 'w': vPos+=sensibility;
                  if (vPos > 1) vPos=1;
                  break;
               case 'z': vPos-=sensibility;
                  if (vPos < -1 ) vPos=-1;
                  break;
               case 'a': rPos-=sensibility;
                  if (rPos < -1 ) rPos=-1;
                  break;
               case 's': rPos+=sensibility;
                  if (rPos > 1) rPos=1;
                  break;

               case 'i': fPos+=sensibility;
                  if (fPos > 1) fPos=1;
                  break;
               case 'm': fPos-=sensibility;
                  if (fPos < -1 ) fPos=-1;
                  break;
               case 'j': sPos-=sensibility;
                  if (sPos < -1 ) sPos=-1;
                  break;
               case 'k': sPos+=sensibility;
                  if (sPos > 1) sPos=1;
                  break;
               case ' ':
                  pushTakeOff_Land();
                  break;
            }
         }
      } catch ( IOException e ) {
         System.out.println("Input error");
         return;
      }
   }
   private InputStream in;
   private float rPos, vPos, fPos, sPos;
   private static float sensibility=0.2f;
}