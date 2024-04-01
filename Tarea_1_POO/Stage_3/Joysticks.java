public class Joysticks extends InputDevice {
   public Joysticks (SkyController controller) {
      super(controller);
      rStick = new Joystick();
      lStick = new Joystick();
      skyControlleraux=controller;
   }
   public float getForwardPos() {
      return rStick.getVerPos();  // devolver joystick derecho vertical
   }
   public float getSidewaysPos(){
      return rStick.getHorPos();  // devolver joystick derecho horizontal
   }
   public float getVerticalPos(){
      return lStick.getVerPos(); // devolver joystick izquiedo vertical
   }
   public float getRotationPos(){
      return lStick.getHorPos();  // devolver joystick izquiedo horizontal
   }

   public Joystick getLjoy() {
      return lStick;
   }

   public Joystick getRjoy() {
      return rStick;
   }

   public void accionarButton(){
      pushTakeOff_Land();
   }

   public State getState() {
      return skyControlleraux.getState();
   }

   private Joystick lStick, rStick;
   private SkyController skyControlleraux;
}