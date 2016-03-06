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
public class PIDVisionShooter extends Command {

    private PIDController pid = new PIDController(RobotMap.PID_SHOOTER_Kp,
            RobotMap.PID_SHOOTER_Ki,
            RobotMap.PID_SHOOTER_Kd, -0.5, 0.5, 1); // creates PID controller with min, max, and tolerance
    
    double angle;

    public PIDVisionShooter() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
//	requires(Robot._shooter); // commented
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	angle = SmartDashboard.getNumber("altitude", 0);
	pid.setSetpoint(angle);
	System.out.println("Value: " + SmartDashboard.getNumber("altitude"));
	// disengage clutch
//	Robot._shooter.setAdjusterSpeed(0.2); // commented
	Timer.delay(0.01);
//	Robot._shooter.setClutchServo(30); // commented
	Timer.delay(RobotMap.SERVO_DELAY);
//	Robot._shooter.setAdjusterSpeed(0); // commented
	Timer.delay(0.01);
	pid.reset();
	pid.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//	double angleError = angle - Robot._shooter.getShooterAngle(); // commented
//	if (angleError > 0) {
////	    pid.calculateDebug(Robot._shooter.getShooterAngle(), true); // commented
//
//	    double power = pid.getOutput();
////	    Robot._shooter.setAdjusterSpeed(power); // commented
//	} else {
////	    pid.calculateDebug(-Robot._shooter.getShooterAngle(), true); // commented
//
//	    double power = -pid.getOutput();
//	    if(angle < 0) {
//		Robot._shooter.setAdjusterSpeed(power * 1.8);
//	    } else {
//		Robot._shooter.setAdjusterSpeed(power * 0.2);
//	    }
//	} // commented
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	// engage clutch latch
//	Robot._shooter.setClutchServo(0);
//	Robot._shooter.setAdjusterSpeed(0); // commented
	pid.reset();
	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
