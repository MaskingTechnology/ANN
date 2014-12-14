package nl.uitdehoogte.ann.trainer.calculator.error;

public class SigmoidErrorCalculator implements ErrorCalculator
{
	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return actualOutput * (1 - actualOutput) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{		
		return (weight * error) * (actualOutput * (1 - actualOutput));
	}
}
