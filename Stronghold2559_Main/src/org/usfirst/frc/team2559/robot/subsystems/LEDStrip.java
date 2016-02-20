package org.usfirst.frc.team2559.robot.subsystems;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.SendLEDState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDStrip extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static int _curMode = 0;
	I2C Wire = new I2C(Port.kOnboard, 4);
	byte[] toSend = new byte[1];
	static int previous_state = -1, current_state = 0;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new SendLEDState());
    }
    
    public void setMode(int num) {
    	_curMode = num;
    }
    
    public void setMode() {
    	if(DriverStation.getInstance().isAutonomous()) {
			_curMode = RobotMap.AUTONOMOUS_ID;
		} else if(DriverStation.getInstance().isOperatorControl()){
			_curMode = RobotMap.TELEOP_ID;
		} else if(DriverStation.getInstance().isDisabled()) {
			_curMode = RobotMap.DISABLED_ID;
		}
    	
    	if(_curMode == RobotMap.TELEOP_ID) {
    		DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
			if(alliance == DriverStation.Alliance.Blue) {
				_curMode = RobotMap.TELEOP_BLUE_ID;
				//System.out.println("alliance == blue");
			} else if(alliance == DriverStation.Alliance.Red) {
				_curMode = RobotMap.TELEOP_RED_ID;
				//System.out.println("alliance == red");
			} else if(alliance == DriverStation.Alliance.Invalid) {
				_curMode = RobotMap.TELEOP_INVALID_ID;
				//System.out.println("alliance == invalid");
			} else {
				_curMode = RobotMap.TELEOP_WHAT_ID;
			}
    	}
    	//System.out.println("Current mode: " + _curMode);
    }
    
    public int getMode() {
    	this.setMode();
    	return _curMode;
    }
    
    public void sendData(int data) {
    	toSend[0] = (byte)data;
        Wire.transaction(toSend, 1, null, 0);
    }
    
}

