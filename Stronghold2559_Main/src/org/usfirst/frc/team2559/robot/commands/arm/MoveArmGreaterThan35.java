package org.usfirst.frc.team2559.robot.commands.arm;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArmGreaterThan35 extends Command {

    private PIDControllerRT pid;  // creates PID controller with min, max, and tolerance

    /**
     * Class constructor for a PID controller to move the arm to a certain degree.
     * 0 degrees is as far back it can go (i.e. lowbar configuration) without hurting the robot. 240 degrees is the position the robot is at
     * when it does a pushup (i.e. endgame pushup).
     * Defaults to a PID controller with 40% speed and +/- 1 degree of tolerance (range of 2 degrees).
     * 
     * @param angle angle to move the arm to
     */
    public MoveArmGreaterThan35() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	pid = new PIDControllerRT(RobotMap.PID_ARM_Kp, RobotMap.PID_ARM_Ki, RobotMap.PID_ARM_Kd, -0.55, 0.55, 1, true);
	pid.setSetpoint(35);
    }


    // Called just before this Command runs the first time
    protected void initialize() {
	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	// double angleError = angle - Robot._arm.getArmAngle();
	// if (angleError > 0) {
	// pid.calculateDebug(Robot._arm.getArmAngle(), true);
	//
	// double power = pid.getOutput();
	// Robot._arm.setAdjusterSpeed(power);
	// } else {
	// pid.calculateDebug(-Robot._arm.getArmAngle(), true);
	//
	// double power = -pid.getOutput();
	// Robot._arm.setAdjusterSpeed(power);
	// }
	pid.calculate(Robot._arm.getArmAngle(), true);
	double power = pid.getOutput();
	Robot._arm.setAdjusterSpeed(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return Robot._arm.getArmAngle() >= 35;
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
