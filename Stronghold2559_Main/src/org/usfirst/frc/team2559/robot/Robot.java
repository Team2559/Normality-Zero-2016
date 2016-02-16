
package org.usfirst.frc.team2559.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2559.robot.commands.SendLEDState;
import org.usfirst.frc.team2559.robot.commands.control.DoNothing;
import org.usfirst.frc.team2559.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2559.robot.subsystems.LEDStrip;
import org.usfirst.frc.team2559.robot.subsystems.Recorder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static DriveTrain _driveTrain;
	public static LEDStrip _ledStrip;
	public static Recorder _recorder;
	
	Compressor compressor;	
	Command autonomousCommand;

	// tribute to Hurricane Joe
	String[] autonomiceNames;
	Command[] autonomice;
	SendableChooser chooser = new SendableChooser();
	
	private void sendSensorData() {
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putNumber("Left Distance", _driveTrain.getLeftDistance());
		SmartDashboard.putNumber("Left Speed", _driveTrain.getLeftSpeedEnc());
		SmartDashboard.putNumber("Right Distance",
				_driveTrain.getRightDistance());
		SmartDashboard.putNumber("Right Speed", _driveTrain.getRightSpeedEnc());
		SmartDashboard.putNumber("Battery Voltage", DriverStation.getInstance()
				.getBatteryVoltage());
		SmartDashboard.putBoolean("Enabled", DriverStation.getInstance()
				.isEnabled());
		SmartDashboard.putBoolean("Autonomous", DriverStation.getInstance()
				.isAutonomous());
		SmartDashboard.putBoolean("TeleOp", DriverStation.getInstance()
				.isOperatorControl());
		SmartDashboard.putBoolean("FMS", DriverStation.getInstance()
				.isFMSAttached());
//		SmartDashboard.putNumber("Forklift Level", _PRISM.getCurrentLevel());
		SmartDashboard.putNumber("Gyro Angle", _driveTrain.getGyroAngle());
//		SmartDashboard.putNumber("Range Finder", _driveTrain.getDistanceIn());
	}
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	_driveTrain = new DriveTrain();
    	_ledStrip = new LEDStrip();
    	_recorder = new Recorder();
		oi = new OI();
		
		compressor = new Compressor(RobotMap.PCM_NODEID_COMPRESSOR);
		compressor.setClosedLoopControl(true);
		compressor.start();
		
		CameraServer server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");
		
		autonomiceNames = new String[] {
				"Do Nothing" };
		autonomice = new Command[] { new DoNothing() };

		for (int i = 0; i < autonomice.length; i++) {
			chooser.addObject(autonomiceNames[i], autonomice[i]);
		}

		SmartDashboard.putData("Which autonomous?", chooser);
		SmartDashboard.putData(Scheduler.getInstance());
		
		new Command("Sensor feedback") {
			protected void initialize() {
			}
			protected void execute() {
				sendSensorData();
			}
			protected boolean isFinished() {
				return false;
			}
			protected void end() {
			}
			protected void interrupted() {
				end();
			}
		}.start();		
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
		sendSensorData();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		sendSensorData();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	Robot._driveTrain.setAuton(true);
		autonomousCommand = (Command) chooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
		sendSensorData();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
		sendSensorData();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	Robot._driveTrain.setAuton(false);
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		sendSensorData();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
		sendSensorData();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
		sendSensorData();
    }
}
