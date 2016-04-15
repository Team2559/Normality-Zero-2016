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
            RobotMap.PID_SHOOTER_Kd, -0.5, 0.6, 1); // creates PID controller with min, max, and tolerance
    
    double angle;
    boolean hasFinishedFiring = false, hasFired = false;
    Long startTime = 0L;

    public PIDVisionShooter() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	angle = SmartDashboard.getNumber("angle", 0);
	pid.setSetpoint(angle);
	System.out.println("Value: " + SmartDashboard.getNumber("angle"));
	// disengage clutch
	Robot._shooter.setAdjusterSpeed(0.2);
	Timer.delay(0.01);
	Robot._shooter.setClutchServo(RobotMap.SERVO_PULLOUT_GAME);
	Timer.delay(RobotMap.SERVO_DELAY);
	Robot._shooter.setAdjusterSpeed(0);
	Timer.delay(0.01);
	pid.reset();
	pid.setSetpoint(angle);
	startTime = 0L;
	hasFinishedFiring = false;
	hasFired = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	double angleError = angle - Robot._shooter.getShooterAngle();
	if (angleError > 0) {
	    pid.calculate(Robot._shooter.getShooterAngle(), true);

	    double power = pid.getOutput();
	    Robot._shooter.setAdjusterSpeed(power);
	} else {
	    pid.calculate(-Robot._shooter.getShooterAngle(), true);

	    double power = -pid.getOutput();
//	    Robot._shooter.setAdjusterSpeed(power);
	    if(angle < 0) {
		Robot._shooter.setAdjusterSpeed(power * 2);
	    } else {
		Robot._shooter.setAdjusterSpeed(power * 0.05);
	    }
	}
	
	// time to shoot
	
	
	if (pid.isDone() && !hasFinishedFiring) {
	    if (startTime == 0L) {
		startTime = System.currentTimeMillis();
	    }
	    Robot._shooter.setTargetingStatus(false);
	    Robot._shooter.setShootingStatus(true);
	    Robot._shooter.setSpinSpeed(-1, -1);
	    if (System.currentTimeMillis() > startTime + 400 && !hasFired) {
		Robot._shooter.setFiringServo(0);
		hasFired = true;
	    }
	    
	    if (System.currentTimeMillis() > startTime + 800 && hasFired) {
		Robot._shooter.setFiringServo(1);
		Robot._shooter.setSpinSpeed(0, 0);
		Robot._shooter.setShootingStatus(false);
		hasFinishedFiring = true;
	    }  
	    
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone() && hasFinishedFiring && hasFired;
    }

    // Called once after isFinished returns true
    protected void end() {
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
