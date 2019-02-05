package frc.team5115.robot.statemachines;

import frc.team5115.robot.robot.Robot;

public class ArmStateMachine extends StateMachineBase {
    public static final int GO = 1;
    public static final int RETURN = 2;
    public static final int INPUT = 0;
    double height = Robot.arm.homeHeight;

    public void update(){
        switch(state) {
            case INPUT:
                if(Robot.joy.getArmUp()){
                    state = GO;
                }
                else if(Robot.joy.getArmDown()){
                    state = RETURN;
                }
                break;

            case GO:
                while(!Robot.arm.getTop()){
                    height += Robot.arm.heightIncrease;
                    Robot.arm.setHeight(height);
                }
                if(Robot.arm.getTop()){
                    state = INPUT;
                }
                break;

            case RETURN:
                while(!Robot.arm.getHome()){
                    height -= Robot.arm.heightIncrease;
                    Robot.arm.setHeight(height);
                }
                if(Robot.arm.getHome()){
                    state = INPUT;
                }
                break;
        }
    }
}
