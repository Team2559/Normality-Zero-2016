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

//@formatter:off
    
// Do not use Eclipse's built-in code formatting for this file!
// It will become very messed up.

/*
 * Programming Constants
 */

//public static final double  PID_TURN_Kp			      = 0.003,
//                            PID_TURN_Ki			      = 0.05, 
//                            PID_TURN_Kd			      = 0.15;
    
public static final double  PID_TURN_Kp			      = 0.3,
                            PID_TURN_Ki			      = 0.4, 
                            PID_TURN_Kd			      = 0.2;

public static final double  PID_SHOOTER_Kp		      = 0.35,
                            PID_SHOOTER_Ki		      = 0.01, 
                            PID_SHOOTER_Kd		      = 0.15;

public static final double  PID_ARM_Kp		     	      = 0.5,
                            PID_ARM_Ki		      	      = 0.3, 
                            PID_ARM_Kd		      	      = 0.3;

public static final double  PID_STRAIGHT_Kp	     	      = 0.5,
                            PID_STRAIGHT_Ki	      	      = 0.3, 
                            PID_STRAIGHT_Kd	      	      = 0.15;

public static final int     PORTCULLIS_ID 		      = 0,
                            CDF_ID			      = 1,
                            MOAT_ID			      = 2,
                            RAMPARTS_ID			      = 3,
                            DRAWBRIDGE_ID		      = 4,
                            SALLYPORT_ID		      = 5,
                            ROCKWALL_ID			      = 6,
                            ROUGHTERRAIN_ID		      = 7,
                            LOWBAR_ID 			      = 8,
                            PUSHUP_ENDGAME_ID		      = 9,
                            HOME_ID			      = 10,
                            SHOOTING_ID			      = 11;

/*
 * LED Constants 
 */

public static final int	    DISABLED_ID			      = 0,
                            AUTONOMOUS_ID		      = 1, 
                            TELEOP_ID			      = 2, 
                            TELEOP_BLUE_ID 		      = 3, 
                            TELEOP_RED_ID 		      = 4, 
                            TELEOP_INVALID_ID		      = 5, 
                            TELEOP_WHAT_ID		      = 6, 
                            TELEOP_LOW_BATTERY_ID	      = 7, 
                            TELEOP_TARGETING_ID 	      = 8, 
                            TELEOP_SHOOTING_ID		      = 9, 
                            TELEOP_LOW_TIME_ID		      = 10;

/*
 * Drivetrain Constants
 */

public static final double  TURNING_SPEED		      = 0.4,
                            TURNING_MIN			      = 0.4;

public static final double  SLOWDRIVE_CONSTANT		      = 1;

public static final int	    JOYSTICK_AXIS_DRIVE_LEFT	      = 1,
			    JOYSTICK_AXIS_DRIVE_RIGHT 	      = 5;

public static final int	    PORT_DRIVETRAIN_LEFT_FR	      = 1,
                            PORT_DRIVETRAIN_LEFT_BACK	      = 2, 
                            PORT_DRIVETRAIN_RIGHT_FR 	      = 3, 
                            PORT_DRIVETRAIN_RIGHT_BACK 	      = 4;

public static final int	    PORT_ENCODER_LEFT_1		      = 0,
                            PORT_ENCODER_LEFT_2		      = 1, 
                            PORT_ENCODER_RIGHT_1	      = 2, 
                            PORT_ENCODER_RIGHT_2 	      = 3;

public static final int	    PORT_ANALOG_GYRO		      = 0;

/*
 * Shooter Constants
 */

public static final double  SHOOTER_INTAKE_SPEED	      = 0.5,
                            SHOOTER_ZERO		      = 2.045,
			    SHOOTER_INTAKE_ANGLE	      = -9;

// min shooting angle is 21

public static final int	    PORT_SHOOTER_LEFT		      = 7,
                            PORT_SHOOTER_RIGHT		      = 8, 
                            PORT_SHOOTER_ADJUSTER	      = 5,
                            PORT_SHOOTER_ENCODER	      = 1,
                            PORT_SHOOTER_LATCH		      = 4,
                            PORT_SHOOTER_PUSHER		      = 5,
                            SERVO_PULLOUT_GAME		      = 50;

public static final double  SERVO_DELAY			      = 0.5;

/*
 * Arm Constants
 */

public static final int	    PORT_ARM_ADJUSTER		      = 9,
                            PORT_ARM_INTAKE 		      = 10,
                            PORT_ARM_ENC		      = 3,
                            ARM_INTAKE_ANGLE		      = 182, // 182
                            ARM_INITIAL_POS_ANGLE	      = 45,
                            ARM_GROUND_ANGLE  		      = 168,
                            ARM_MAX_ANGLE		      = 200; // 200


public static final double  ARM_ZERO			      = 0.071,
			    ARM_INTAKE_SPEED 		      = 1;


/*
 * Autonomous Constants
 */

public static final String  AUTONOMOUS_FILE		      = "/home/lvuser/recordedAuto";

// 96 inches from field to outer-works this kid claims... http://www.chiefdelphi.com/forums/showthread.php?t=144448
// this may need to be higher! 150?
public static final double DISTANCE_TO_OUTERWORKS	      = 96.5;

/*
 * SmartShoot Constants
 */

public static final int	    SMARTSHOOT_X_THRESHOLD	      = 15, // old
                            SMARTSHOOT_Y_THRESHOLD	      = 20; // old

public static final double  SMARTSHOOT_TURN_SPEED	      = 0.41;

public static final double  SMARTSHOOT_SPINUP_DELAY	      = 0.4;


/*
 * Misc Constants
 */

public static final boolean XBOX_SCHEMA			      = false;

public static final boolean ANNOY_THE_DRIVERS 		      = true,
			    USE_TOO_MUCH_BANDWIDTH	      = false;
}
