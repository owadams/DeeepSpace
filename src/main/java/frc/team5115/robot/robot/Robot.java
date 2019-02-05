package frc.team5115.robot.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team5115.robot.PID;
import frc.team5115.robot.joysticks.Controller;
import frc.team5115.robot.joysticks.ThrustMaster;
import frc.team5115.robot.statemachines.ArmStateMachine;
import frc.team5115.robot.statemachines.DriveStateMachine;
import frc.team5115.robot.subsystems.Arm;
import frc.team5115.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
  public static DriveTrain budgerobot;
  public static Arm arm;
  public static Controller joy;
  public static DriveStateMachine drivedomination;
  public static ArmStateMachine armdomination;
  PID PIDLoop; //PID Loop
  public void robotInit() {
      joy = new ThrustMaster(0);
      budgerobot = new DriveTrain();
      arm = new Arm();
  }

  public void teleopInit(){
      drivedomination.setState(DriveStateMachine.GO);
      armdomination.setState(ArmStateMachine.INPUT);
  }

  public void teleopPeriodic() {
      drivedomination.update();
      armdomination.update();
      if(joy.ExterminatePressed()){
          drivedomination.setState(DriveStateMachine.STOP);
      }
  }
  public void autonomousInit(){
      PIDLoop = new PID("heyy");
  }
  public void autonomousPeriodic(){
      budgerobot.driveLeft(PIDLoop.getPID(3000, budgerobot.returnPosistionLeft(), budgerobot.returnVelocityLeft()));
      budgerobot.driveRight(PIDLoop.getPID(3000, budgerobot.returnPositionRight(), budgerobot.returnVelocityRight()));
      if(PIDLoop.isFinished(500,.01)){
          budgerobot.driveLeft(0);
          budgerobot.driveLeft(0);
      }
  }
}