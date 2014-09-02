package nl.uitdehoogte.ann.activation;

public class SigmoidActivationFunction implements ActivationFunction 
{
	private static final long serialVersionUID = -2000083522785633686L;

	public double execute(double input) 
	{
		return 1.0 / (1 + Math.pow(Math.E, -input));
	}
}
