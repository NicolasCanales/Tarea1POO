public abstract class InputDevice {
   public InputDevice (SkyController controller) {
      button=State.TAKE_OFF;
      this.controller=controller;
      drone = controller.getDrone();
   }
   public abstract float getForwardPos();
   public abstract float getSidewaysPos();
   public abstract float getVerticalPos();
   public abstract float getRotationPos();

   public void pushTakeOff_Land() { 	// OK
      button = drone.getState();
      drone.takeOff();
      drone.land();
   }
   private State button;
   private SkyController controller;
   private Drone drone;
}