package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.lib.PIDController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDAdjustShooter extends Command {

    private PIDController pid = new PIDController(RobotMap.PID_SHOOTER_Kp,
            RobotMap.PID_SHOOTER_Ki,
            RobotMap.PID_SHOOTER_Kd, -0.5, 0.5, .5); // creates PID controller with min, max, and tolerance

    public PIDAdjustShooter(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._shooter);
	pid.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	// disengage clutch
	Robot._shooter.setAdjusterSpeed(1);
	Robot._shooter.setClutchServo(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	pid.calculate(Robot._shooter.getShooterAngle(), false);

	double power = pid.getOutput();
	Robot._shooter.setAdjusterSpeed(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	// engage clutch latch
	Robot._shooter.setAdjusterSpeed(0);
	Robot._shooter.setClutchServo(1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
