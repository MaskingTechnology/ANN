package nl.uitdehoogte.ann.activation;

import java.util.Random;

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
}
