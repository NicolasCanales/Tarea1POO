public class Drone implements Actionable{
   public Drone() {
      x = 0; // posicion x
      y = 0; // posicion y
      h = 0; // height h
      direction = (float) Math.PI / 2; // initial degree
      fSpeed = 0;    // forward speed
      vSpeed = 0;   // vertical Speed
      sSpeed = 0;   //	slide speed
      rSpeed = 0;   // rotational speed
      state = State.LANDED; // initial state
   }

   static {
      MAX_F_SPEED = MAX_S_SPEED = 5; // [m/s]
      MAX_V_SPEED = 2;    // [m/s]
      MAX_R_SPEED = (float) (0.25 * Math.PI / 2); // [rad/s] 0.25 * Math.PI / 2
      TAKEOFF_LANDING_SPEED = 1; // [m/s]
   }

   public void takeAction(float t) {
      float delta_t = t-time;
         switch (state) {

            case TAKING_OFF:  //drone moves only upwards in this stage ("despegando")

               setFlySpeed( TAKEOFF_LANDING_SPEED, 0, 0); // set vertical speed for taking off
               if (delta_t >= 0.1) {
                  h = (float) (Math.round((h + (delta_t * vSpeed)) *1000)/1000d);   // actualized height
               }
               if (h >= 1.0) {
                  state = State.TAKE_OFF;
                  setFlySpeed( 0, 0, 0);  // don´t move
               }
               break;

            case TAKE_OFF:
               state = State.FLYING; // (volando)
               break;

            case FLYING: // all speeds are "0" before this state

               if (delta_t >= 0.1) {
                  if (h>1) { h = h + (delta_t * vSpeed); }
                  else{
                     if (vSpeed <= 0){
                        h = 1;
                        vSpeed=0;
                     }
                     else { h = h + (delta_t * vSpeed);}
                  }
                  direction = direction + (delta_t * rSpeed);
               }
               if (direction >= 2* Math.PI  || direction <= -2* Math.PI) { // direction e [-2pi,2pi]
                  direction = 0;
               }
               // actualized coordenades
               x = (float) (Math.round((x + (delta_t * ((fSpeed * Math.cos(direction)) + (sSpeed * Math.sin(direction))))) *1000)/1000d);
               y = (float) (Math.round((y + (delta_t * ((sSpeed * Math.cos(direction)) + (fSpeed * Math.sin(direction))))) *1000)/1000d);
               h = (float) (Math.round((h + (delta_t * vSpeed)) *1000)/1000d);

               break;


            case LANDING:
               setFlySpeed(-TAKEOFF_LANDING_SPEED, 0, 0);
               if (delta_t >= 0.1) {
                  h = (float) (Math.round((h + (delta_t * vSpeed)) *1000)/1000d);
               }
               if (h <= 0.005) {
                  setFlySpeed(0, 0, 0); // Landed and off all
                  h=0; // set height to 0
                  state = State.LANDED;
               }
               break;

            default:
               break;
         }
         time = t;

   }

   public void setRotationSpeed(float percentage) {
      rSpeed = (MAX_R_SPEED * percentage);
   }

   public void setFlySpeed(float verPer, float forwPer, float sidePer) {
      vSpeed = verPer * MAX_V_SPEED;   // porportional to movement of joystick
      fSpeed = forwPer * MAX_F_SPEED;
      sSpeed = sidePer * MAX_S_SPEED;
   }

   public String toString() {
      return x + ";" + y + ";" + h;
   }

   public void takeOff() {
      if (state == State.LANDED)
         state = State.TAKING_OFF; //
   }

   public void land() {
      if (state == State.FLYING) {
         state = State.LANDING;
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