public class Drone {
   public Drone() {
      x = 0; // posicion x
      y = 0; // posicion y
      h = 0; // altura h
      direction = (float) Math.PI / 2; // angulo inicial
      fSpeed = 0;    // forward speed
      vSpeed = 0;   // vertical Speed
      sSpeed = 0;   //	slide speed
      rSpeed = 0;   // velocidad rotacional
      state = State.LANDED; // estado inicial aterrizado
   }

   static {
      MAX_F_SPEED = MAX_S_SPEED = 5; // [m/s]
      MAX_V_SPEED = 2;    // [m/s]
      MAX_R_SPEED = (float) (0.25 * Math.PI / 2); // [rad/s]
      TAKEOFF_LANDING_SPEED = 1; // [m/s]
   }

   public void takeAction(float t) {
      float delta_t = t-time;
         switch (state) {
            case TAKING_OFF:  //drone moves only upwards in this stage "despegando"
               setFlySpeed( TAKEOFF_LANDING_SPEED, 0, 0);
               if (delta_t >= 0.09) {
                  h = h + (delta_t * vSpeed);
               }
               if (h >= 1.0) {
                  state = State.TAKE_OFF; // despegado
                  setFlySpeed( 0, 0, 0);
               }
               break;

            case TAKE_OFF: // despegado
               state = State.FLYING; // volando

            case FLYING: //Caso en el que el drone esta en vuelo

               if (delta_t >= 0.1) {      // En esta parte no permitimos que el drone baje
                  if (h>1) { h = h + (delta_t * vSpeed); } // menos de un metro
                  else{                      // aunque el joystick se lo pida
                     if (vSpeed <= 0){       // esto con el sentido de no tener h negativas ni
                        h = 1;               // que el drone toque el suelo
                        vSpeed=0;
                     }
                     else { h = h + (delta_t * vSpeed);}
                  }
                  direction = direction + (delta_t * rSpeed);
               }
                if (direction >= 2* Math.PI  || direction <= -2* Math.PI) {
                    direction = 0;
                }

               x = (float) (Math.round((x + (delta_t * ((fSpeed * Math.cos(direction)) + (sSpeed * Math.sin(direction))))) *10)/10d); // Movimiento en X
               y = (float) (Math.round((y + (delta_t * ((sSpeed * Math.cos(direction)) + (fSpeed * Math.sin(direction))))) *10)/10d); // Movimiento en y
               h = (float) (Math.round((h + delta_t * (vSpeed)) *10)/10d); // Movimiento en h
               break;

            case LANDING: //aterrizando
               setFlySpeed(-TAKEOFF_LANDING_SPEED, 0, 0);
               if (delta_t >= 0.09) {
                  h = (float) (Math.round((h + (delta_t * vSpeed)) *10)/10d);
               }
               if (h <= 0.005) {
                  setFlySpeed(0, 0, 0); // aterriza y apaga toro
                  h=0;
                  state = State.LANDED; // aterrizado
               }
               break;
         }
         time = t;
   }

   public void setRotationSpeed(float percentage) {
      rSpeed = (MAX_R_SPEED * percentage);
   }

   public void setFlySpeed(float verPer, float forwPer, float sidePer) {
      vSpeed = verPer * MAX_V_SPEED;
      fSpeed = forwPer * MAX_F_SPEED;
      sSpeed = sidePer * MAX_S_SPEED;
   }

   public float getHeight() {
      return h;
   }

   public String toString() {
      return x + "; " + y + "; " + h;
   }

   public void takeOff() {
      if (state == State.LANDED)
         state = State.TAKING_OFF; // cambia "DESPEGANDO"
   }

   public void land() {
      if (state == State.FLYING) {// Si esta "VOLANDO"
         state = State.LANDING; // cambia a "ATERRIZANDO"
      }
   }

   public State getState() {
      return state;
   }

   private State state;
   private float time;
   private float fSpeed, vSpeed, sSpeed, rSpeed;
   private float direction; // angle
   private float x, y, h;
   private static float MAX_F_SPEED; // velocidad hacia adelante (forward Speed)
   private static float MAX_V_SPEED; // Velocidad vertical
   private static float MAX_S_SPEED;  // Velocidad lateral (slide speed)
   private static float MAX_R_SPEED;  // Velocidad Rotacional
   private static float TAKEOFF_LANDING_SPEED;
}