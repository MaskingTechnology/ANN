package nl.uitdehoogte.ann.trainer.calculator.error;

import java.io.Serializable;

public class BinairyErrorCalculator implements ErrorCalculator, Serializable
{	
	private static final long serialVersionUID = 1437856129389694676L;

	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		return weight * error;
	}
}
