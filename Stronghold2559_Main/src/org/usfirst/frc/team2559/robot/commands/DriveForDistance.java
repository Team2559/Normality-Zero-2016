package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForDistance extends Command {
	private final double _speed,
						 _distance;
	private final boolean _forw;

    public DriveForDistance(double speed, double distance, boolean forw) {
        requires(Robot._driveTrain);
        _speed = speed;
        _distance = distance;
        _forw = forw;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot._driveTrain.clearEncoder();
    	System.out.println("Started drivefordist");
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("looping through execute");
		if (_forw) {
			Robot._driveTrain.tankDrive(_speed, _speed);
		} else {
			Robot._driveTrain.tankDrive(-_speed, -_speed);
		}

	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("looping through isFinished");
    	double left = Robot._driveTrain.getLeftDistance();
    	double right = Robot._driveTrain.getRightDistance();
    	double distance = Math.abs(left) > Math.abs(right) ? left : right;
    	if(_forw) {
    		return distance >= _distance;
    	} else {
    		return distance <= _distance;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot._driveTrain.tankDrive(0, 0);
    	System.out.println("DriveForDistance ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("DriveForDistance interrupted");
    	end();
    }
}
