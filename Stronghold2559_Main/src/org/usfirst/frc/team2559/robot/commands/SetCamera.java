package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class SetCamera extends Command {

	boolean val;
	
    public SetCamera(boolean val) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.val = val;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(val) {
			Robot.server = CameraServer.getInstance();
			Robot.server.setQuality(50);
			Robot.cam = new USBCamera("cam0");
			Robot.cam.setSize(640, 360);
			Robot.cam.setExposureManual(0);
			Robot.cam.setBrightness(0);
			Robot.cam.setWhiteBalanceManual(10000);
			Robot.server.startAutomaticCapture(Robot.cam);
    	} else {
			Robot.server = CameraServer.getInstance();
			Robot.server.setQuality(50);
			Robot.cam = new USBCamera("cam0");
			Robot.cam.setSize(640, 360);
			Robot.cam.setExposureManual(50);
			Robot.cam.setBrightness(0);
			Robot.cam.setWhiteBalanceAuto();
			Robot.server.startAutomaticCapture(Robot.cam);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
