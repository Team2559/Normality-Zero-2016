//package org.usfirst.frc.team2559.robot.subsystems;
//
//import javax.swing.text.StyleContext.SmallAttributeSet;
//
//import org.usfirst.frc.team2559.robot.RobotMap;
//
//import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.Talon;
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
///**
// *
// */
//public class Shooter extends Subsystem {
//
//    // Put methods for controlling this subsystem
//    // here. Call these from Commands.
//
//    public void initDefaultCommand() {
//	// Set the default command for a subsystem here.
//	// setDefaultCommand(new MySpecialCommand());
//    }
//
//    public Shooter() {
//	this.invertMotors();
//    }
//
//    private final CANTalon    _left       = new CANTalon(RobotMap.PORT_SHOOTER_LEFT), 
//	    		      _right 	  = new CANTalon(RobotMap.PORT_SHOOTER_RIGHT);
//
//    private final Servo       _pusher     = new Servo(RobotMap.PORT_SHOOTER_PUSHER), 
//	    		     _engageLatch = new Servo(RobotMap.PORT_SHOOTER_LATCH);
//    //
//    private final CANTalon    _adjuster   = new CANTalon(RobotMap.PORT_SHOOTER_ADJUSTER);
//    private final AnalogInput _shooterEnc = new AnalogInput(RobotMap.PORT_SHOOTER_ENCODER);
//
//    private boolean	   shootingStatus = false, targetingStatus = false;
//    private double	    shooterEncZero = 0;
//
//    public double getXOffset() {
//	// i'm a one-liner god
//	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerXOffset", 0) : 0;
//    }
//
//    public double getYOffset() {
//	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("towerYOffset", 0) : 0;
//    }
//
//    public double getVisionAzimuth() {
//	System.out.println("Azimuth Value: " + SmartDashboard.getNumber("azimuth", 0));
//	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("azimuth", 0) : 0;
//    }
//
//    public double getVisionAltitude() {
//	return SmartDashboard.getBoolean("foundTower") ? SmartDashboard.getNumber("altitude", 0) : 0;
//    }
//
//    public void setSpinSpeed(double left, double right) {
//	_left.set(left);
//	_right.set(right);
//    }
//
//    public void invertMotors() {
//	 _left.setInverted(true);
//	//_right.setInverted(true);
//    }
//
//    public double getShooterAngle() {
//	return (360/5) * (_shooterEnc.getVoltage() - RobotMap.SHOOTER_ZERO);
//    }
//    
//    public double getShooterAngleInVolts() {
//	return _shooterEnc.getVoltage();
//    }
//
//    public void setEncZero(double val) {
//	shooterEncZero = val;
//    }
//
//    public void setAdjusterSpeed(double speed) {
//	_adjuster.set(speed);
//    }
//
//    public void intakeIn() {
//	this.setSpinSpeed(RobotMap.SHOOTER_INTAKE_SPEED, RobotMap.SHOOTER_INTAKE_SPEED);
//    }
//
//    public void intakeOut() {
//	this.setSpinSpeed(-RobotMap.SHOOTER_INTAKE_SPEED, -RobotMap.SHOOTER_INTAKE_SPEED);
//    }
//
//    public void intakeStop() {
//	this.setSpinSpeed(0, 0);
//    }
//
//    public double getLeftShooterMotor() {
//	return _left.get();
//    }
//
//    public double getRightShooterMotor() {
//	return _right.get();
//    }
//
//    public void setFiringServo(double val) {
//	 _pusher.set(val);
//    }
//
//    public void setClutchServo(double val) {
//	 _engageLatch.setAngle(val);
//    }
//
//    public double getPusherServo() {
//	 return _pusher.getAngle();
//    }
//
//    public boolean getShootingStatus() {
//	return shootingStatus;
//    }
//
//    public boolean getTargetingStatus() {
//	return targetingStatus;
//    }
//
//    public void setShootingStatus(boolean val) {
//	shootingStatus = val;
//    }
//
//    public void setTargetingStatus(boolean val) {
//	targetingStatus = val;
//    }
//}
