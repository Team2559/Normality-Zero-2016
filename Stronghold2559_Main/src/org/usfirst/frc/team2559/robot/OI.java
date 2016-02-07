package org.usfirst.frc.team2559.robot;

import org.usfirst.frc.team2559.robot.commands.PIDTurn;
import org.usfirst.frc.team2559.robot.commands.SendLEDState;
import org.usfirst.frc.team2559.robot.commands.DobbyBackward;
import org.usfirst.frc.team2559.robot.commands.DobbyForward;
import org.usfirst.frc.team2559.robot.commands.Turn;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	Joystick driverStick = new Joystick(0);
	Joystick shooterStick = new Joystick(1);
	
	Button 	_180 = new JoystickButton(driverStick, 1),
			_back = new JoystickButton(driverStick, 2),
			_forw = new JoystickButton(driverStick, 3),
			_turn = new JoystickButton(driverStick, 4),
			_fast = new JoystickButton(driverStick, 5),
			driverButton6 = new JoystickButton(driverStick, 6),
			driverButton7 = new JoystickButton(driverStick, 7),
			driverButton8 = new JoystickButton(driverStick, 8),
			driverButton9 = new JoystickButton(driverStick, 9),
			driverButton10 = new JoystickButton(driverStick, 10);

	Button 	shooterButton1 = new JoystickButton(shooterStick, 1),
			shooterButton2 = new JoystickButton(shooterStick, 2),
			shooterButton3 = new JoystickButton(shooterStick, 3),
			shooterButton4 = new JoystickButton(shooterStick, 4),
			shooterButton5 = new JoystickButton(shooterStick, 5),
			shooterButton6 = new JoystickButton(shooterStick, 6),
			shooterButton7 = new JoystickButton(shooterStick, 7),
			shooterButton8 = new JoystickButton(shooterStick, 8),
			shooterButton9 = new JoystickButton(shooterStick, 9),
			shooterButton10 = new JoystickButton(shooterStick, 10),
			shooterButton11 = new JoystickButton(shooterStick, 11),
			shooterButton12 = new JoystickButton(shooterStick, 12);
	
	public OI() {
		_forw.whileHeld(new DobbyForward());
		_back.whileHeld(new DobbyBackward());
		_fast.whileHeld(new Command() {
			protected void initialize() {
				Robot._driveTrain.setFastDrive(true);
			}
			protected void execute() {
			}
			protected boolean isFinished() {
				return false;
			}
			protected void end() {
				Robot._driveTrain.setFastDrive(false);
			}
			protected void interrupted() {
				end();
			}
		});
		_turn.whenPressed(new PIDTurn(90, RobotMap.TURNING_SPEED));
		_180.whenPressed(new PIDTurn(180, RobotMap.TURNING_SPEED));
	}
	
	public double _zeroDeadzone(double joyValue, double dead) {
		return Math.abs(joyValue) > dead ? joyValue : 0;
	}

	public double getLeftDrive() {
		return _zeroDeadzone(-driverStick.getRawAxis(RobotMap.JOYSTICK_AXIS_DRIVE_LEFT), 0.1);
	}

	public double getRightDrive() {
		return _zeroDeadzone(-driverStick.getRawAxis(RobotMap.JOYSTICK_AXIS_DRIVE_RIGHT), 0.1);
		}
	
	public double getSliderVal() {
		return shooterStick.getRawAxis(3);
	}

	public void setRumble(RumbleType type, float num) {
		driverStick.setRumble(type, num);
	}

	public double getTrigger() {
		return driverStick.getRawAxis(3);
	}
}