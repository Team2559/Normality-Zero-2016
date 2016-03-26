package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireServo extends Command {
	
	Long startTime;

    public FireServo() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot._shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
		if (Robot._shooter.getPusherServo() >= 10) {
			Robot._shooter.setFiringServo(1);
			Timer.delay(RobotMap.SERVO_DELAY);
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot._shooter.setFiringServo(0);
    	Timer.delay(RobotMap.SERVO_DELAY);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot._shooter.setFiringServo(1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
