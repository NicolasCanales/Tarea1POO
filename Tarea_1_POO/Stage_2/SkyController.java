public class SkyController {
   public SkyController (Drone drone,Joystick lStick_aux,Joystick rStick_aux) {
      this.drone = drone;
      lStick = lStick_aux;
      rStick = rStick_aux;
   }

   public String getInfo(){
       return drone.toString(); // x, y,h
   }
   public void pushTakeOff_Land () { //funcion que activa el boton
      drone.takeOff();
      drone.land();
   }
   public void takeAction(float time) {
      ljv = lStick.getVerPos();  // Pos Vertical Joystick Izq
      ljh = lStick.getHorPos();	// Pos horizontal Joystick Izq
      rjv = rStick.getVerPos();	// Pos Vertical Joystick Der
      rjh = rStick.getHorPos();	// Pos horizontal Joystick Der
      drone.setFlySpeed(ljv, rjv, rjh); // setFlySpeed(float verPer, float forwPer, float sidePer)
      drone.setRotationSpeed(ljh); // seteamos velocidad de rotacion

   }
   public State getdronState() {
      return drone.getState();
   }

   private Drone drone;
   private Joystick lStick, rStick;
   private float ljv,ljh,rjv,rjh;
}

