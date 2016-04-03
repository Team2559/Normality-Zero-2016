package org.usfirst.frc.team2559.robot.commands.arm;

import org.usfirst.frc.team2559.lib.PIDTurnController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDSetArm_Overshoot extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    double angle;

//   private PIDTurnController       pid	       = new PIDTurnController(RobotMap.PID_ARM_Kp, RobotMap.PID_ARM_Ki, RobotMap.PID_ARM_Kd, -1, 1, 0.5, true); // creates
   private PIDTurnController       pid	       = new PIDTurnController(RobotMap.PID_ARM_Kp, RobotMap.PID_ARM_Ki, RobotMap.PID_ARM_Kd, -0.65, 0.65, 0.5, true, 25, 10, 0.8, 0.7);
    // 0.5, and .5 as min/max/tolerance

    public PIDSetArm_Overshoot(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	pid.reset();
	pid.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
