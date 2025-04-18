package nl.uitdehoogte.ann.activation;

import java.util.Random;

import nl.uitdehoogte.ann.activation.error.BogoErrorCalculator;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public class BogoActivationFunction implements ActivationFunction
{
	private static final long serialVersionUID = -2878271108716345002L;
	
	private Random random;
	
	public BogoActivationFunction()
	{
		random = new Random();
	}
	
	public double execute(double input) 
	{
		return random.nextDouble();
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return new BogoErrorCalculator();
	}	
}
