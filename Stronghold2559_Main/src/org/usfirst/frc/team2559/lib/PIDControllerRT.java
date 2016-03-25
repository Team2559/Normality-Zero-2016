package org.usfirst.frc.team2559.lib;

public class PIDControllerRT {

	private boolean print = false;

	private double Kp = 0;
	private double Ki = 0;
	private double Kd = 0;
	private double maxoutput_low = 0;
	private double maxoutput_high = 0;
	private double endthreshold = 0;

	private double setpoint = 0;

	private double cur_error = 0;
	private double prev_error = 0;
	private double output_value = 0;

	private double integral = 0;

	/**
	 * Make sure we can call isDone() before we start, i.e. if I want
	 * 
	 * <pre>
	 *  while(!pid.isDone()){
	 *     ...
	 *  }
	 * </pre>
	 */
	private boolean isStarted = false;

	/**
	 * Prevent integral windup. I.e. if we have to clamp to 1,1,1,1,1... for a
	 * long time as we are moving from a large distance from the setpoint then
	 * we don't want to build up a high integral during that time as the
	 * controller can't do anything about it - it's already at maximum movement.
	 * 
	 * We will start with integral windup stopped, i.e. we'll assume that we're
	 * going to make big movements. As soon as we see that we are within range
	 * of our target (I.e. we are no longer clamped or we have crossed/overshot
	 * the setpoint) we'll start using the integral term and never stop using it
	 * again. I.e. once we start allowing windup we won't stop. This will
	 * prevent us potentially swinging high above and below the setpoint
	 * 
	 */
	private boolean preventWindUp = true;

	/**
	 * We will remember the original error so that we can tell if we've crossed
	 * the setoint. I.e. if we started at -10 and the current error is 5 then we
	 * know we crossed the setpoint. If we do cross the setpoint then it will be
	 * a signal to start using the integral term as we might oscillate
	 */
	private double originalError = 0;

	/**
	 * We want to time how often our PID loop is called. We need to know this so
	 * that the controller knows how its output effected the actuator. For
	 * example:
	 * 
	 * <pre>
	 * - If we moved 5 degrees in 15ms then the PID controller's
	 *   output caused 1 degree of movement every 3ms
	 * 
	 * - If we moved 5 degrees in 25ms then the PID controller's
	 *   output caused 1 degree of movement every 5ms
	 * </pre>
	 * 
	 * As the robot is doing other things we can't really controlled how often
	 * our PID is called, so we'll measure and adjust.
	 * 
	 * Our smart timer will measure the delay between the calculate() calls for
	 * the first few times and then normalize readings to that time for calls
	 * after that. i.e.
	 * 
	 * <pre>
	 * calculate() - wait 25 ms
	 * calculate() - wait 15 ms
	 * calculate() - wait 20 ms
	 * -> dt = 20ms
	 * - any readings that are for <20ms we would increase as the
	 *   robot didn't have as long to perform the action
	 * 
	 * - any readings that are for >20ms we would decrease as the
	 *   robot had longer to perform the action
	 * </pre>
	 */
	private boolean useSmartTime = true;
	private double averagePeriod = 0;
	private long lastCalledTime = 0;
	private double defaultPeriod = 1.0;
	// We initially have -1 readings as we need at least 2 reading to start the
	// calculation
	private double timeReadings = -1;

	/**
	 * 
	 * @param P
	 *            P constant
	 * @param I
	 *            I constant
	 * @param D
	 *            D constant
	 * @param outputmax_low
	 *            Largest output in the negative direction
	 * @param outputmax_high
	 *            Largest output in the positive direction
	 * @param threshold
	 *            threshold for declaring the PID 'done'
	 */
	public PIDControllerRT(double P, double I, double D, double outputmax_low, double outputmax_high, double threshold,
			boolean useSmartTime) {
		Kp = P;
		Ki = I;
		Kd = D;
		maxoutput_low = outputmax_low;
		maxoutput_high = outputmax_high;
		endthreshold = threshold;
		this.useSmartTime = useSmartTime;
	}

	/**
	 * Constructs a PID controller, with the specified P,I,D values, along with
	 * the end threshold.
	 * 
	 * @param P
	 *            Proportional value used in the PID controller
	 * @param I
	 *            Integral value used in the PID controller
	 * @param D
	 *            Derivative value used in the PID controller
	 * 
	 * @param threshold
	 *            the end threshold for declaring the PID 'done'
	 */
	public PIDControllerRT(double P, double I, double D, double threshold, boolean useSmartTime) {
		Kp = P;
		Ki = I;
		Kd = D;
		maxoutput_low = -1;
		maxoutput_high = 1;
		endthreshold = threshold;
		this.useSmartTime = useSmartTime;
	}

	/**
	 * We may want to use same PID object, but with different setpoints, so this
	 * is separated from constructor
	 * 
	 * @param set
	 *            Target point to match
	 */
	public void setSetpoint(double set) {
		setpoint = set;
		integral = 0;
	}

	/**
	 * If we want to set values, such as with SmartDash
	 * 
	 * @param P
	 *            Proportional value used in the PID controller
	 * @param I
	 *            Integral value used in the PID controller
	 * @param D
	 *            Derivative value used in the PID controller
	 */
	public void setConstants(double P, double I, double D) {
		Kp = P;
		Ki = I;
		Kd = D;
	}

	/**
	 * Same as calculate() except that it prints debugging information
	 * 
	 * @param cur_input
	 *            The current input to be plugged into the PID controller
	 * @param smart
	 *            True if you want the output to be dynamically adjusted to the
	 *            speed controller
	 */
	public void calculateDebug(double cur_input, boolean smart) {
		print = true;
		calculate(cur_input, smart);
	}

	/**
	 * Calculate PID value. Run only once per loop. Use getOutput to get output.
	 * 
	 * @param cur_input
	 *            Input value from sensor
	 * @param clamp
	 *            True if you want the output to be clamped
	 */
	public void calculate(double cur_input, boolean clamp) {
		// We have started, so it is now possible for us to return TRUE in
		// isDone()
		isStarted = true;

		// Check if we are at the set point
		cur_error = (setpoint - cur_input);
		if (isDone()) {
			output_value = 0;
			pr("pid done");
			return;
		}

		pr("Running PID loop with input: " + cur_input + ", current error: " + cur_error);

		// Only get dt once per call
		double dt = getSmartDt();

		// Perform the pid calculation
		double out = performCalc(dt);

		// See if we clamped or not
		double preClampOut = out;
		if (clamp)
			output_value = clip(out);
		else
			output_value = out;

		// If we ever cross the origin we want to enable integral next loop
		originCrossCheck(cur_error);

		// If the output is no longer clamped then we will use the integral term
		if ((preClampOut == output_value) && preventWindUp) {
			preventWindUp = false;

			// Re-do this calculation with integration enabled
			preClampOut = output_value;
			out = performCalc(dt);
			if (clamp)
				output_value = clip(out);
			else
				output_value = out;

			pr("We are enabling integral as we are not clamped. Out was: " + preClampOut + ", it is now: "
					+ output_value);
		}

		// Get ready for next loop
		prev_error = cur_error;

		pr("	Total Eror: " + integral + "\tCurrent Error: " + cur_error + "\tOutput: " + output_value
				+ "\tPre-clip Output: " + preClampOut + "\tSetpoint: " + setpoint);
	}

	/**
	 * Perform the PID calculation
	 * 
	 * @param dt
	 *            The time period that we waited for more current input
	 * @return The control value
	 */
	private double performCalc(final double dt) {
		double derivative = 0;

		// Only use I and D if windup protection is off, i.e. we are somewhere
		// near the target
		if (!preventWindUp) {
			integral += cur_error * dt;

			if (integral > maxoutput_high) {
				pr("Capping max integral to: " + maxoutput_high + " from " + integral);
				integral = maxoutput_high;
			} else if (integral < maxoutput_low) {
				pr("Capping min integral to: " + maxoutput_low + " from " + integral);
				integral = maxoutput_low;
			}

			// double out = Kp * cur_error;
			derivative = (cur_error - prev_error) / dt;
		}

		double out = Kp * cur_error + Ki * integral + Kd * derivative;

		return out;
	}

	/**
	 * Calculate the size of the current wait period based on the average wait
	 * period we have observed
	 * 
	 * @return The weighted Dt value
	 */
	private double getSmartDt() {
		// If we are not using smart time then just use 1.0
		if (!useSmartTime)
			return defaultPeriod;

		timeReadings++;

		// If this is the first call then there is no change, so just recored
		// the time called
		if (timeReadings == 0) {
			lastCalledTime = System.currentTimeMillis();
			return defaultPeriod;
		}

		final long currentTime = System.currentTimeMillis();
		final double period = currentTime - lastCalledTime;

		// Calculate the moving average
		averagePeriod = (averagePeriod * (timeReadings - 1.0) / timeReadings) + (period * 1.0 / timeReadings);
		pr("Calculated average dt as: " + averagePeriod);

		// Set the last called time to now
		lastCalledTime = currentTime;

		// If we don't have enough reading to calculate a good average yet then
		// just return default
		if (timeReadings < 3)
			return defaultPeriod;
		// If there is no smart time then use default
		if (averagePeriod <= 0l)
			return defaultPeriod;

		// Otherwise normalize the current period to the average. I.e. if the
		// average is 500ms and the period is 500ms then the normalized value is
		// 1.0 units
		//
		// If the average period is 500ms and the period is 750ms then the
		// normalized time value is 1.5 units
		double ret = ((double) period) / averagePeriod;
		pr("The current wait period of " + period + "ms is " + ret + " units");
		return ret;
	}

	public void basicCalculate(double cur_input) {
		cur_error = setpoint - cur_input;
		output_value = Kp * cur_error + Kd * (cur_error - prev_error) * 100.0;

		prev_error = cur_error;
	}

	public double getOutput() {
		return output_value;
	}

	public boolean isDone() {
		return Math.abs(cur_error) < endthreshold && isStarted;
	}

	/**
	 * Reset all accumulated errors
	 */
	public void reset() {
		cur_error = 0;
		prev_error = 0;
		integral = 0;
		isStarted = false;
		preventWindUp = true;
		originalError = 0;
		lastCalledTime = 0;
		averagePeriod = 0;
		timeReadings = -1;
	}

	/**
	 * Clips value for sending to speed controllers. This deals with if you
	 * don't want to run an arm or wheels at full speed under PID.
	 * 
	 * @param clipped
	 * @return clipped value, safe for setting to controllers
	 */
	private double clip(double clipped) {
		double out = clipped;
		if (out > maxoutput_high)
			out = maxoutput_high;
		if (out < maxoutput_low)
			out = maxoutput_low;
		return out;
	}

	/**
	 * Checks to see if the input value has crossed the setpoint, and will
	 * enable I and D if they are not enabled
	 * 
	 * @param cur_input
	 *            The current PID input
	 */
	private void originCrossCheck(double cur_input) {
		// If this is the first time we are called remember the initial error so
		// we know if it was above or below the setpoint
		if (originalError == 0)
			originalError = cur_error;

		// If we have crossed the setpoint then we will enable the integral term
		// if it is not already
		if (!((cur_error >= 0) ^ (originalError < 0)) && preventWindUp) {
			pr("We have crossed the setpoint so we are enabling the integral term\n" + "originalError: " + originalError
					+ "\tcur_error: " + cur_error);
			preventWindUp = false;
		}
	}

	public double getError() {
		return integral;
	}

	public double getCurrentError() {
		return cur_error;
	}

	public void setMaxoutputHigh(double in) {
		maxoutput_high = in;
	}

	public void setMaxoutputLow(double in) {
		maxoutput_low = in;
	}

	private void pr(Object text) {
		if (print)
			System.out.println("PID: " + text);
	}

}