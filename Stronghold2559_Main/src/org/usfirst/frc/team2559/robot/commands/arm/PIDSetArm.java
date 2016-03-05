package org.usfirst.frc.team2559.robot.commands.arm;

import org.usfirst.frc.team2559.lib.PIDController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDSetArm extends Command {

    private PIDController pid = new PIDController(RobotMap.PID_ARM_Kp,
            RobotMap.PID_ARM_Ki,
            RobotMap.PID_ARM_Kd, -0.4, 0.4, 1); // creates PID controller with min, max, and tolerance
    
    double angle;

    public PIDSetArm(double angle) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._arm);
	this.angle = angle;
	pid.setSetpoint(angle);
	System.out.println("Angle in arm constructor is: " + angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	pid.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	double angleError = angle - Robot._arm.getArmAngle();
	if (angleError > 0) {
	    pid.calculateDebug(-Robot._arm.getArmAngle(), true);

	    double power = pid.getOutput();
	    Robot._arm.setAdjusterSpeed(-power);
	} else {
	    pid.calculateDebug(Robot._arm.getArmAngle(), true);

	    double power = -pid.getOutput();
	    Robot._arm.setAdjusterSpeed(power);
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return pid.isDone();
    }

    // Called once after isFinished returns true
    protected void end() {
	pid.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}