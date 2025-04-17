package nl.uitdehoogte.ann.activation;

import java.io.Serializable;

import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;

public interface ActivationFunction extends Serializable
{
	public double execute(double input);
	
	public ErrorCalculator getErrorCalculator();
}
