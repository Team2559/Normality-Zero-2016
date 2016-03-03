package org.usfirst.frc.team2559.robot.commands.shooter;

import org.usfirst.frc.team2559.robot.Robot;
import org.usfirst.frc.team2559.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class SmartShoot extends CommandGroup {
    
    public  SmartShoot() {
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
    	
    	addSequential(new AlignWithTarget());
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
    	addSequential(new WaitCommand(RobotMap.SMARTSHOOT_SPINUP_DELAY * 2));
    	addSequential(new SetShooter(0, 0));
    	addParallel(new Command() {
			protected void initialize() {
				Robot._shooter.setShootingStatus(false);
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
    	
    }
}
