package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.lib.PIDTurnController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDSallyStraight extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    private static final double SPEED = 0.6;

   private PIDControllerRT       pid	       = new PIDControllerRT(RobotMap.PID_STRAIGHT_Kp, RobotMap.PID_STRAIGHT_Ki, RobotMap.PID_STRAIGHT_Kd, -0.4, 0.4, 1, true); // creates

    // 0.5, and .5 as min/max/tolerance

    public PIDSallyStraight() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.oi.setGoStraight(true);
	Robot._driveTrain.setAuton(true);
	Robot._driveTrain.setFastDrive(false);
	Robot._driveTrain.setReverseDrive(false);
	Robot._driveTrain.setSlowDrive(false);
	Robot._driveTrain.clearGyro();
	pid.reset();
	pid.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	pid.calculate(Robot._driveTrain.getGyroAngle(), true);
	double scale = pid.getOutput() * ANGLES_TO_DEGREES;
	Robot._driveTrain.tankDrive(SPEED + scale, SPEED - scale);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//	return Robot.oi.getPIDSallyStraightButton();
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
	System.out.println("We just got interupted!");
	end();
    }
}
