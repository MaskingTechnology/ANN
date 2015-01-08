package nl.uitdehoogte.ann.activation;

public class TangentActivationFunction implements ActivationFunction
{
	private static final long serialVersionUID = -1944770031002105160L;

	public double execute(double input) 
	{
		return 1.7159 * Math.tanh(2.0 / 3 * input);
	}
}
