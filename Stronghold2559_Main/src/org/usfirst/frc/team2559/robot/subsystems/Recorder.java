package org.usfirst.frc.team2559.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.control.DoNothing;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Recorder extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	// recorder stuff
	FileWriter writer;
	
	// player stuff
	Scanner scanner;
	boolean onTime = true, shouldStop = false;
	double nextDouble;
	long playerStartTime;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new DoNothing());
    }
    
    public void initializeRecorder(String id) throws IOException {
    	writer = new FileWriter(RobotMap.AUTONOMOUS_FILE + id + ".csv");
    }
    
    public void recordInput(long startTime) throws IOException {
    	if(writer != null) {
    		writer.append("" + (System.currentTimeMillis() - startTime));
    		
//    		writer.append("," + Robot._driveTrain.getFrontLeftMotor());
//    		writer.append("," + Robot._driveTrain.getFrontRightMotor());
//    		writer.append("," + Robot._driveTrain.getBackLeftMotor());
    		
    		writer.append("," + Robot._driveTrain.getLastLeftDrive());    		   		
    		
    		/*
    		 * THE LAST THING YOU APPEND MUST HAVE \N
    		 */
    		 		
//    		writer.append("," + Robot._driveTrain.getBackRightMotor() + "\n");
    		    		
    		writer.append("," + Robot._driveTrain.getLastRightDrive() + "\n");
    		
    		/*
    		 * KEEP THE LAST THING BETWEEN THESE CODEBLOCKS TO REMEMBER
    		 */
    	}
    }
    
    public void stopRecording() throws IOException {
    	if(writer != null) {
    		writer.flush();
    		writer.close();
    	}
    }
    
    public void initializePlayer(String id) throws FileNotFoundException {
    	scanner = new Scanner(new File(RobotMap.AUTONOMOUS_FILE + id + ".csv"));
    	
    	// lets the scanner know numbers are separated by a comma or a \n
    	scanner.useDelimiter(",|\\n");
    	
    	playerStartTime = System.currentTimeMillis();
    	Robot._driveTrain.setAuton(true);
    	shouldStop = false;
    }
    
    public void playRecording() {
    	if ((scanner != null) && (scanner.hasNextDouble())) {
    		double t_delta;
    		
    		if(onTime) {
    			nextDouble = scanner.nextDouble();
    		}
    		
    		t_delta = nextDouble - (System.currentTimeMillis() - playerStartTime);
    		
    		if(t_delta <= 0) {
//        		Robot._driveTrain.setFrontLeftMotor(scanner.nextDouble());
//        		Robot._driveTrain.setFrontRightMotor(scanner.nextDouble());
//        		Robot._driveTrain.setBackLeftMotor(scanner.nextDouble());
//        		Robot._driveTrain.setBackRightMotor(scanner.nextDouble());
    			
        		Robot._driveTrain.tankDrive(scanner.nextDouble(), scanner.nextDouble());
        		
        		onTime = true;
    		} else {
    			onTime = false;
    		}
    	} else {
    		this.stopPlayback();
    		
    		if(scanner != null) {
    			scanner.close();
    			scanner = null;
    		}
    	}
    }
    
    public void stopPlayback() {
    	Robot._driveTrain.tankDrive(0, 0);
    	Robot._driveTrain.setAuton(false);
    	shouldStop = true;
    }
    
    public boolean hasFinishedPlayback() {
    	return shouldStop;
    }
    
}

