package nl.uitdehoogte.ann.activation.error;

public class TangentErrorCalculator implements ErrorCalculator
{
	private static final long serialVersionUID = 610143577953410908L;

	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return 2.0 / 3 * (1.7159 - (actualOutput * actualOutput)) * (expectedOutput - actualOutput);
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{		
		return 2.0 / 3 * (1.7159 - (actualOutput * actualOutput)) * (weight * error);
	}
}
