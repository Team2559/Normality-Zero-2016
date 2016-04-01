package org.usfirst.frc.team2559.robot.commands.drive;

import org.usfirst.frc.team2559.lib.PIDTurnController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDLowbarTurn extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    double angle, originalVal;

    // 0.5, and .5 as min/max/tolerance

    public PIDLowbarTurn() {
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
	originalVal = Robot._shooter.getVisionAltitude();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot._driveTrain.tankDrive(0.5, -0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	angle = SmartDashboard.getNumber("azimuth", 0);
	return (angle != originalVal) && (angle != 0);
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
