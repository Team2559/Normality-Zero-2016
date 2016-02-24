package org.usfirst.frc.team2559.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class POVTrigger extends Trigger {
	
	Joystick joystick;
	boolean up;
	
	public POVTrigger(Joystick joy, boolean up) {
		joystick = joy;
		this.up = up;
	}
    
    public boolean get() {
        if (up) {
        	return joystick.getPOV() >= 60 && joystick.getPOV() <= 120;
        } else {
        	return joystick.getPOV() >= 0 && joystick.getPOV() <= 60;
        }
    }
}
