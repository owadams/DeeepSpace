package frc.team5115.robot.statemachines;

import frc.team5115.robot.robot.Robot;

public class ArmStateMachine extends StateMachineBase {
    public static final int MOVING_UP = 1;
    public static final int MOVING_DOWN = 2;
    public static final int NEUTRAL = 0;
    public double target = Robot.arm.level;

    public void update(){
        Robot.arm.getPosition();
        switch(state) {
            case NEUTRAL:
                Robot.arm.moveArm(0);
                break;

            case MOVING_UP:
                System.out.println("target " + target);
                System.out.println("angle " + Robot.arm.navX.getRoll());
                System.out.println("position " + Robot.arm.level);
                if(Robot.arm.level == target){
                    state = NEUTRAL;
                }
                Robot.arm.moveArm(.5);
                break;

            case MOVING_DOWN:
                System.out.println("target " + target);
                System.out.println("angle " + Robot.arm.navX.getRoll());
                System.out.println("position " + Robot.arm.level);
                System.out.println("downnnnn");
                if(Robot.arm.level == target){
                    state = NEUTRAL;
                }
                Robot.arm.moveArm(-.5);
                break;
        }
    }
}