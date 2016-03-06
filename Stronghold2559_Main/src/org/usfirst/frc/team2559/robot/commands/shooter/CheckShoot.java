package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CheckShoot extends Command {

	boolean hasShot = false;
	
    public CheckShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot._shooter); // commented
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot._shooter.setShootingStatus(true); // commented
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if ((Math.abs(Robot._shooter.getXOffset()) <= RobotMap.SMARTSHOOT_X_THRESHOLD) &&  // commented
//   			(Math.abs(Robot._shooter.getYOffset()) <= RobotMap.SMARTSHOOT_Y_THRESHOLD)) { // commented
    		// shoot
    		hasShot = true;
//    	} // commented
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hasShot;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot._shooter.setShootingStatus(false); // commented
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
