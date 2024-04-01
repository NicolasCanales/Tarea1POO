public class SkyController implements Actionable{
   public SkyController (Drone drone) {
      this.drone = drone;
   }

   public void takeAction(float time) {
      ljv = device.getVerticalPos();
      ljh = device.getRotationPos();
      rjv = device.getForwardPos();
      rjh = device.getSidewaysPos();
      drone.setFlySpeed(ljv, rjv, rjh);
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
   private float ljv,ljh,rjv,rjh;
   private InputDevice device;
}

