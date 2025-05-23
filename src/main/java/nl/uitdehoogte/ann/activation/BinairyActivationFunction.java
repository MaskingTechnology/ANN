package nl.uitdehoogte.ann.activation;

import nl.uitdehoogte.ann.activation.error.BinairyErrorCalculator;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public class BinairyActivationFunction implements ActivationFunction 
{
	private static final long serialVersionUID = -1421072142407441918L;

	public double execute(double input) 
	{
		return input > 0 ? 1.0: -1.0;
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return new BinairyErrorCalculator();
	}
}
