package nl.uitdehoogte.ann.activation;

public class SigmoidActivationFunction implements ActivationFunction 
{
	public double execute(double input) 
	{
		return 1.0 / (1 + Math.pow(Math.E, -input));
	}
}
