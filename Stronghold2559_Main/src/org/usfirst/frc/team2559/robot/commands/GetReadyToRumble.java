package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.RobotMap;
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
	if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID || val == RobotMap.LOWBAR_ID) {
	    addParallel(new PIDSetShooter(0));
	} else if (val == RobotMap.CDF_ID || val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val == RobotMap.ROUGHTERRAIN_ID) {
	    addParallel(new PIDSetShooter(60));
	}
	
	if (val == RobotMap.PORTCULLIS_ID || val == RobotMap.DRAWBRIDGE_ID || val == RobotMap.SALLYPORT_ID) {
	    //set arms as high as possible
	} else if (val == RobotMap.LOWBAR_ID || val == RobotMap.MOAT_ID || val == RobotMap.RAMPARTS_ID || val == RobotMap.ROCKWALL_ID || val == RobotMap.ROUGHTERRAIN_ID) {
	    //set arms as far back as possible
	} else if (val == RobotMap.CDF_ID) {
	    //set arms in the middle
	}

    }
}
