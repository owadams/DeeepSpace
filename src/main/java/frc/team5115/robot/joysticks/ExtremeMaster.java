///EXTREME3DPRO
package frc.team5115.robot.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class ExtremeMaster extends Controller{
    public ExtremeMaster(int port){
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

