package frc.team5115.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Arm {
    public TalonSRX arm;
    public static DigitalInput home;
    public static DigitalInput top;
    public double homeHeight = 0;
    public double heightIncrease = 2;

    public Arm(){
        arm = new TalonSRX(5);
        home = new DigitalInput(0);
        top = new DigitalInput(1);
    }
    public static boolean getHome(){return home.get();}
    public static boolean getTop(){return top.get();}

    public void setHeight(double height){
        arm.set(ControlMode.Position, height);
    }
}
