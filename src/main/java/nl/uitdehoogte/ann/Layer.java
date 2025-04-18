package nl.uitdehoogte.ann;

import java.io.Serializable;
import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;

public class Layer implements Serializable
{
	private static final long serialVersionUID = 8508449559696724813L;
	private Perceptron[] perceptrons;
	private ErrorCalculator errorCalculator;
	
	public Layer(Perceptron[] perceptrons, ErrorCalculator errorCalculator) 
	{
		this.perceptrons = perceptrons;
		this.errorCalculator = errorCalculator;
	}
	
	public double[] getOutput(double[] input) throws PerceptronException
	{
		double[] output = new double[perceptrons.length];
		
		for (int i = 0; i < perceptrons.length; i++)
		{
			output[i] = perceptrons[i].getOutput(input);
		}

		return output;
	}

	public double getOutput(double[] input, int index) throws PerceptronException
	{
		return perceptrons[index].getOutput(input);
	}
	
	public Perceptron[] getPerceptrons()
	{
		return this.perceptrons;
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return this.errorCalculator;
	}
}
