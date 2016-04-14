package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDSetShooter extends Command {

    // doesn't work for negative numbers

    private PIDControllerRT pid;		// creates PID controller with min, max, and tolerance

    double		  angle;
    boolean		 delay = false, hasSetClutchServo = false, hasStoppedAdjuster = false;
    Long		    startTime;

    /**
     * Class constructor specifying the angle to move the shooter to.
     * 
     * @param angle the angle to align the shooter with
     */
    public PIDSetShooter(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
	this.angle = angle;
	pid = new PIDControllerRT(RobotMap.PID_SHOOTER_Kp,
	        RobotMap.PID_SHOOTER_Ki,
	        RobotMap.PID_SHOOTER_Kd, -0.9, 0.9, 1, true);
	pid.setSetpoint(angle);
    }

    /**
     * Class constructor that allows the added functionality of setting a delay after we finish aligning but before putting the clutch back in.
     * This is useful for letting the shooter "bounce" or go as low as it can (for intake).
     * 
     * @param angle the angle to align the shooter with
     * @param delay should there be a delay?
     */
    public PIDSetShooter(double angle, boolean delay) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
	this.angle = angle;
	this.delay = delay;
	pid = new PIDControllerRT(RobotMap.PID_SHOOTER_Kp,
	        RobotMap.PID_SHOOTER_Ki,
	        RobotMap.PID_SHOOTER_Kd, -0.7, 0.7, 1, true);
	pid.setSetpoint(angle);
    }

    /**
     * Class constructor that allows setting the minimum and maximum values that the PID controller should output.
     * Use this if you want to bypass the built-in speed limiting for the shooter adjuster.
     * 
     * @param angle the angle to align the shooter with
     * @param delay should there be a delay?
     * @param min maximum backwards speed, range of -1 to 0
     * @param max maximum forwards speed, range of 0 to 1
     */

    public PIDSetShooter(double angle, boolean delay, double min, double max) {
	requires(Robot._shooter);
	this.angle = angle;
	this.delay = delay;
	pid = new PIDControllerRT(RobotMap.PID_SHOOTER_Kp,
	        RobotMap.PID_SHOOTER_Ki,
	        RobotMap.PID_SHOOTER_Kd, min, max, 1, true);
	pid.setSetpoint(angle);
    }

    /**
     * Class constructor that allows the most verbose customization. Use this to customize the PID controller used.
     * Use this if you want to change the speed and tolerance of the PID controller.
     * 
     * @param angle the angle to align the shooter with
     * @param delay should there be a delay?
     * @param min maximum backwards speed, range of -1 to 0
     * @param max maximum forwards speed, range of 0 to 1
     * @param tolerance how accurate the PID controller must be to be considered done, if the tolerance is 1 then there is a range of two degrees
     */
    public PIDSetShooter(double angle, boolean delay, double min, double max, int tolerance) {
	requires(Robot._shooter);
	this.angle = angle;
	this.delay = delay;
	pid = new PIDControllerRT(RobotMap.PID_SHOOTER_Kp,
	        RobotMap.PID_SHOOTER_Ki,
	        RobotMap.PID_SHOOTER_Kd, min, max, tolerance, true);
	pid.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	startTime = System.currentTimeMillis();
	// disengage clutch
	Robot._shooter.setAdjusterSpeed(0.4);

	try {
	    Thread.sleep(20);
	    Robot._shooter.setClutchServo(RobotMap.SERVO_PULLOUT_GAME);
	    Thread.sleep(500);
	    Robot._shooter.setAdjusterSpeed(0);
	    pid.reset();

	}
	catch (Exception e)
	{
	    
	}
	
	
//	Timer.delay(0.02);
//	Robot._shooter.setClutchServo(RobotMap.SERVO_PULLOUT_GAME);
//	Timer.delay(RobotMap.SERVO_DELAY);
//	Robot._shooter.setAdjusterSpeed(0);
//	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//	if (System.currentTimeMillis() > startTime + 20 && !hasSetClutchServo) {
//	    Robot._shooter.setClutchServo(RobotMap.SERVO_PULLOUT_GAME);
//	    hasSetClutchServo = true;
//	    startTime = System.currentTimeMillis();
//	}
//	
//	if (System.currentTimeMillis() > startTime + 500 && !hasStoppedAdjuster) {
//		Robot._shooter.setAdjusterSpeed(0);
//		hasStoppedAdjuster = true;
//	}
	
//	if (hasSetClutchServo && hasStoppedAdjuster) {
	    pid.calculate(Robot._shooter.getShooterAngle(), true);
	    double power = pid.getOutput();
	    if (power >= 0)
		Robot._shooter.setAdjusterSpeed(power);
	    else
		Robot._shooter.setAdjusterSpeed(power * 0.5);
//	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	// Delay for shooter bounce
	if (delay) {
	    Timer.delay(0.5);
	}
	// engage clutch latch
	Robot._shooter.setClutchServo(0);
	Robot._shooter.setAdjusterSpeed(0);
	pid.reset();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
