package org.usfirst.frc.team2559.lib;

public class PIDControllerRTExamples {
	public static void main(String args[]) {
		PIDVisionShooter pidCommand = new PIDVisionShooter();
		
		// A command that keeps getting called
		System.out.println("***** Example: Moving from 0 to +5, angle increasing");
		pidCommand.setTarget(5);
		while(!pidCommand.isFinished())
			pidCommand.execute();
		pidCommand.end();
		
		Shooter.currentAngle = 0.0;
		
		System.out.println("\n***** Example: Moving from 0 to -5, angle decreasing");
		pidCommand.setTarget(-5);
		while(!pidCommand.isFinished())
			pidCommand.execute();
		pidCommand.end();
		
		Shooter.currentAngle = 5.0;
		
		System.out.println("\n***** Example: Moving from 5 to 10, angle increasing");
		pidCommand.setTarget(10);
		while(!pidCommand.isFinished())
			pidCommand.execute();
		pidCommand.end();
		
		/**
		 * MOVEMENT. WE ARE GOING TO MOVE FOR 10 UNITS OF TIME
		 */
		System.out.println("\n***** Example: Staying on target. Robot moving to right with no PID control");
		double angle = 90;
		for(int i=0; i<10; i++){
			System.out.println("Before we move we are pointing at " + angle);
			
			// our robot is veering to the right 
			angle = angle + 0.25 + Math.random();
			
			// no control here so we'll probably end up as > 90
			
			System.out.println("After we move we are pointing at  " + angle);
		}
		
		System.out.println("\n***** Example: Staying on target. Robot moving to right with PID control");
		angle = 90;
		PIDControllerRT pid = new PIDControllerRT(0.8, 0.4, 0.4, 0.1, true);
		pid.setSetpoint(90);
		
		for(int i=0; i<10; i++){
			System.out.println("Before we move we are pointing at " + angle);
			
			// our robot is veering to the right 
			angle = angle + 0.25 + Math.random();
			
			// PID to stay on target
			pid.calculate(angle, true);
			double amountToMove = pid.getOutput();
			angle = angle + amountToMove;
			
			System.out.println("After we move we are pointing at  " + angle);
		}		
	}

	private static class PIDVisionShooter {
		private PIDControllerRT pid = new PIDControllerRT(0.8, 0.4, 0.4, 0.1, true);

		public void setTarget(double target){
			pid.reset();
			pid.setSetpoint(target);
		}
		
		public void execute() {
			System.out.println("***** Angle before PID loop:\t" + Shooter.getCurrentAngle());
			
			pid.calculate(Shooter.getCurrentAngle(), true);
			double powerToSend = pid.getOutput();		
			Shooter.setAdjusterSpeed(powerToSend);
			
			System.out.println("***** Angle after PID loop:\t" + Shooter.getCurrentAngle() + ", power sent: " + powerToSend);
		}

		protected boolean isFinished() {
			return pid.isDone();
		}

		protected void end() {
			pid.reset();

		}
	}

	/**
	 * Our simple shooter class that will move roughly how we tell it
	 * 
	 * @author Andy
	 *
	 */
	private static class Shooter {
		static double currentAngle = 0.0;

		private static void setAdjusterSpeed(double speed) {
			// Our angle will up with positive power and down with negative with some error
			double error = 0.85 + Math.random() / 5.0;
			currentAngle += speed * error;
		}

		public static double getCurrentAngle() {
			return currentAngle;
		}
	}
}
