package frc.team5115.robot.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team5115.robot.ArmCommandDown;
import frc.team5115.robot.ArmCommandUp;
import frc.team5115.robot.PID;
import frc.team5115.robot.joysticks.Controller;
import frc.team5115.robot.joysticks.ThrustMaster;
import frc.team5115.robot.statemachines.ArmStateMachine;
import frc.team5115.robot.statemachines.DriveStateMachine;
import frc.team5115.robot.subsystems.Arm;
import frc.team5115.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
  public static DriveTrain driveTrain;
  public static Arm arm;
  public static Controller joy;
  public static DriveStateMachine drivedomination;
  public static ArmStateMachine armdomination;
  public PID PIDLoop;
  public double targetRightEncoderValue;
  public double targetLeftEncoderValue;
  public int esketit = 0;

  public void robotInit() {
      joy = new ThrustMaster(0);

      JoystickButton armCommandUp;
      JoystickButton armCommandDown;
      armCommandUp = new JoystickButton(joy.getJoy(), 1);
      armCommandUp.whenPressed(new ArmCommandUp());
      armCommandDown = new JoystickButton(joy.getJoy(), 2);
      armCommandDown.whenPressed(new ArmCommandDown());

      driveTrain = new DriveTrain();
      arm = new Arm();
      drivedomination = new DriveStateMachine();
      armdomination  = new ArmStateMachine();
      PIDLoop = new PID("mr.loopy");
  }

  public void teleopInit(){
      drivedomination.setState(DriveStateMachine.GO);
      armdomination.setState(ArmStateMachine.NEUTRAL);
  }

  public void teleopPeriodic() {
      Scheduler.getInstance().run();
      drivedomination.update();
      armdomination.update();
      if(joy.ExterminatePressed()){
          drivedomination.setState(DriveStateMachine.STOP);
      }
      if(joy.RevivalPressed()){
          drivedomination.setState(DriveStateMachine.GO);
      }
  }
  public void autonomousInit(){
      //instead of resetting the encoders in the robot
      targetLeftEncoderValue = driveTrain.returnPosistionLeft() + 5000;
      targetRightEncoderValue = driveTrain.returnPositionRight() + 5000;
  }
  public void autonomousPeriodic(){
      switch(esketit) {
          case 0:
              driveTrain.driveLeft(PIDLoop.getPID(
                      targetLeftEncoderValue,
                      driveTrain.returnPosistionLeft(),
                      driveTrain.returnVelocityLeft()));
//              driveTrain.driveRight(PIDLoop.getPID(
//                      targetRightEncoderValue,
//                      driveTrain.returnPositionRight(),
//                      driveTrain.returnVelocityRight()));
              if (PIDLoop.isFinished(500, 20)){
                  esketit = 1;}
              break;
          case 1:
                  System.out.println("Done :)");
                  driveTrain.driveLeft(0);
                  driveTrain.driveRight(0);
      }
  }
  public void testInit(){}

  public void testPeriodic(){
      System.out.println(arm.navX.getRoll());
      if(joy.getArmUp()){
          arm.moveArm(.5);
      }
      else if(joy.getArmDown()){
          arm.moveArm(-.5);
      }
      else{
          arm.moveArm(0);
      }
  }
}