package nl.uitdehoogte.ann.activation.error;

public class SigmoidErrorCalculator implements ErrorCalculator
{
	private static final long serialVersionUID = -4484678169316421493L;

	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return actualOutput * (1 - actualOutput) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{		
		return actualOutput * (1 - actualOutput) * (weight * error);
	}
}
