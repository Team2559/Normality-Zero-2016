package org.usfirst.frc.team2559.robot.commands.recorder;

import java.io.FileNotFoundException;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PlayRecording extends Command {
	
	String id;

    public PlayRecording(String id) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._recorder);
    	requires(Robot._driveTrain);
    	this.id = id;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			Robot._recorder.initializePlayer(id);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (RobotMap.ANNOY_THE_FUCK_OUT_OF_THE_DRIVERS) {
			Robot.oi.setRumble(RumbleType.kLeftRumble, 1);
			Robot.oi.setRumble(RumbleType.kRightRumble, 1);
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot._recorder.playRecording();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.getRecorderStopButton() || Robot._recorder.hasFinishedPlayback();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot._recorder.stopPlayback();
		Robot.oi.setRumble(RumbleType.kLeftRumble, 0);
		Robot.oi.setRumble(RumbleType.kRightRumble, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
