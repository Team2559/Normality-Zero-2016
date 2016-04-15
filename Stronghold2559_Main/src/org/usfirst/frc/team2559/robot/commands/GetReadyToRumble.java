package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetReadyToRumble extends CommandGroup {
    
    int val;

    public GetReadyToRumble(int val) {
	this.val = val;
	
	 System.out.println(Robot._arm.getArmAngle());
	 if (Robot._arm.getArmAngle() < 35.0) {
	 addSequential(new PIDSetArm(RobotMap.ARM_INITIAL_POS_ANGLE));
	 }
	
	 // Do we need a delay?
	
	 if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID) {
	 addSequential(new PIDSetShooter(10));
	 // shooter_setting = 10;
	 } else if (val == RobotMap.CDF_ID || val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val
	 == RobotMap.ROUGHTERRAIN_ID) {
	 addSequential(new PIDSetShooter(40.5));
	 // shooter_setting = 60;
	 } else if (val == RobotMap.LOWBAR_ID) {
	 addSequential(new PIDSetShooter(1));
	 } else if (val == RobotMap.PUSHUP_ENDGAME_ID) {
	 addSequential(new PIDSetShooter(30));
	 } else if (val == RobotMap.HOME_ID) {
	 addSequential(new PIDSetShooter(35));
	 } else if (val == RobotMap.SHOOTING_ID) {
	 addSequential(new PIDSetShooter(30));
	 } else if (val == RobotMap.LOWGOAL_SHOOTING_ID) {
	 addSequential(new PIDSetShooter(RobotMap.SHOOTER_INTAKE_ANGLE));
	 }
	
	 if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID) {
	 addSequential(new PIDSetArm(200));
	 // arm_setting= 0;
	 } else if (val == RobotMap.LOWBAR_ID) {
	 // set arms as far back as possible
	 addSequential(new PIDSetArm(0));
	 // arm_setting=51;
	 } else if (val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val ==
	 RobotMap.ROUGHTERRAIN_ID) {
	 addSequential(new PIDSetArm(145));
	 // arm_setting=41;
	 } else if (val == RobotMap.CDF_ID) {
	 // set arms in the middle
	 addSequential(new PIDSetArm(145));
	 // arm_setting=8;
	 } else if (val == RobotMap.PUSHUP_ENDGAME_ID) {
	 addSequential(new PIDSetArm(210, -0.8, 0.8));
	 } else if (val == RobotMap.HOME_ID || val == RobotMap.LOWGOAL_SHOOTING_ID) {
	 addSequential(new PIDSetArm(RobotMap.ARM_INITIAL_POS_ANGLE));
	 } else if (val == RobotMap.SHOOTING_ID) {
	 addSequential(new PIDSetArm(RobotMap.ARM_INTAKE_ANGLE));
	 }
    }
    
    protected void initialize() {
		
	
    }
}
