///XBOX CONTROLLER
package frc.team5115.robot.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class RumbleMaster extends Controller{
    public RumbleMaster(int port){
        forwardAxis = 0;
        throttleAxis = 3;
        turnAxis = 1;
        ExterminateBind = 1;
        this.port = port;
        stick = new Joystick(this.port);
    }
    @Override
    public double processThrottle(){
        throttle = (-stick.getThrottle() + 1) / 2;
        return throttle;
    }
}
