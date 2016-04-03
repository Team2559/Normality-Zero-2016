package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DobbySpin extends Command {

    boolean left;
    double  speed = 0.7;

    /**
     * Class constructor for a function that makes the robot spin in a circle.
     * Defaults to a speed of 50%.
     * 
     * @param direction spin direction, left = true and false = right
     */
    public DobbySpin(boolean direction) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
	this.left = direction;
    }

    /**
     * Class constructor for a function that makes the robot spin in a circle.
     * Be careful of using a high spin speed as wheel slipping may occur.
     * 
     * @param direction spin direction, left = true and false = right
     * @param speed speed of the turn, range of 0 to 1
     */
    public DobbySpin(boolean direction, double speed) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
	this.left = direction;
	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot._driveTrain.setAuton(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot._driveTrain.tankDrive(left ? -speed : speed, left ? speed : -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot._driveTrain.tankDrive(0, 0);
	Robot._driveTrain.setAuton(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }

}
