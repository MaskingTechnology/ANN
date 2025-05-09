package nl.uitdehoogte.ann.activation.error;

import java.util.Random;

public class BogoErrorCalculator implements ErrorCalculator
{
	private static final long serialVersionUID = 1765780499412566553L;
	private Random random;
	private double nextDouble;
	
	public BogoErrorCalculator()
	{
		random = new Random();
	}
	
	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		nextDouble = random.nextDouble();
		return nextDouble * (1 - nextDouble) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		nextDouble = random.nextDouble();
		return nextDouble * (1 - nextDouble) * (weight * error);
	}
}
