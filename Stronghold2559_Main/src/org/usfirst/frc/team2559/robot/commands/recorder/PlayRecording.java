package org.usfirst.frc.team2559.robot.commands.recorder;

import java.io.FileNotFoundException;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PlayRecording extends Command {

    public PlayRecording() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._recorder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			Robot._recorder.initializePlayer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot._recorder.playRecording();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.getRecorderStopButton();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot._recorder.stopPlayback();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
