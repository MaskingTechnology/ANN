package nl.uitdehoogte.ann.activation.error;

import java.io.Serializable;

public interface ErrorCalculator extends Serializable
{
	public double calculateOutputError(double actualOutput, double expectedOutput);
	
	public double calculateHiddenError(double actualOutput, double weight, double error);
}
