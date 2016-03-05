package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepIntake extends CommandGroup {
    
    public  PrepIntake() {
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
	
	addSequential(new PIDSetArm(RobotMap.ARM_INTAKE_ANGLE));
	addSequential(new PIDSetShooter(RobotMap.SHOOTER_INTAKE_ANGLE, true));
	
    }
}
