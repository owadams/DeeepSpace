package frc.team5115.robot;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.robot.Robot;
import frc.team5115.robot.statemachines.ArmStateMachine;

import static frc.team5115.robot.robot.Robot.armdomination;

public class ArmCommandUp extends Command {
    protected boolean isFinished(){
        return true;
    }

    protected void initialize(){
        System.out.println("up");
        Robot.armdomination.target += 1;
        Robot.armdomination.setState(Robot.armdomination.MOVING_UP);
    }

}
