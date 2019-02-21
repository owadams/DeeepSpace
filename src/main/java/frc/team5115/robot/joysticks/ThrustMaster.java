///THRUST MASTER
package frc.team5115.robot.joysticks;
import edu.wpi.first.wpilibj.Joystick;

public class ThrustMaster extends Controller {
    public ThrustMaster(int port){
        forwardAxis = 0;
        throttleAxis = 3;
        turnAxis = 1;
        this.port = port;
        stick = new Joystick(this.port);
        armUp = 1;
        armDown = 2;
    }

    @Override
    public double processThrottle(){
        throttle = (-stick.getThrottle() + 1) / 2;
        return throttle;
    }
}
