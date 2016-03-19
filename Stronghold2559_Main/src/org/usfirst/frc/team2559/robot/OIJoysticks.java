package org.usfirst.frc.team2559.robot;

import org.usfirst.frc.team2559.robot.commands.Cancel;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.LowGoal;
import org.usfirst.frc.team2559.robot.commands.PIDVisionTurn;
import org.usfirst.frc.team2559.robot.commands.PrepIntake;
import org.usfirst.frc.team2559.robot.commands.SetCamera;
import org.usfirst.frc.team2559.robot.commands.SetIntake;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.drive.DobbyBackward;
import org.usfirst.frc.team2559.robot.commands.drive.DobbyForward;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn766;
import org.usfirst.frc.team2559.robot.commands.recorder.CreateRecording;
import org.usfirst.frc.team2559.robot.commands.recorder.PlayRecording;
import org.usfirst.frc.team2559.robot.commands.shooter.AdjustShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.AlignWithTarget;
import org.usfirst.frc.team2559.robot.commands.shooter.DumbShoot;
import org.usfirst.frc.team2559.robot.commands.shooter.FireServo;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSmartShoot;
import org.usfirst.frc.team2559.robot.commands.shooter.SetShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.SmartShoot;
import org.usfirst.frc.team2559.robot.commands.shooter.SpinForSeconds;
import org.usfirst.frc.team2559.robot.triggers.POVTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OIJoysticks {
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
	
	
	Joystick driverStick1 = new Joystick(0);
	Joystick driverStick2 = new Joystick(1);
	Joystick shooterStick = new Joystick(2);
	
	Button 	_fast = new JoystickButton(driverStick1, 1),
			_lowgoal = new JoystickButton(driverStick1, 2),
			_forw = new JoystickButton(driverStick1, 3),
			_back = new JoystickButton(driverStick1, 4),
			_bringItIn = new JoystickButton(driverStick1, 5),
			_lowbar = new JoystickButton(driverStick1, 6),
			_portcullis = new JoystickButton(driverStick1, 7),
			_chival = new JoystickButton(driverStick1, 8),
			_moat = new JoystickButton(driverStick1, 9),
			_ramparts = new JoystickButton(driverStick1, 10),
			_drawbridge = new JoystickButton(driverStick1, 11),
			_sallyport = new JoystickButton(driverStick1, 12);
	
	Button 	_smartShoot = new JoystickButton(driverStick2, 1),
			_align = new JoystickButton(driverStick2, 2),
			_intakeOn = new JoystickButton(driverStick2, 3),
			_spinup = new JoystickButton(driverStick2, 4),
			_prepIntake = new JoystickButton(driverStick2, 5),
			_dumbShoot = new JoystickButton(driverStick2, 6),
			driverStick2Button7 = new JoystickButton(driverStick2, 7),
			driverStick2Button8 = new JoystickButton(driverStick2, 8),
			_rockwall = new JoystickButton(driverStick2, 9),
			_roughterrain = new JoystickButton(driverStick2, 10),
			_shooterLowBar = new JoystickButton(driverStick2, 11),
			_endgame = new JoystickButton(driverStick2, 12);

	Button 	_arm = new JoystickButton(shooterStick, 1),
			_bringItBack = new JoystickButton(shooterStick, 2),
			_cancel = new JoystickButton(shooterStick, 3),
			_resetAll = new JoystickButton(shooterStick, 4),
			_stopRecording = new JoystickButton(shooterStick, 5),
			_debugShooter = new JoystickButton(shooterStick, 6),
			shooterButton7= new JoystickButton(shooterStick, 7),
			shooterButton8 = new JoystickButton(shooterStick, 8),
			shooterButton9 = new JoystickButton(shooterStick, 9),
			shooterButton10 = new JoystickButton(shooterStick, 10);
	
	Trigger _driver1POVUp = new POVTrigger(driverStick1, true);
	Trigger _driver1POVDown = new POVTrigger(driverStick1, false);
	
	Trigger _driver2POVUp = new POVTrigger(driverStick2, true);
	Trigger _driver2POVDown = new POVTrigger(driverStick2, false);
	
	public OIJoysticks() {
		_forw.whileHeld(new DobbyForward());
		_back.whileHeld(new DobbyBackward());
		_lowgoal.whenPressed(new LowGoal());
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
		_bringItIn.whenPressed(new PIDSetShooter(0));

		_align.whenPressed(new PIDVisionTurn());
		
		_smartShoot.whenPressed(new PIDSmartShoot());
		
		_lowbar.whenPressed(new GetReadyToRumble(RobotMap.LOWBAR_ID));
		_shooterLowBar.whenPressed(new GetReadyToRumble(RobotMap.LOWBAR_ID));
		_portcullis.whenPressed(new GetReadyToRumble(RobotMap.PORTCULLIS_ID));
		_chival.whenPressed(new GetReadyToRumble(RobotMap.CDF_ID));
		_moat.whenPressed(new GetReadyToRumble(RobotMap.MOAT_ID));
		_ramparts.whenPressed(new GetReadyToRumble(RobotMap.RAMPARTS_ID));
		_drawbridge.whenPressed(new GetReadyToRumble(RobotMap.DRAWBRIDGE_ID));
		_sallyport.whenPressed(new GetReadyToRumble(RobotMap.SALLYPORT_ID));
		_rockwall.whenPressed(new GetReadyToRumble(RobotMap.ROCKWALL_ID));
		_roughterrain.whenPressed(new GetReadyToRumble(RobotMap.ROUGHTERRAIN_ID));
		_endgame.whenPressed(new GetReadyToRumble(RobotMap.PUSHUP_ENDGAME_ID));
		
		_spinup.whenPressed(new SpinForSeconds(3));
		_prepIntake.whenPressed(new PrepIntake());
		_intakeOn.whenPressed(new SetIntake("in"));
		_intakeOn.whenReleased(new SetIntake("off"));
		_dumbShoot.whenPressed(new DumbShoot());
		
		_arm.whileHeld(new Command() {
			protected void initialize() {
				Robot._arm.setAdjusterSpeed(shooterStick.getRawAxis(1) * 0.3);
			}
			protected void execute() {
			}
			protected boolean isFinished() {
				return true;
			}
			protected void end() {
			}
			protected void interrupted() {
				end();
			}
		});
		_bringItBack.whenPressed(new PIDSetShooter(0));
		_resetAll.whenPressed(new Command() {
			protected void initialize() {
				Robot._shooter.setFiringServo(0);
				Robot._shooter.setClutchServo(0);
			}
			protected void execute() {
			}
			protected boolean isFinished() {
				return true;
			}
			protected void end() {
			}
			protected void interrupted() {
				end();
			}
		});
		
		shooterButton7.whenPressed(new PIDSetArm(30));
		_debugShooter.whileHeld(new Command() {
			protected void initialize() {
				Robot._shooter.setAdjusterSpeed(shooterStick.getRawAxis(1));
			}
			protected void execute() {
			}
			protected boolean isFinished() {
				return true;
			}
			protected void end() {
			    Robot._shooter.setAdjusterSpeed(0);
			}
			protected void interrupted() {
				end();
			}
		});
		
		shooterButton8.whenPressed(new PIDSetShooter(60));
		_cancel.whenPressed(new Cancel());
		
		_driver2POVUp.whenActive(new AdjustShooter(0.3));
		_driver2POVUp.whenInactive(new AdjustShooter(0));
		
		_driver2POVDown.whenActive(new AdjustShooter(-0.3));
		_driver2POVDown.whenInactive(new AdjustShooter(0));
		
		_driver1POVUp.whenActive(new SetCamera(true));
		_driver1POVDown.whenActive(new SetCamera(false));
		
		
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
	
	public int getPOVValue() {
		return driverStick1.getPOV();
	}
	
	public Joystick getLeftDriverJoystick() {
		return driverStick1;
	}
	
	public Joystick getRightDriverJoystick() {
		return driverStick2;
	}

	/**
	 * Returns the left joystick drive value.
	 * @return left joystick value
	 */
	public double getLeftDrive() {
		return _zeroDeadzone(-driverStick1.getRawAxis(1), 0.1);
	}
	
	/**
	 * Returns the right joystick drive value.
	 * @return right joystick value
	 */
	public double getRightDrive() {
		return _zeroDeadzone(-driverStick2.getRawAxis(1), 0.1);
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
		shooterStick.setRumble(type, num);
	}
	
	/**
	 * This is meant to be used in tandem with the recorder subsystem.
	 * @return value of recorder button
	 */
	public boolean getRecorderStopButton() {
		return _stopRecording.get();
	}
}