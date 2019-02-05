package frc.team5115.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.robot.robot.Robot;

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


    public static ShuffleboardTab tab = Shuffleboard.getTab("tab1");
    NetworkTableEntry throttleValue;

    public DriveTrain() {
        FrontRight = new TalonSRX(FrontRightMotorID);
        FrontLeft = new TalonSRX(FrontLeftMotorID);
        BackRight = new TalonSRX(BackRightMotorID);
        BackLeft = new TalonSRX(BackLeftMotorID);


        BackRight.set(ControlMode.Follower, FrontRightMotorID);
        BackLeft.set(ControlMode.Follower, FrontLeftMotorID);

        BackRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,5);
        BackLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,5);
        throttleValue = tab.add("throttle2", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    }
    public void Exterminate(){
        FrontRight.set(ControlMode.PercentOutput, 0);
        FrontLeft.set(ControlMode.PercentOutput, 0);
    }
    public void Drive() {
        double xValue = Robot.joy.getTurn();
        double yValue = Robot.joy.getForward();
        FrontRight.set(ControlMode.PercentOutput, (yValue + xValue) * throttle);
        FrontLeft.set(ControlMode.PercentOutput, (-yValue + xValue) * throttle);
        throttleValue.setDouble(throttle);
    }
    public double returnVelocityLeft(){return BackLeft.getSelectedSensorVelocity(0);}
    public double returnVelocityRight(){return BackRight.getSelectedSensorVelocity(0);}

    public double returnPosistionLeft(){return BackLeft.getSelectedSensorPosition(0);}
    public double returnPositionRight(){return BackRight.getSelectedSensorPosition(0);}


}
