package org.usfirst.frc.team2559.robot.commands.arm;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualArm extends Command {

    boolean shouldStop = false;
    
    public ManualArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	shouldStop = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	if ((Robot._arm.getArmAngle() < RobotMap.ARM_MAX_ANGLE)) {
	    Robot._arm.setAdjusterSpeed(-Robot.oi.getShooterStickVal() * 0.7);
	} else {
	    if (Robot.oi.getShooterStickVal() > 0) {
		Robot._arm.setAdjusterSpeed(-Robot.oi.getShooterStickVal() * 0.7);
	    } else {
		Robot._arm.setAdjusterSpeed(0);
		shouldStop = true;
	    }
	}
//	Robot._arm.setAdjusterSpeed(-Robot.oi.getShooterStickVal() * 0.7);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shouldStop;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot._arm.setAdjusterSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
