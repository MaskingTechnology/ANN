package nl.uitdehoogte.ann.activation;

import nl.uitdehoogte.ann.activation.error.LinearErrorCalculator;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public class LinearActivationFunction implements ActivationFunction 
{
	private static final long serialVersionUID = -1421072142407441918L;

	public double execute(double input) 
	{
		return input;
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return new LinearErrorCalculator();
	}	
}
