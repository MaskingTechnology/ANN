package nl.uitdehoogte.ann.trainer.calculator.error;

import java.util.Random;


public class BogoErrorCalculator implements ErrorCalculator
{	
	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		Random random = new Random();
		
		return random.nextInt(1) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		Random random = new Random();
		
		return random.nextInt(1) * (weight * error);
	}
}
