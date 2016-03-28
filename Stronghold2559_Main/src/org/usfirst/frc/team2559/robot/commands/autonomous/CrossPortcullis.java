package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossPortcullis extends CommandGroup {

    public CrossPortcullis() {
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

	// set our arms and shooter to portcullis in the event we aren't in starting config while driving to outerworks
	addParallel(new GetReadyToRumble(RobotMap.CDF_ID));
	addSequential(new DriveForDistance(RobotMap.DISTANCE_TO_OUTERWORKS));
	// bring our arms up while driving through the defense slowly
	addSequential(new PIDSetArm(RobotMap.ARM_INITIAL_POS_ANGLE));
	addSequential(new DriveForDistance(0.5, RobotMap.DISTANCE_TO_OUTERWORKS));

	// add shooting logic here later

    }
}
