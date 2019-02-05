package frc.team5115.robot.statemachines;

import frc.team5115.robot.robot.Robot;

public class DriveStateMachine extends StateMachineBase{
    public static final int GO = 1;
    public void update(){
        switch(state){
            case STOP:
                Robot.budgerobot.Exterminate();
                break;
            case GO:
                Robot.budgerobot.Drive();
                break;
        }
    }

}
