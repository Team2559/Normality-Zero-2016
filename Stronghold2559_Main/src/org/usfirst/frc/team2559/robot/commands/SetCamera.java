package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class SetCamera extends Command {

    boolean	       val;
    private static String ATTR_EX_MODE = "CameraAttributes::Exposure::Mode";
    private static String ATTR_EX_VALUE = "CameraAttributes::Exposure::Value";


    public SetCamera(boolean val) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	this.val = val;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	if (val) {

//	    NIVision.IMAQdxStopAcquisition(Robot.currentSession);
//	    Robot.currentSession = Robot.session0;
//	    NIVision.IMAQdxConfigureGrab(Robot.currentSession);
//	    int m_id = Robot.currentSession, m_exposureValue = 0;
//	    NIVision.IMAQdxSetAttributeString(m_id, ATTR_EX_MODE, "Manual");
//		long minv = NIVision.IMAQdxGetAttributeMinimumI64(m_id, ATTR_EX_VALUE);
//		long maxv = NIVision.IMAQdxGetAttributeMaximumI64(m_id, ATTR_EX_VALUE);
//		long val = minv + (long) (((double) (maxv - minv)) * (((double) m_exposureValue) / 100.0));
//		NIVision.IMAQdxSetAttributeI64(m_id, ATTR_EX_VALUE, val);
//	    }

	     Robot.cam0.setExposureManual(0);
	     Robot.cam0.setBrightness(0);
	     Robot.cam0.setWhiteBalanceManual(10000);
	     Robot.server.startAutomaticCapture(Robot.cam0);
	} else {
//	    NIVision.IMAQdxStopAcquisition(Robot.currentSession);
//	    Robot.currentSession = Robot.session1;
//	    NIVision.IMAQdxConfigureGrab(Robot.currentSession);
	    
//	    NIVision.IMAQdxStopAcquisition(Robot.currentSession);
//	    Robot.currentSession = Robot.session1;
//	    NIVision.IMAQdxConfigureGrab(Robot.currentSession);
//	    int m_id = Robot.currentSession, m_exposureValue = 1;
//	    NIVision.IMAQdxSetAttributeString(m_id, ATTR_EX_MODE, "Manual");
//		long minv = NIVision.IMAQdxGetAttributeMinimumI64(m_id, ATTR_EX_VALUE);
//		long maxv = NIVision.IMAQdxGetAttributeMaximumI64(m_id, ATTR_EX_VALUE);
//		long val = minv + (long) (((double) (maxv - minv)) * (((double) m_exposureValue) / 100.0));
//		NIVision.IMAQdxSetAttributeI64(m_id, ATTR_EX_VALUE, val);
//	    }
	     Robot.cam0.setExposureManual(1);
	     Robot.cam0.setBrightness(0);
	     Robot.cam0.setWhiteBalanceAuto();
	     Robot.server.startAutomaticCapture(Robot.cam0);
	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return true;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
