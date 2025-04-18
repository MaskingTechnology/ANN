package nl.uitdehoogte.ann.activation;

import nl.uitdehoogte.ann.activation.error.ErrorCalculator;
import nl.uitdehoogte.ann.activation.error.TangentErrorCalculator;

public class TangentActivationFunction implements ActivationFunction
{
	private static final long serialVersionUID = -1944770031002105160L;

	public double execute(double input) 
	{
		return 1.7159 * Math.tanh(2.0 / 3 * input);
	}

	public ErrorCalculator getErrorCalculator()
	{
		return new TangentErrorCalculator();
	}	
}
