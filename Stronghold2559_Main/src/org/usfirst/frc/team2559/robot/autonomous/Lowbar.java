package org.usfirst.frc.team2559.robot.autonomous;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.commands.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.PIDTurn;
import org.usfirst.frc.team2559.robot.commands.SetIntake;
import org.usfirst.frc.team2559.robot.commands.Turn;
import org.usfirst.frc.team2559.robot.commands.shooter.SmartShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Lowbar extends CommandGroup {
    
    public  Lowbar() {
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
    	addSequential(new DriveForDistance(0.5, 100, true));
    	addSequential(new PIDTurn(45, 0.3));
    	addSequential(new SmartShoot());
    	addSequential(new PIDTurn(-(int)Robot._driveTrain.getGyroAngle(), 0.3));
    	addSequential(new DriveForDistance(0.5, 100, true));
    	addParallel(new SetIntake("in"));
    	addSequential(new DriveForDistance(0.5, 12, true));
    	addSequential(new PIDTurn(180, 0.5));
    	addParallel(new SetIntake("off"));
    	addSequential(new DriveForDistance(0.5, 112, true));
    	addSequential(new PIDTurn(45, 0.3));
    	addSequential(new SmartShoot());
    }
}
