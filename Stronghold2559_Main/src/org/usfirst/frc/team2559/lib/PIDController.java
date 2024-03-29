package org.usfirst.frc.team2559.lib;

public class PIDController {
	
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
	private double total_error = 0;

	private double output_value = 0;

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
	public PIDController(double P, double I, double D, double outputmax_low,
			double outputmax_high, double threshold) {
		Kp = P;
		Ki = I;
		Kd = D;
		maxoutput_low = outputmax_low;
		maxoutput_high = outputmax_high;
		endthreshold = threshold;
	}

	/**
	 * Constructs a PID controller, with the specified P,I,D values, along with the end threshold.
	 * 
	 * @param P Proportional value used in the PID controller
	 * @param I	Integral value used in the PID controller
	 * @param D Derivative value used in the PID controller
	 * 
	 * @param threshold the end threshold for declaring the PID 'done'
	 */
	public PIDController(double P, double I, double D, double threshold) {
		Kp = P;
		Ki = I;
		Kd = D;
		maxoutput_low = -1;
		maxoutput_high = 1;
		endthreshold = threshold;
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
		total_error = 0;
	}

	/**
	 * If we want to set values, such as with SmartDash
	 * 
	 * @param P Proportional value used in the PID controller
	 * @param I	Integral value used in the PID controller
	 * @param D Derivative value used in the PID controller
	 */
	public void setConstants(double P, double I, double D) {
		Kp = P;
		Ki = I;
		Kd = D;
	}
	
	/** Same as calculate() except that it prints debugging information
	 * 
	 * @param cur_input The current input to be plugged into the PID controller
	 * @param smart True if you want the output to be dynamically adjusted to the speed controller
	 */
	public void calculateDebug(double cur_input, boolean smart) {
		print = true;
		calculate(cur_input, smart);
	}

	/**
	 * Calculate PID value. Run only once per loop. Use getOutput to get output.
	 * 
	 * @param cur_input Input value from sensor
	 * @param clamp True if you want the output to be clamped
	 */
	public void calculate(double cur_input, boolean clamp) {
		cur_error = (setpoint - cur_input);
		if (isDone()) {
			output_value = 0;
			pr("pid done");
			return;
		}

		total_error += cur_error;

		if ((total_error * Ki) > 1) {
			total_error = 1 / Ki;
		} else {
			if ((total_error * Ki) < -1)
				total_error = -1 / Ki;
		}

		// double out = Kp * cur_error;

		double out = Kp * cur_error + Ki * total_error + Kd
				* (cur_error - prev_error);
		prev_error = cur_error;

		pr("Pre-clip output: " + out);
		
		if (clamp)
			output_value = clip(out);
		else
			output_value = out;

		pr("	Total Eror: " + total_error + "		Current Error: " + cur_error
				+ "	Output: " + output_value + " 	Setpoint: " + setpoint);
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
		return Math.abs(cur_error) < endthreshold;
	}

	/**
	 * Reset all accumulated errors
	 */
	public void reset() {
		cur_error = 0;
		prev_error = 0;
		total_error = 0;
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

	public double getError() {
		return total_error;
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