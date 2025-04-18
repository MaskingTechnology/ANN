package nl.uitdehoogte.ann;

import java.io.Serializable;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public class Layer implements Serializable
{
	private static final long serialVersionUID = 8508449559696724813L;
	private Neuron[] neurons;
	private ErrorCalculator errorCalculator;
	
	public Layer(Neuron[] neurons, ErrorCalculator errorCalculator)
	{
		this.neurons = neurons;
		this.errorCalculator = errorCalculator;
	}
	
	public double[] getOutput(double[] input) throws NeuronException
	{
		double[] output = new double[neurons.length];
		
		for (int i = 0; i < neurons.length; i++)
		{
			output[i] = neurons[i].getOutput(input);
		}

		return output;
	}

	public double getOutput(double[] input, int index) throws NeuronException
	{
		return neurons[index].getOutput(input);
	}
	
	public Neuron[] getNeurons()
	{
		return this.neurons;
	}
	
	public ErrorCalculator getErrorCalculator()
	{
		return this.errorCalculator;
	}
}
