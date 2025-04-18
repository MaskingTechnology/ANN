package nl.uitdehoogte.ann.activation.error;

public class LinearErrorCalculator implements ErrorCalculator
{
	private static final long serialVersionUID = 1373695337120583019L;

	public double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return actualOutput - expectedOutput;
	}
	
	public double calculateHiddenError(double actualOutput, double weight, double error)
	{
		return weight * error;
	}
}
