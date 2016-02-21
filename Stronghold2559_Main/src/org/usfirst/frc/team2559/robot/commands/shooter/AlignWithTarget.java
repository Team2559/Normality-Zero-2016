package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWithTarget extends Command {
	
	double distanceToCenter = 0;

    public AlignWithTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot._driveTrain.setAuton(true);
    	distanceToCenter = Robot._shooter.getXOffset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	distanceToCenter = Robot._shooter.getXOffset();
		if (distanceToCenter < -RobotMap.SMARTSHOOT_X_THRESHOLD) {
			Robot._driveTrain.tankDrive(-RobotMap.SMARTSHOOT_TURN_SPEED, RobotMap.SMARTSHOOT_TURN_SPEED);
		} else if (distanceToCenter > RobotMap.SMARTSHOOT_X_THRESHOLD) {
			Robot._driveTrain.tankDrive(RobotMap.SMARTSHOOT_TURN_SPEED, -RobotMap.SMARTSHOOT_TURN_SPEED);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (distanceToCenter >= -RobotMap.SMARTSHOOT_X_THRESHOLD && distanceToCenter <= RobotMap.SMARTSHOOT_X_THRESHOLD);
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
