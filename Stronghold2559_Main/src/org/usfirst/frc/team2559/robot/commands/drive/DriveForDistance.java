package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForDistance extends Command {

    private double  _speed = 0.8, _distance;
    private boolean _forw  = true;

    /**
     * The most verbose class constructor for a command that drives in a certain direction for a certain distance at a certain speed and stops.
     * This command does not use a PID controller, so do not expect for it to be 100% accurate.
     * 
     * @param speed speed to move at, a value between 0 and 1
     * @param distance distance, in inches, to drive
     * @param forw true = move forward, false = move backward
     */
    public DriveForDistance(double speed, double distance, boolean forw) {
	requires(Robot._driveTrain);
	_speed = speed;
	_distance = distance;
	_forw = forw;
    }

    /**
     * Class constructor for a command that drives for a certain distance at a certain speed and stops.
     * This command does not use a PID controller, so do not expect for it to be 100% accurate.
     * 
     * @param speed speed to move at, between 0 and 1
     * @param distance distance, in inches, to drive forward
     */
    public DriveForDistance(double speed, double distance) {
	requires(Robot._driveTrain);
	_speed = speed;
	_distance = distance;
    }

    /**
     * Class constructor for a command that drives for a certain distance and stops.
     * This command does not use a PID controller, so do not expect for it to be 100% accurate.
     * Defaults to a speed of 80%.
     * 
     * @param distance distance, in inches, to drive forward
     */

    public DriveForDistance(double distance) {
	requires(Robot._driveTrain);
	_distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot._driveTrain.clearEncoder();
	Robot._driveTrain.setAuton(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (_forw) {
	    Robot._driveTrain.tankDrive(_speed, _speed);
	} else {
	    Robot._driveTrain.tankDrive(-_speed, -_speed);
	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	double left = Robot._driveTrain.getLeftDistance();
	double right = Robot._driveTrain.getRightDistance();
	double distance = Math.abs(left) > Math.abs(right) ? left : right;
	if (_forw) {
	    return distance >= _distance;
	} else {
	    return distance <= _distance;
	}
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
