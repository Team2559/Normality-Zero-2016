package org.usfirst.frc.team2559.robot.commands;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.shooter.FireServo;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDSetShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.PIDVisionShooter;
import org.usfirst.frc.team2559.robot.commands.shooter.SetShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LowGoal extends CommandGroup {
    
    public  LowGoal() {
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
	addParallel(new Command() {

	    protected void initialize() {
		Robot._shooter.setTargetingStatus(true);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	addSequential(new PIDSetShooter(0));
	addParallel(new Command() {    

	    protected void initialize() {
		Robot._shooter.setTargetingStatus(false);
		Robot._shooter.setShootingStatus(true);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	addSequential(new SetShooter(1, 1));
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY));
	addSequential(new FireServo());
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY * 2));
	addSequential(new SetShooter(0, 0));
	addParallel(new Command() {

	    protected void initialize() {
		Robot._shooter.setShootingStatus(false);
	    }

	    protected void execute() {}

	    protected boolean isFinished() {
		return true;
	    }

	    protected void end() {}

	    protected void interrupted() {
		end();
	    }
	});
	
    }
}
