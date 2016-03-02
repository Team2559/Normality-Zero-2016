package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.commands.SetIntake;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn766;
import org.usfirst.frc.team2559.robot.commands.drive.Turn;
import org.usfirst.frc.team2559.robot.commands.shooter.SmartShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Lowbar_2Ball extends CommandGroup {
    
    public  Lowbar_2Ball() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	/**
    	 * @author Ozuru
    	 * 
    	 * This should cross under the low bar, turn, and then shoot.
    	 * It should then turn back around, drive back towards the ball,
    	 * turn the intake system on, drive forward and pickup a second ball,
    	 * turn back around again, drive under the low bar, turn, and then shoot.
    	 * 
    	 * If this doesn't work, the command groups may be canceling each other out. 
    	 */
    	
    	addSequential(new DriveForDistance(0.5, 100, true));
    	addSequential(new PIDTurn766(45));
    	addSequential(new SmartShoot());
    	addSequential(new PIDTurn766(-(int)Robot._driveTrain.getGyroAngle())); // negate the amount we have turned already
    	addSequential(new DriveForDistance(0.5, 100, true));
    	addParallel(new SetIntake("in"));
    	addSequential(new DriveForDistance(0.5, 12, true));
    	addSequential(new PIDTurn766(180));
    	addParallel(new SetIntake("off"));
    	addSequential(new DriveForDistance(0.5, 112, true));
    	addSequential(new PIDTurn766(45));
    	addSequential(new SmartShoot());
    }
}