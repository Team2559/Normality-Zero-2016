package org.usfirst.frc.team2559.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class XboxTrigger extends Trigger {

    Joystick joystick;
    boolean  left;

    public XboxTrigger(Joystick joy, boolean left) {
	joystick = joy;
	this.left = left;
    }

    public boolean get() {
	if (left) {
	    return joystick.getRawAxis(2) > 0;
	} else {
	    return joystick.getRawAxis(3) > 0;
	}
    }
}
