package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.lib.PIDControllerRT;
import org.usfirst.frc.team2559.lib.PIDTurnController;
import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDStayOnTarget_Drive extends Command {

    private static final double ANGLES_TO_DEGREES = 1;
    double angle;

   private PIDTurnController       pid	       = new PIDTurnController(RobotMap.PID_TURN_Kp, RobotMap.PID_TURN_Ki, RobotMap.PID_TURN_Kd, -1, 1, 1, true); // creates

    // 0.5, and .5 as min/max/tolerance

    public PIDStayOnTarget_Drive() {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot._driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	angle = SmartDashboard.getNumber("azimuth", 0);
	Robot._driveTrain.setAuton(true);
	Robot._driveTrain.setFastDrive(false);
	Robot._driveTrain.setReverseDrive(false);
	Robot._driveTrain.setSlowDrive(false);
	Robot._driveTrain.clearGyro();
	pid.reset();
	pid.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute(){
	angle = SmartDashboard.getNumber("azimuth", 0);
	pid.setSetpoint(angle);
	pid.calculate(Robot._driveTrain.getGyroAngle(), true);
	double power = pid.getOutput() * ANGLES_TO_DEGREES;
	Robot._driveTrain.tankDrive(power, -power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot._driveTrain.tankDrive(0, 0);
	Robot._driveTrain.setAuton(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	end();
    }
}
