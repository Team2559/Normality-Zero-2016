package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroDebug extends Command {

	Long startTime, currentTime;
	double gyroAngle, lastAngle;
	int counterExecute = 0, counterChange = 0;

	public GyroDebug() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot._driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot._driveTrain.setAuton(true);
		Robot._driveTrain.setFastDrive(false);
		Robot._driveTrain.setReverseDrive(false);
		Robot._driveTrain.setSlowDrive(false);
		Robot._driveTrain.clearGyro();
		startTime = System.currentTimeMillis();
		gyroAngle = Robot._driveTrain.getGyroAngle();
		lastAngle = gyroAngle;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		gyroAngle = Robot._driveTrain.getGyroAngle();
		currentTime = System.currentTimeMillis();
		if(gyroAngle < lastAngle) {
			counterChange++;
		}
		lastAngle = gyroAngle;
		counterExecute++;
		System.out.println("Gyro Angle: " + gyroAngle);
		System.out.println("Time Elapsed (ms): " + (currentTime - startTime));
		System.out.println("----------------------");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return currentTime >= (startTime + 30000);
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Finished.\nAverage change in angle was " +  gyroAngle / ((currentTime - startTime)));
		System.out.println("Gyro: " + gyroAngle + "\nTime: " + (currentTime - startTime));
		System.out.println("Execute Counter: " + counterExecute);
		System.out.println("Change Counter: " + counterChange);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
