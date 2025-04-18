package nl.uitdehoogte.ann.activation;

import java.io.Serializable;

import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public interface ActivationFunction extends Serializable
{
	public double execute(double input);
	
	public ErrorCalculator getErrorCalculator();
}
