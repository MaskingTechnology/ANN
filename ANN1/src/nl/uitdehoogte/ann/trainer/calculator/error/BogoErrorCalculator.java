package nl.uitdehoogte.ann.trainer.calculator.error;

import java.util.Random;


public class BogoErrorCalculator implements ErrorCalculator
{
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
