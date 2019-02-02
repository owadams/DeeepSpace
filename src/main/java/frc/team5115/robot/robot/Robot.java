package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team5115.robot.joysticks.Controller;
import frc.team5115.robot.joysticks.ThrustMaster;

public class Robot extends TimedRobot {

  DriveTrain drivetrain;
  static Controller joy;
  public void robotInit() {
      joy = new ThrustMaster(0);
      drivetrain = new DriveTrain();
  }

  public void teleopPeriodic() {
      drivetrain.Drive();
      if(joy.ExterminatePressed()){
          drivetrain.Exterminate();
      }
  }
}