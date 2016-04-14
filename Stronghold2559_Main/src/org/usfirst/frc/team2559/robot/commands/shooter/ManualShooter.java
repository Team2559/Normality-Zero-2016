package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualShooter extends Command {

    public ManualShooter() {
	requires(Robot._shooter);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
	// disengage clutch
	Robot._shooter.setAdjusterSpeed(0.4);
	Timer.delay(0.02);
	Robot._shooter.setClutchServo(RobotMap.SERVO_PULLOUT_GAME);
	Timer.delay(RobotMap.SERVO_DELAY);
	Robot._shooter.setAdjusterSpeed(0);
	Timer.delay(0.01);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	double val = Robot.oi.getShooterStickVal();
	if (val > 0)
	    Robot._shooter.setAdjusterSpeed(-Robot.oi.getShooterStickVal()* 0.25);
	else {
	    Robot._shooter.setAdjusterSpeed(-Robot.oi.getShooterStickVal());
	}
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
