package nl.uitdehoogte.ann.activation;

public class LinearActivationFunction implements ActivationFunction 
{
	public double execute(double input) 
	{
		return input > 0 ? 1.0: -1.0;
	}
}
