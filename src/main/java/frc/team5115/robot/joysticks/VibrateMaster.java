///LOGITECH CONTROLLER
package frc.team5115.robot.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class VibrateMaster extends Controller{
    public VibrateMaster(int port){
        forwardAxis = 1;
        turnAxis = 2;
        throttleIncrease = 8;
        throttleDecrease = 7;
        ExterminateBind = 1;
        this.port = port;
        stick = new Joystick(this.port);
    }
}
