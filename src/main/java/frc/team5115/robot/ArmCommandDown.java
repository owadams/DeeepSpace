package frc.team5115.robot;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.robot.Robot;
    public class ArmCommandDown extends Command {
        protected boolean isFinished() {
            return true;
        }

        protected void initialize() {
            System.out.println("down");
            Robot.armdomination.target -= 1;
            Robot.armdomination.setState(Robot.armdomination.MOVING_DOWN);
        }
    }
