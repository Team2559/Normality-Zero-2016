package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForDistance extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    private double BASE_SPEED = 0.6;
    private double _distance;
    private boolean _forw  = true;

   private PIDControllerRT       pid	       = new PIDControllerRT(RobotMap.PID_STRAIGHT_Kp, RobotMap.PID_STRAIGHT_Ki, RobotMap.PID_STRAIGHT_Kd, -0.4, 0.4, 5, true); // creates

    // 0.5, and .5 as min/max/tolerance

   /**
    * The most verbose class constructor for a command that drives in a certain direction for a certain distance at a certain speed and stops.
    * This command does not use a PID controller, so do not expect for it to be 100% accurate.
    * 
    * @param speed speed to move at, a value between 0 and 1
    * @param distance distance, in inches, to drive
    * @param forw true = move forward, false = move backward
    */
   public DriveStraightForDistance(double speed, double distance, boolean forw) {
	requires(Robot._driveTrain);
	BASE_SPEED = speed;
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
   public DriveStraightForDistance(double speed, double distance) {
	requires(Robot._driveTrain);
	BASE_SPEED = speed;
	_distance = distance;
   }

   /**
    * Class constructor for a command that drives for a certain distance and stops.
    * This command does not use a PID controller, so do not expect for it to be 100% accurate.
    * Defaults to a speed of 80%.
    * 
    * @param distance distance, in inches, to drive forward
    */

   public DriveStraightForDistance(double distance) {
	requires(Robot._driveTrain);
	_distance = distance;
   }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.oi.setGoStraight(true);
	Robot._driveTrain.setAuton(true);
	Robot._driveTrain.setFastDrive(false);
	Robot._driveTrain.setReverseDrive(false);
	Robot._driveTrain.setSlowDrive(false);
	Robot._driveTrain.clearGyro();
	Robot._driveTrain.clearEncoder();
	pid.reset();
	pid.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	pid.calculate(Robot._driveTrain.getGyroAngle(), true);
	double scale = pid.getOutput() * ANGLES_TO_DEGREES;
	if (_forw)
	    Robot._driveTrain.tankDrive(BASE_SPEED + scale, BASE_SPEED - scale);
	else
	    Robot._driveTrain.tankDrive(-(BASE_SPEED + scale), -(BASE_SPEED - scale));
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
	System.out.println("We just got interupted!");
	end();
    }
}
