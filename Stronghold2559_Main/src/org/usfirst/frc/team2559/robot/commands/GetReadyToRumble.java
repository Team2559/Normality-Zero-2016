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

    public GetReadyToRumble(int val) {
	// Add Commands here:
	// e.g. addSequential(new Command1());
	// addSequential(new Command2());
	// these will run in order.

	// To run multiple commands at the same time,
	// use addParallel()
	// e.g. addParallel(new Command1());
	// addSequential(new Command2());
	// Command1 and Command2 will run in parallel.

	// A command group will require all of the subsystems that each member
	// would require.
	// e.g. if Command1 requires chassis, and Command2 requires arm,
	// a CommandGroup containing them would require both the chassis and the
	// arm.
	// int shooter_setting=255, arm_setting=255;

	// Set arm to not in the way position
	// check where we are
	if ((val != RobotMap.PUSHUP_ENDGAME_ID) || (Robot._arm.getArmAngle() < 35)) {
	    addSequential(new PIDSetArm(RobotMap.ARM_INITIAL_POS_ANGLE));
	}

	// Do we need a delay?

	if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID || val == RobotMap.LOWBAR_ID) {
	    addSequential(new PIDSetShooter(10));
	    // shooter_setting = 10;
	} else if (val == RobotMap.CDF_ID || val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val == RobotMap.ROUGHTERRAIN_ID) {
	    addSequential(new PIDSetShooter(60));
	    // shooter_setting = 60;
	}

	if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID) {
	    addSequential(new PIDSetArm(202));
	    // arm_setting= 0;
	} else if (val == RobotMap.LOWBAR_ID) {
	    // set arms as far back as possible
	    addSequential(new PIDSetArm(0));
	    // arm_setting=51;
	} else if (val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val == RobotMap.ROUGHTERRAIN_ID) {
	    addSequential(new PIDSetArm(145));
	    // arm_setting=41;
	} else if (val == RobotMap.CDF_ID) {
	    // set arms in the middle
	    addSequential(new PIDSetArm(145));
	    // arm_setting=8;
	} else if (val == RobotMap.PUSHUP_ENDGAME_ID) {
	    addSequential(new PIDSetArm(240));
	}

	// We have setting, now set shooter and arm to position. 255 IS DO NOTHING VALUE
	// if()
    }
}
