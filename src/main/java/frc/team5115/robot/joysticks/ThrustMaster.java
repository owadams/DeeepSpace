package frc.team5115.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ThrustMaster extends Controller{
    public ThrustMaster(int port){
        forwardAxis = 0;
        turnAxis = 1;
        ExterminateBind = 7;
        this.port = port;
        stick = new Joystick(this.port);
    }
}
