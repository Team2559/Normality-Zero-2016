package org.usfirst.frc.team2559.robot;

import org.usfirst.frc.team2559.robot.commands.SetIntake;
import org.usfirst.frc.team2559.robot.commands.drive.DobbyBackward;
import org.usfirst.frc.team2559.robot.commands.drive.DobbyForward;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn;
import org.usfirst.frc.team2559.robot.commands.recorder.CreateRecording;
import org.usfirst.frc.team2559.robot.commands.recorder.PlayRecording;
import org.usfirst.frc.team2559.robot.commands.shooter.AlignWithTarget;
import org.usfirst.frc.team2559.robot.commands.shooter.DumbShoot;
import org.usfirst.frc.team2559.robot.commands.shooter.SmartShoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OIXbox {
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
	
	Button 	_align = new JoystickButton(driverStick, 1),
			_back = new JoystickButton(driverStick, 2),
			_forw = new JoystickButton(driverStick, 3),
			_turn = new JoystickButton(driverStick, 4),
			_fast = new JoystickButton(driverStick, 5),
			_intakeOn = new JoystickButton(driverStick, 6),
			driverButton7 = new JoystickButton(driverStick, 7),
			driverButton8 = new JoystickButton(driverStick, 8),
			driverButton9 = new JoystickButton(driverStick, 9),
			driverButton10 = new JoystickButton(driverStick, 10);

	Button 	_smartShoot = new JoystickButton(shooterStick, 1),
			_stopRecording = new JoystickButton(shooterStick, 2),
			_dumbShoot = new JoystickButton(shooterStick, 3),
			shooterButton4 = new JoystickButton(shooterStick, 4),
			_play1 = new JoystickButton(shooterStick, 5),
			shooterButton6 = new JoystickButton(shooterStick, 6),
			_record1 = new JoystickButton(shooterStick, 7),
			shooterButton8 = new JoystickButton(shooterStick, 8),
			shooterButton9 = new JoystickButton(shooterStick, 9),
			shooterButton10 = new JoystickButton(shooterStick, 10),
			shooterButton11 = new JoystickButton(shooterStick, 11),
			shooterButton12 = new JoystickButton(shooterStick, 12);
	
	public OIXbox() {
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

		_align.whenPressed(new AlignWithTarget());
		
		_smartShoot.whenPressed(new SmartShoot());
		
		_record1.whenPressed(new CreateRecording("1"));
		_play1.whenPressed(new PlayRecording("1"));
		
		_dumbShoot.whenPressed(new DumbShoot());
		_intakeOn.whenPressed(new SetIntake("in"));
		_intakeOn.whenReleased(new SetIntake("off"));
	}
	
	/**
	 * Allows accounting for the deadzone that exists in joysticks.
	 * @param joyValue the value of the joystick
	 * @param dead the minimum joystick value to discard input under
	 * @return 0 if less than dead
	 */
	public double _zeroDeadzone(double joyValue, double dead) {
		return Math.abs(joyValue) > dead ? joyValue : 0;
	}

	/**
	 * Returns the left joystick drive value.
	 * @return left joystick value
	 */
	public double getLeftDrive() {
		return _zeroDeadzone(-driverStick.getRawAxis(RobotMap.JOYSTICK_AXIS_DRIVE_LEFT), 0.1);
	}

	/**
	 * Returns the right joystick drive value.
	 * @return right joystick value
	 */
	public double getRightDrive() {
		return _zeroDeadzone(-driverStick.getRawAxis(RobotMap.JOYSTICK_AXIS_DRIVE_RIGHT), 0.1);
		}
	
	/**
	 * Returns the slider value of the joystick.
	 * @return slider value of a Logitech joystick
	 */
	public double getSliderVal() {
		return shooterStick.getRawAxis(3);
	}

	/**
	 * This is used to set the rumble of the driver's xbox controller.
	 * @param type left, right, or both sides
	 * @param num intensity of rumble
	 */
	public void setRumble(RumbleType type, float num) {
		driverStick.setRumble(type, num);
	}

	/**
	 * Returns the value of the driver's triggers.
	 * @return driver stick's triggers
	 */
	public double getTrigger() {
		return driverStick.getRawAxis(3);
	}
	
	/**
	 * This is meant to be used in tandem with the recorder subsystem.
	 * @return value of recorder button
	 */
	public boolean getRecorderStopButton() {
		return _stopRecording.get();
	}
}