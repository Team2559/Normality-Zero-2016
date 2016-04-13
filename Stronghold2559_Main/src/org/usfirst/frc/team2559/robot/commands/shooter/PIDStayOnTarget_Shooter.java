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
public class PIDStayOnTarget_Shooter extends Command {

    private PIDControllerRT pid	= new PIDControllerRT(RobotMap.PID_SHOOTER_Kp,
            RobotMap.PID_SHOOTER_Ki,
            RobotMap.PID_SHOOTER_Kd, -0.5, 0.6, 1, true);		      // creates PID controller with min, max, and tolerance

    double		    angle, prevAngle;

    public PIDStayOnTarget_Shooter() {
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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	angle = SmartDashboard.getNumber("angle", 0);
	pid.setSetpoint(angle);
	pid.calculate(Robot._shooter.getShooterAngle(), true);
	double power = pid.getOutput();
	if(power >= 0)
	    Robot._shooter.setAdjusterSpeed(power);
	else
	    Robot._shooter.setAdjusterSpeed(power * 0.2);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
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
