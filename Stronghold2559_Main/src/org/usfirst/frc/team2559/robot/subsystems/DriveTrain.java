package org.usfirst.frc.team2559.robot.subsystems;

import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private final CANTalon 		_leftfr = new CANTalon(RobotMap.PORT_DRIVETRAIN_LEFT_FR),
								_leftback = new CANTalon(RobotMap.PORT_DRIVETRAIN_LEFT_BACK),
								_rightfr = new CANTalon(RobotMap.PORT_DRIVETRAIN_RIGHT_FR),
								_rightback = new CANTalon(RobotMap.PORT_DRIVETRAIN_RIGHT_BACK);

	private final RobotDrive	_drive = new RobotDrive(_leftfr, _leftback, _rightfr, _rightback);

	private final AnalogGyro	_gyro = new AnalogGyro(RobotMap.PORT_ANALOG_GYRO);
	
	// If encoders aren't working, they might need to be negative
	private final Encoder 		_leftEncoder = new Encoder(RobotMap.PORT_ENCODER_LEFT_1, 
								RobotMap.PORT_ENCODER_LEFT_2);
	private final Encoder 		_rightEncoder = new Encoder(RobotMap.PORT_ENCODER_RIGHT_1, 
								RobotMap.PORT_ENCODER_RIGHT_2);

	private final Accelerometer _accel = new BuiltInAccelerometer();
	private double _gyroOffset = 0;

	boolean reverseDrive = false, 
			slowDrive = false, 
			fastDrive = false,
			auton = false;

	private boolean cameraSettingsOne = true;
	
	double lastLeft = 0, lastRight = 0;

	public DriveTrain() {
		_leftEncoder.setDistancePerPulse((Math.PI * 8) / 250);
		_rightEncoder.setDistancePerPulse((Math.PI * 8) / 250);
		resetGyro();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
	}

	public void setReverseDrive(boolean val) {
		reverseDrive = val;
	}

	public void setSlowDrive(boolean val) {
		slowDrive = val;
	}

	public void setFastDrive(boolean val) {
		fastDrive = val;
	}
	
	public void setFrontLeftMotor(double val) {
		_leftfr.set(val);
	}
	
	public void setFrontRightMotor(double val) {
		_rightfr.set(val);
	}

	public void setBackLeftMotor(double val) {
		_leftback.set(val);
	}

	public void setBackRightMotor(double val) {
		_rightback.set(val);
	}

	public void tankDrive(double left, double right) {
		// double leftCalc = left * Math.pow(Math.abs(left), 0.3);
		// double rightCalc = right * Math.pow(Math.abs(right), 0.3);
		double leftCalc = (left * Math.pow(Math.abs(left), 0.6)) * RobotMap.SLOWDRIVE_CONSTANT;
		double rightCalc = (right * Math.pow(Math.abs(right), 0.6)) * RobotMap.SLOWDRIVE_CONSTANT;
		if (auton) {
			_drive.tankDrive(left, right);
			System.out.println("Left: " + left + "\nRight: " + right);
			lastLeft = left;
			lastRight = right;
		} else if (!reverseDrive && !fastDrive) {
			_drive.tankDrive(leftCalc, rightCalc);
			lastLeft = leftCalc;
			lastRight = rightCalc;
		} else if (reverseDrive && fastDrive) {
			_drive.tankDrive(-leftCalc * RobotMap.SLOWDRIVE_CONSTANT,
					-rightCalc * RobotMap.SLOWDRIVE_CONSTANT);
			lastLeft = -leftCalc * RobotMap.SLOWDRIVE_CONSTANT;
			lastRight = -rightCalc * RobotMap.SLOWDRIVE_CONSTANT;
		} else if (!reverseDrive && fastDrive) {
			_drive.tankDrive(leftCalc * (1 / RobotMap.SLOWDRIVE_CONSTANT),
					rightCalc * (1 / RobotMap.SLOWDRIVE_CONSTANT));
			lastLeft = leftCalc * (1 / RobotMap.SLOWDRIVE_CONSTANT);
			lastRight = rightCalc * (1 / RobotMap.SLOWDRIVE_CONSTANT);
		} else if (reverseDrive && !fastDrive) {
			_drive.tankDrive(-leftCalc, -rightCalc);
			lastLeft = -leftCalc;
			lastRight = -rightCalc;
		}
	}
	
	public double getLastLeftDrive() {
		return lastLeft;
	}
	
	public double getLastRightDrive() {
		return lastRight;
	}
	
	public double getFrontLeftMotor() {
		return _leftfr.get();
	}
	
	public double getFrontRightMotor() {
		return _rightfr.get();
	}

	public double getBackLeftMotor() {
		return _leftback.get();
	}

	public double getBackRightMotor() {
		return _rightback.get();
	}

	public double getLeftDistance() {
		return _leftEncoder.getDistance();
	}

	public double getRightDistance() {
		return -_rightEncoder.getDistance();
	}

	public double getLeftSpeedEnc() {
		return _leftEncoder.getRate();
	}

	public double getRightSpeedEnc() {
		return -_rightEncoder.getRate();
	}

	public void clearEncoder() {
		_leftEncoder.reset();
		_rightEncoder.reset();
	}

	public void setAuton(boolean val) {
		auton = val;
	}

	public void stop() {
		_drive.drive(0, 0);
	}

	public double getGyroAngle() {
		return _gyro.getAngle() - _gyroOffset;
	}

	public double getGyroRate() {
		return _gyro.getRate();
	}

	public void resetGyro() {
		_gyro.reset();
		clearGyro();
	}

	public void clearGyro() {
		_gyroOffset = _gyro.getAngle();
	}

	public double getXAccel() {
		return _accel.getX();
	}

	public double getYAccel() {
		return _accel.getY();
	}

	public boolean getCameraSettings() {
		return cameraSettingsOne;
	}

	public void setCameraSettings(boolean cameraSettingsOne) {
		this.cameraSettingsOne = cameraSettingsOne;
	}
}
