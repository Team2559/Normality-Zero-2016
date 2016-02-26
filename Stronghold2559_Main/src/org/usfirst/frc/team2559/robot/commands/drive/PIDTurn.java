package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDTurn extends Command {

	int _angle;
	double _speed;
	Long startTime;

	public PIDTurn(int angle, double speed) {
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
		startTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double 	gyroAngle = Robot._driveTrain.getGyroAngle(), 
				calcAngle = (1 - (Robot._driveTrain.getGyroAngle() / _angle)) * 0.7;
		
		if (_angle > 0) {
			System.out.println("-Angle: " + gyroAngle);
			System.out.println("-Theta over 90: " + calcAngle);
			if (calcAngle <= RobotMap.TURNING_MIN) {
				Robot._driveTrain.tankDrive(RobotMap.TURNING_SPEED, -RobotMap.TURNING_SPEED);
				System.out.println("Theta over angle is less than " + RobotMap.TURNING_MIN + ", sending " + RobotMap.TURNING_MIN + "!");
			} else {
				Robot._driveTrain.tankDrive(calcAngle, -calcAngle);
				System.out.println("Sending Voltage: " + calcAngle);
			}
		} else {
		}
		System.out.println("Gyro Angle: " + gyroAngle);
		System.out.println("----------------------");
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
