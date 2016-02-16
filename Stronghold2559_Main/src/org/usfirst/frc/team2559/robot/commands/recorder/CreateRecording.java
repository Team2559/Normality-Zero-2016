package org.usfirst.frc.team2559.robot.commands.recorder;

import java.io.IOException;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CreateRecording extends Command {

	long startTime;
	
    public CreateRecording() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._recorder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
    	Robot._recorder.initializeRecorder();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    	Robot._recorder.recordInput(startTime);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.oi.getRecorderStopButton();
    }

    // Called once after isFinished returns true
    protected void end() {
    	try {
			Robot._recorder.stopRecording();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
