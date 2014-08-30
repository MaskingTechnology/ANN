package nl.uitdehoogte.ann.activation;

import java.io.Serializable;

public interface ActivationFunction extends Serializable
{
	public double execute(double input);
}
