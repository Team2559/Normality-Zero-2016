package org.usfirst.frc.team2559.robot.subsystems;

import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final Talon 	_adjuster = new Talon(RobotMap.PORT_ARM_ADJUSTER),
							_intake = new Talon(RobotMap.PORT_ARM_INTAKE);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setIntakeSpeed(double speed) {
    	_intake.set(speed);
    }
    
    public void setArmSpeed(double speed) {
    	_adjuster.set(speed);
    }
    
    public void intakeIn() {
    	this.setIntakeSpeed(RobotMap.ARM_INTAKE_SPEED);
    }
    
    public void intakeOut() {
    	this.setIntakeSpeed(-RobotMap.ARM_INTAKE_SPEED);
    }
    
    public void intakeStop() {
    	this.setIntakeSpeed(0);
    }
    
}

