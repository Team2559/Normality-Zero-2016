package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AdjustShooter extends Command {
	
	double value;

    public AdjustShooter(double val) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot._shooter); // commented
    	value = val;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (value < 0) {
//    		Robot._shooter.setAdjusterSpeed(1); // commented
//    		Robot._shooter.setClutchServo(0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
//    	Robot._shooter.setAdjusterSpeed(value); // commented
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot._shooter.setAdjusterSpeed(0); // commented
//    	Robot._shooter.setClutchServo(1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
