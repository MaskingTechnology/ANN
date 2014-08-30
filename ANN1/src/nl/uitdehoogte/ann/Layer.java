package nl.uitdehoogte.ann;

import java.io.Serializable;


public class Layer implements Serializable
{
	private Perceptron[] perceptrons;
	
	public Layer(Perceptron[] perceptrons) 
	{
		this.perceptrons = perceptrons;
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
	
	public Perceptron[] getPerceptrons()
	{
		return this.perceptrons;
	}
}
