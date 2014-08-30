package nl.uitdehoogte.ann.trainer.calculator.error;


public class LinearErrorCalculator implements ErrorCalculator
{	
	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		return weight * error;
	}
}
