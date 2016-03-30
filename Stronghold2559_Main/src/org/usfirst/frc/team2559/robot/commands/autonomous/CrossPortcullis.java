package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.PIDVisionTurn;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDVisionShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

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
	addSequential(new GetReadyToRumble(RobotMap.PORTCULLIS_ID));
	addSequential(new WaitCommand(1));
	addSequential(new DriveForDistance(RobotMap.DISTANCE_TO_OUTERWORKS));
	addSequential(new WaitCommand(1));
	// bring our arms up while driving through the defense slowly
	addSequential(new Command() {

	    protected void initialize() {
		Robot._arm.setAdjusterSpeed(-1);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	addSequential(new WaitCommand(0.2));
	addSequential(new Command() {

	    protected void initialize() {
		Robot._arm.setAdjusterSpeed(0);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	addSequential(new WaitCommand(1));
	addSequential(new DriveForDistance(0.5, RobotMap.DISTANCE_TO_OUTERWORKS));
	addSequential(new PIDVisionTurn());
	addSequential(new PIDVisionShooter());

	// add shooting logic here later

    }
}
