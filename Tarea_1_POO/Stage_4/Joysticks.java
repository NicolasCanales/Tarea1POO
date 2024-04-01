public class Joysticks extends InputDevice {
   public Joysticks (SkyController controller) {
      super(controller);
      rStick = new Joystick();
      lStick = new Joystick();
      skyControlleraux=controller;
   }
   public float getForwardPos() {

      return rStick.getVerPos();

   }
   public float getSidewaysPos(){

      return rStick.getHorPos();

   }
   public float getVerticalPos(){

      return lStick.getVerPos();

   }
   public float getRotationPos(){

      return lStick.getHorPos();

   }

   public Joystick getLjoy() {
      return lStick;
   }

   public Joystick getRjoy() {
      return rStick;
   }

   public void accionarButton(){
      pushTakeOff_Land();
   }  // use to landing

   public State getState() {

      return skyControlleraux.getState();
   }

   private Joystick lStick, rStick;
   private SkyController skyControlleraux;
}