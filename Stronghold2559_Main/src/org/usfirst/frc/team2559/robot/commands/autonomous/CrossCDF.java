package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossCDF extends CommandGroup {

    public CrossCDF() {
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
	addSequential(new WaitCommand(1));
	addSequential(new DriveForDistance(RobotMap.DISTANCE_TO_OUTERWORKS));
	addSequential(new WaitCommand(1));
	// push our arms down the whole way to push the CDF down
	addSequential(new PIDSetArm(RobotMap.ARM_GROUND_ANGLE));
	addSequential(new WaitCommand(1));
	// move full speed over the CDF
	addSequential(new DriveForDistance(1, RobotMap.DISTANCE_TO_OUTERWORKS));
    }
}
