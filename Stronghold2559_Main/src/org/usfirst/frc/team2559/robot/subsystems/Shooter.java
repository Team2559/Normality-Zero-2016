package org.usfirst.frc.team2559.robot.subsystems;

import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Shooter() {
    	this.invertMotors();
    }
    
	private final Talon 		_left = new Talon(RobotMap.PORT_SHOOTER_LEFT),
								_right = new Talon(RobotMap.PORT_SHOOTER_RIGHT);
	
	private final Servo			_pusher = new Servo(0);
	
	private final CANTalon _adjuster = new CANTalon(RobotMap.PORT_SHOOTER_ADJUSTER);
    
    public double getXOffset() {
    	// i'm a one-liner god
    	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerXOffset", 0) : 0;
    }
    
    public double getYOffset() {
    	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerYOffset", 0) : 0;
    }
    
    public void setSpinSpeed(double left, double right) {
    	_left.set(left);
    	_right.set(right);
    }
    
    public void invertMotors() {
    	//_left.setInverted(true);
    	_right.setInverted(true);
    }
    
    public void setAdjusterSpeed(double speed) {
    	_adjuster.set(speed);
    }
    
    public void intakeIn() {
    	this.setSpinSpeed(RobotMap.SHOOTER_INTAKE_SPEED, RobotMap.SHOOTER_INTAKE_SPEED);
    }
    
    public void intakeOut() {
    	this.setSpinSpeed(-RobotMap.SHOOTER_INTAKE_SPEED, -RobotMap.SHOOTER_INTAKE_SPEED);
    }
    
    public void intakeStop() {
    	this.setSpinSpeed(0, 0);
    }
    
    public double getLeftShooterMotor() {
    	return _left.get();
    }
    
    public double getRightShooterMotor() {
    	return _right.get();
    }
    
    public void setFiringServo(double val) {
    	_pusher.set(1);
    }

	public double getFiringServo() {
		return _pusher.get();
	}
}

