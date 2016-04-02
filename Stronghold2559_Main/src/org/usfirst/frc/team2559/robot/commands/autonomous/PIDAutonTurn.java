package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.lib.PIDTurnController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author Evan T
 * 
 * The purpose of this class is to make the robot turn until it sees the retro-reflective tape.
 * 
 * It does this by grabbing the state of a SendableChooser, or a set of radio buttons on the driver station.
 * The only thing that might need changing is the speed the robot turns at. This might need to be higher
 * for carpet.
 *
 */
public class PIDAutonTurn extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    private static final double TURN_SPEED = 0.5;
    double angle, originalVal;
    int direction;

    public PIDAutonTurn() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	direction = (int)Robot.autonTurnDirection.getSelected();
	Robot._driveTrain.setAuton(true);
	Robot._driveTrain.setFastDrive(false);
	Robot._driveTrain.setReverseDrive(false);
	Robot._driveTrain.setSlowDrive(false);
	Robot._driveTrain.clearGyro();
	originalVal = Robot._shooter.getVisionAltitude();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if (direction == 1) {
	Robot._driveTrain.tankDrive(-TURN_SPEED, TURN_SPEED);
	} else if (direction == 2) {
	    Robot._driveTrain.tankDrive(TURN_SPEED, -TURN_SPEED);
	}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	angle = SmartDashboard.getNumber("azimuth", 0);
	return ((angle != originalVal) && (angle != 0)) || direction == 0;
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
