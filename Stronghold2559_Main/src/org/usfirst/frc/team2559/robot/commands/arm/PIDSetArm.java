package org.usfirst.frc.team2559.robot.commands.arm;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class PIDSetArm extends Command {

    private PIDControllerRT pid;	// creates PID controller with min, max, and tolerance

    double		  angle;

    /**
     * Class constructor for a PID controller to move the arm to a certain degree.
     * 0 degrees is as far back it can go (i.e. lowbar configuration) without hurting the robot. 240 degrees is the position the robot is at when it does a pushup (i.e. endgame pushup).
     * Defaults to a PID controller with 40% speed and +/- 1 degree of tolerance (range of 2 degrees).
     * 
     * @param angle angle to move the arm to
     */
    public PIDSetArm(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	this.angle = angle;
	pid = new PIDControllerRT(RobotMap.PID_ARM_Kp,
	        RobotMap.PID_ARM_Ki,
	        RobotMap.PID_ARM_Kd, -0.5, 0.5, 1, true);
	pid.setSetpoint(angle);
    }

    /**
     * Class constructor for a PID controller to move the arm to a certain degree.
     * 0 degrees is as far back it can go (i.e. lowbar configuration) without hurting the robot. 240 degrees is the position the robot is at when it does a pushup (i.e. endgame pushup).
     * Defaults to a PID controller with +/- 1 degree of tolerance (range of 2 degrees).
     * 
     * @param angle angle to move the arm to
     * @param min maximum backwards speed, range of -1 to 0
     * @param max maximum forwards speed, range of 0 to 1
     */
    public PIDSetArm(double angle, double min, double max) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	this.angle = angle;
	pid = new PIDControllerRT(RobotMap.PID_ARM_Kp,
	        RobotMap.PID_ARM_Ki,
	        RobotMap.PID_ARM_Kd, min, max, 1, true);
	pid.setSetpoint(angle);
    }

    /**
     * Class constructor for a PID controller to move the arm to a certain degree.
     * 0 degrees is as far back it can go (i.e. lowbar configuration) without hurting the robot. 240 degrees is the position the robot is at when it does a pushup (i.e. endgame pushup).
     * 
     * @param angle angle to move the arm to
     * @param min maximum backwards speed, range of -1 to 0
     * @param max maximum forwards speed, range of 0 to 1
     * @param tolerance how accurate the PID controller must be to be considered done, if the tolerance is 1 then there is a range of two degrees
     */
    public PIDSetArm(double angle, double min, double max, int tolerance) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	this.angle = angle;
	pid = new PIDControllerRT(RobotMap.PID_ARM_Kp,
	        RobotMap.PID_ARM_Ki,
	        RobotMap.PID_ARM_Kd, min, max, tolerance, true);
	pid.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//	double angleError = angle - Robot._arm.getArmAngle();
//	if (angleError > 0) {
//	    pid.calculateDebug(Robot._arm.getArmAngle(), true);
//
//	    double power = pid.getOutput();
//	    Robot._arm.setAdjusterSpeed(power);
//	} else {
//	    pid.calculateDebug(-Robot._arm.getArmAngle(), true);
//
//	    double power = -pid.getOutput();
//	    Robot._arm.setAdjusterSpeed(power);
//	}
	pid.calculate(Robot._arm.getArmAngle(), true);
	double power = pid.getOutput();
	Robot._arm.setAdjusterSpeed(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot._arm.setAdjusterSpeed(0);
	pid.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}