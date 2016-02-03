package org.usfirst.frc.team2559.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static final int DISABLED_ID = 0,
							AUTONOMOUS_ID = 1,
							TELEOP_ID = 2,
							TELEOP_BLUE_ID = 3,
							TELEOP_RED_ID = 4,
							TELEOP_INVALID_ID = 5,
							TELEOP_WHAT_ID = 6;
	
	public static final double SLOWDRIVE_CONSTANT = 0.75;
	
	public static final int JOYSTICK_AXIS_DRIVE_LEFT = 1,
							JOYSTICK_AXIS_DRIVE_RIGHT = 5;
	
	public static final int PORT_DRIVETRAIN_LEFT_FR = 1,
							PORT_DRIVETRAIN_LEFT_BACK = 2,
							PORT_DRIVETRAIN_RIGHT_FR = 3,
							PORT_DRIVETRAIN_RIGHT_BACK = 4;
	
	public static final int PCM_NODEID = 0,
							PDP_NODEID = 1,
							PCM_NODEID_COMPRESSOR = 0;
	
	public static final int PORT_ANALOG_GYRO = 0,
							PORT_ANALOG_RANGEFINDER = 1;	

	public static final int PORT_RELAY_LEFT = 1,
							PORT_RELAY_RIGHT = 2;
	
	public static final int PORT_ENCODER_LEFT_1 = 6,
							PORT_ENCODER_LEFT_2 = 7,
							PORT_ENCODER_RIGHT_1 = 8,
							PORT_ENCODER_RIGHT_2 = 9;
	
	public static final boolean ANNOY_THE_FUCK_OUT_OF_THE_DRIVERS = true;
	public static final boolean USE_TOO_MUCH_BANDWIDTH = false;
}
