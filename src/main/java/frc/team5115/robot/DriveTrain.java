package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveTrain {
    int FrontRightMotorID = 4;
    int FrontLeftMotorID = 3;
    int BackRightMotorID = 2;
    int BackLeftMotorID = 1;
    double throttle;

    TalonSRX FrontRight;
    TalonSRX FrontLeft;
    TalonSRX BackRight;
    TalonSRX BackLeft;
    Joystick joy;

    ShuffleboardTab tab = Shuffleboard.getTab("tab1");
    NetworkTableEntry throttleValue;

    public DriveTrain() {
        FrontRight = new TalonSRX(FrontRightMotorID);
        FrontLeft = new TalonSRX(FrontLeftMotorID);
        BackRight = new TalonSRX(BackRightMotorID);
        BackLeft = new TalonSRX(BackLeftMotorID);
        joy = new Joystick(0);

        BackRight.set(ControlMode.Follower, FrontRightMotorID);
        BackLeft.set(ControlMode.Follower, FrontLeftMotorID);
        throttleValue = tab.add("throttle2", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    }

    public void Drive() {
        double xValue = joy.getX();
        double yValue = joy.getY();
        throttle = (-joy.getThrottle() + 1) / 2;
        FrontRight.set(ControlMode.PercentOutput, (yValue + xValue) * throttle);
        FrontLeft.set(ControlMode.PercentOutput, (-yValue + xValue) * throttle);
        throttleValue.setDouble(throttle);
    }
}
