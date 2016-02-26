package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {

	int _angle;
	double _speed;
	Long startTime;

	public Turn(int angle, double speed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot._driveTrain);
		_speed = speed;
		_angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot._driveTrain.setAuton(true);
		Robot._driveTrain.setFastDrive(false);
		Robot._driveTrain.setReverseDrive(false);
		Robot._driveTrain.setSlowDrive(false);
		Robot._driveTrain.clearGyro();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (_angle > 0) {
			Robot._driveTrain.tankDrive(_speed, -_speed);
		} else {
			Robot._driveTrain.tankDrive(-_speed, _speed);
		}
		System.out.println("Gyro Angle: " + Robot._driveTrain.getGyroAngle());	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(_angle > 0) {
			if(Robot._driveTrain.getGyroAngle() > _angle) {
				return true;
			}
		} else {
			if(Robot._driveTrain.getGyroAngle() < _angle) {
				return true;
			}
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot._driveTrain.tankDrive(0, 0);
		Robot._driveTrain.setAuton(false);
		System.out.println("Finished. Gyro Angle: " + Robot._driveTrain.getGyroAngle());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
