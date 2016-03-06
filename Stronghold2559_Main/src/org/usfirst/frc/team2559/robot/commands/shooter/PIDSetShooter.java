package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.lib.PIDController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDSetShooter extends Command {

    private PIDController pid = new PIDController(RobotMap.PID_SHOOTER_Kp,
            RobotMap.PID_SHOOTER_Ki,
            RobotMap.PID_SHOOTER_Kd, -0.75, 0.75, 1); // creates PID controller with min, max, and tolerance
    
    double angle;
    boolean delay = false;

    public PIDSetShooter(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
	this.angle = angle;
	pid.setSetpoint(angle);
	System.out.println("Angle in constructor is: " + angle);
    }
    
    public PIDSetShooter(double angle, boolean delay) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
	this.angle = angle;
	this.delay = delay;
	pid.setSetpoint(angle);
	System.out.println("Angle in constructor is: " + angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	System.out.println("Value: " + SmartDashboard.getNumber("altitude"));
	// disengage clutch
	Robot._shooter.setAdjusterSpeed(0.2);
	Timer.delay(0.01);
	Robot._shooter.setClutchServo(30);
	Timer.delay(RobotMap.SERVO_DELAY);
	Robot._shooter.setAdjusterSpeed(0);
	Timer.delay(0.01);
	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	double angleError = angle - Robot._shooter.getShooterAngle();
	if (angleError > 0) {
	    pid.calculateDebug(Robot._shooter.getShooterAngle(), true);

	    double power = pid.getOutput();
	    Robot._shooter.setAdjusterSpeed(power);
	} else {
	    pid.calculateDebug(-Robot._shooter.getShooterAngle(), true);

	    double power = -pid.getOutput();
	    if(angle < 0) {
		Robot._shooter.setAdjusterSpeed(power * 1.8);
	    } else {
		Robot._shooter.setAdjusterSpeed(power * 0.2);
	    }
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	// Delay for shooter bounce
	if(delay) {
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
