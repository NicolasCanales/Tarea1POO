public class SkyController implements Actionable{
   public SkyController (Drone drone) {
      this.drone = drone;
   }

   public void takeAction(float time) {
      button = drone.getState();
      ljv = device.getVerticalPos();  		// Pos Vertical Joystick Izq
      ljh = device.getRotationPos();			// Pos horizontal Joystick Izq
      rjv = device.getForwardPos();			// Pos Vertical Joystick Der
      rjh = device.getSidewaysPos();			// Pos horizontal Joystick Der
      drone.setFlySpeed(ljv, rjv, rjh); // setFlySpeed(float verPer, float forwPer, float sidePer)
      drone.setRotationSpeed(ljh);

   }
   public Drone getDrone() {
      return drone;
   }
   public State getState() {
      return drone.getState();
   }

   public void setInputDevice(InputDevice Device){
      device = Device;
   }

   private Drone drone;
   private State button;
   private float ljv,ljh,rjv,rjh;
   private InputDevice device;
}

