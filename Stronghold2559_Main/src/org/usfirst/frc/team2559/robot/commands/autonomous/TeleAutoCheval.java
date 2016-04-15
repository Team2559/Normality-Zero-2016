package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class TeleAutoCheval extends CommandGroup {
    
    public  TeleAutoCheval() {
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
	addSequential(new GetReadyToRumble(RobotMap.CDF_ID));
	// drive to the CDF and touch it
	addSequential(new DriveForDistance(0.8, 10), 1);
	// addSequential(new DriveForDistance(0.8, RobotMap.DISTANCE_TO_OUTERWORKS / 1.8), 3);
	// let the robot roll down off a little
	addSequential(new WaitCommand(0.8));
	// push our arms down the whole way to push the CDF down
	addSequential(new PIDSetArm(186));
	// wait before we move at full speed
	addSequential(new DriveForDistance(0.8, 20));
	// get the shooter ready for shooting
	// get the arm up so we don't bang it when we hit the ground
	addParallel(new PIDSetArm(RobotMap.ARM_INITIAL_POS_ANGLE));
	// move full speed over the CDF
	
	addSequential(new DriveForDistance(0.7, RobotMap.DISTANCE_TO_OUTERWORKS / 2));

    }
}
