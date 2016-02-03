package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SendLEDState extends Command {
	
	I2C Wire = new I2C(Port.kOnboard, 4);
	byte[] toSend = new byte[1];
	static int previous_state = 0, current_state = 0;

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
    	current_state = Robot._ledStrip.getMode();
    	if(previous_state != current_state) {
    		toSend[0] = (byte)current_state;
        	Wire.transaction(toSend, 1, null, 0);
        	System.out.println("Sent state of " + Robot._ledStrip.getMode());
        	previous_state = current_state;
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
