package org.usfirst.frc.team2559.lib;

/**
 * A PID controller for turning the robot. It will slow down as it approaches
 * the correct angle to account for the momentum of the robot
 * 
 * @author Andy
 *
 */
public class PIDTurnController extends PIDControllerRT {
	// How close we need to get to start slowing down;
	private double outerThreshold = 25.0;
	private double innerThreshold = 10.0;

	// How much to slow down. 0.5=50% slow down, 0.2=20% slow down etc.
	private double outerSlowdown = 0.65;
	private double innerSlowdown = 0.5;

	// If we have factored in the slowdown yet
	private boolean startedInnerSlowdown = false;
	private boolean startedOuterSlowdown = false;

	// Orig values before we make them smaller
	private double origKp = 0;
	private double origKi = 0;
	private double origKd = 0;
	private double origMaxoutput_low = 0;
	private double origMaxoutput_high = 0;

	// After we have finished, allow the bot to settle and then enable control
	// again for a short period
	private long settleTimeMs = 300;
	private long currentSettleTimeMs = 0;
	private long maxRetryTimeMs = 350;
	private long currentRetryTimeMs = 0;
	private boolean isNormallyDone = false;

	/**
	 * 
	 * @param P
	 * @param I
	 * @param D
	 * @param outputmax_low
	 * @param outputmax_high
	 * @param threshold
	 * @param useSmartTime
	 * @param outerThreshold The value at which to start slowing down the controller
	 * @param innerThreshold The value at which to start slowing down the controller even more
	 * @param outerSlowdown The factor to slow down the controller
	 * @param innerSlowdown The factor to slow down the controller even more
	 */
	public PIDTurnController(double P, double I, double D, double outputmax_low, double outputmax_high,
			double threshold, boolean useSmartTime, double outerThreshold, double innerThreshold, double outerSlowdown,
			double innerSlowdown) {
		this(P, I, D, outputmax_low, outputmax_high, threshold, useSmartTime);

		this.outerThreshold = outerThreshold;
		this.innerThreshold = innerThreshold;
		this.outerSlowdown = outerSlowdown;
		this.innerSlowdown = innerSlowdown;
	}

	public PIDTurnController(double P, double I, double D, double outputmax_low, double outputmax_high,
			double threshold, boolean useSmartTime) {
		super(P, I, D, outputmax_low, outputmax_high, threshold, useSmartTime);

		this.origKp = Kp;
		this.origKi = Ki;
		this.origKd = Kd;
		this.origMaxoutput_low = maxoutput_low;
		this.origMaxoutput_high = maxoutput_high;
	}

	public PIDTurnController(double P, double I, double D, double threshold, boolean useSmartTime) {
		this(P, I, D, -1.0, 1.0, threshold, useSmartTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team2559.lib.PIDControllerRT#calculate(double,
	 * boolean)
	 */
	public void calculate(double cur_input, boolean clamp) {
		long currentLastCalledTime = lastCalledTime;
		
		doSlowdown(cur_input);

		// Now do the calculation as normal (maybe with slowed down values)
		super.calculate(cur_input, clamp);

		// If the controller is normally done we might want to wait for the
		// robot to settle. We will set the output to 0.0 to settle
		if (this.isNormallyDone && currentSettleTimeMs < settleTimeMs) {
			output_value = 0.0;

			// reverse any integral windup for this period
			if (!preventWindUp) {
				double cur_error = (setpoint - cur_input);
				integral -= cur_error * dt;
			}

			// Add some time to the settle time (at least 1ms)
			currentSettleTimeMs += Math.max(1l, System.currentTimeMillis() - currentLastCalledTime);
		}
		// Otherwise, if we would normally be finished by we are doing the after
		// settle control increment that time
		else if (this.isNormallyDone) {
			currentRetryTimeMs += Math.max(1l, System.currentTimeMillis() - currentLastCalledTime);;
		}
	}

	/**
	 * If the current error is within one of the thresholds then slow down
	 * @param cur_input
	 */
	private void doSlowdown(double cur_input) {
		double cur_error = (setpoint - cur_input);

		// Do the outer slowdown
		if (!startedOuterSlowdown && Math.abs(cur_error) < outerThreshold) {
			Kp = this.origKp * outerSlowdown;
			Ki = this.origKi * outerSlowdown;
			Kd = this.origKd * outerSlowdown;

			maxoutput_low = this.origMaxoutput_low * outerSlowdown;
			maxoutput_high = this.origMaxoutput_high * outerSlowdown;

			this.startedOuterSlowdown = true;
		}

		// Do the inner slowdown
		if (!startedInnerSlowdown && Math.abs(cur_error) < innerThreshold) {
			Kp = this.origKp * innerSlowdown;
			Ki = this.origKi * innerSlowdown;
			Kd = this.origKd * innerSlowdown;

			maxoutput_low = this.origMaxoutput_low * innerSlowdown;
			maxoutput_high = this.origMaxoutput_high * innerSlowdown;

			this.startedInnerSlowdown = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.usfirst.frc.team2559.lib.PIDControllerRT#isDone()
	 */
	public boolean isDone() {
		boolean superDone = super.isDone();

		if (superDone)
			isNormallyDone = true;

		return isNormallyDone && (currentRetryTimeMs >= maxRetryTimeMs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team2559.lib.PIDControllerRT#reset()
	 */
	public void reset() {
		super.reset();

		// Reset the slowdown
		this.startedInnerSlowdown = false;
		this.startedOuterSlowdown = false;

		Kp = this.origKp;
		Ki = this.origKi;
		Kd = this.origKd;
		maxoutput_low = this.origMaxoutput_low;
		maxoutput_high = this.origMaxoutput_high;

		// Reset the settle time
		currentSettleTimeMs = 0;
		currentRetryTimeMs = 0;
		isNormallyDone = false;
	}
}
