package org.usfirst.frc.team2559.robot.subsystems;

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
    
    public double getXOffset() {
    	// i'm a one-liner god
    	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerXOffset", 0) : 0;
    }
    
    public double getYOffset() {
    	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerYOffset", 0) : 0;
    }
}

