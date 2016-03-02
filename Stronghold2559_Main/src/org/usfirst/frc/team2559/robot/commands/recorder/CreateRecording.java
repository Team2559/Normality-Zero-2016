package org.usfirst.frc.team2559.robot.commands.recorder;

import java.io.IOException;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CreateRecording extends Command {

	long startTime;
	String id;
	
    public CreateRecording(String id) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._recorder);
    	this.id = id;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
    	Robot._recorder.initializeRecorder(id);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		if (RobotMap.ANNOY_THE_DRIVERS) {
			Robot.oi.setRumble(RumbleType.kLeftRumble, 1);
			Robot.oi.setRumble(RumbleType.kRightRumble, 1);
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
		Robot.oi.setRumble(RumbleType.kLeftRumble, 0);
		Robot.oi.setRumble(RumbleType.kRightRumble, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
