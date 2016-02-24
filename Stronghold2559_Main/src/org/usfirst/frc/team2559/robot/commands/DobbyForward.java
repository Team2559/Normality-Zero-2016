package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DobbyForward extends Command {

	Long startTime;

	// if input time is larger than a constant reject and set as default

	public DobbyForward() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot._driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot._driveTrain.setAuton(true);
		startTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		/*
		 * execute: raise motor if raiseTime + current time is not greater than
		 * the maxTime 
		 * execute: if it is, raise for maxTime - currentTime (raise
		 * to the max)
		 * 
		 * isFinished: set the timeUp to the original value + the difference between the
		 * current time and the start time
		 * isFinished: stop if the current time is greater than the raise time +
		 * the start time
		 */
		Robot._driveTrain.tankDrive(0.5, 0.5);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot._driveTrain.setAuton(false);
		Robot._driveTrain.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
