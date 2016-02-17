package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SendLEDState extends Command {
	
	int current_state;
	int counter = 0;

    public SendLEDState() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._ledStrip);
    }

    // Called just before this Command runs the first time
    protected void initialize() {    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(counter++ > 5) {
    		current_state = Robot._ledStrip.getMode();
    		Robot._ledStrip.sendData(current_state);
    		counter = 0;    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {    	
    }
}
