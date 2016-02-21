package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntake extends Command {
	
	String forward;

    public SetIntake(String forward) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._shooter);
    	this.forward = forward;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (forward.equals("in")) {
    		Robot._shooter.intakeIn();
    	} else if(forward.equals("out")){
    		Robot._shooter.intakeOut();
    	} else {
    		Robot._shooter.intakeStop();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}