package nl.uitdehoogte.ann.activation;

import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;
import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;

public class SigmoidActivationFunction implements ActivationFunction 
{
	private static final long serialVersionUID = -2000083522785633686L;

	public double execute(double input) 
	{
		return 1.0 / (1 + Math.pow(Math.E, -input));
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return new SigmoidErrorCalculator();
	}	
}
