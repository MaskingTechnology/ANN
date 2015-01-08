package nl.uitdehoogte.ann.trainer.calculator.error;

import java.util.Random;


public class BogoErrorCalculator implements ErrorCalculator
{
	private Random random;
	
	public BogoErrorCalculator()
	{
		random = new Random();
	}
	
	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return random.nextInt(1) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		return random.nextInt(1) * (weight * error);
	}
}
