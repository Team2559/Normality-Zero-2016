package org.usfirst.frc.team2559.robot.commands.autonomous;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;
import org.usfirst.frc.team2559.robot.commands.GetReadyToRumble;
import org.usfirst.frc.team2559.robot.commands.arm.PIDSetArm;
import org.usfirst.frc.team2559.robot.commands.drive.DriveForDistance;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn;
import org.usfirst.frc.team2559.robot.commands.drive.PIDTurn766;
import org.usfirst.frc.team2559.robot.commands.drive.PIDVisionTurn;
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
public class CrossLowbar extends CommandGroup {
    
    public  CrossLowbar() {
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
	addSequential(new GetReadyToRumble(RobotMap.LOWBAR_ID));
//	addSequential(new DriveForDistance(0.6, RobotMap.DISTANCE_TO_OUTERWORKS + (RobotMap.DISTANCE_TO_OUTERWORKS / 2)));
	addSequential(new PIDSetArm(RobotMap.ARM_INTAKE_ANGLE));
	addSequential(new PIDSetShooter(30));
	// turn the proper direction
//	addSequential(new PIDAutonTurn((int)Robot.autonTurnDirection.getSelected()));
	addSequential(new PIDAutonTurn());
	/** vision **/
	addSequential(new PIDVisionTurn());
	addSequential(new PIDVisionShooter());
	addParallel(new Command() {
		protected void initialize() {
			Robot._shooter.setShootingStatus(true);
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
	addSequential(new SetShooter(1, 1));
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY));
	addSequential(new FireServo());
	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY));
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
