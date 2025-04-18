package nl.uitdehoogte.ann;

import java.io.Serializable;

public class Network implements Serializable
{
	private static final long serialVersionUID = 2673317010935875034L;
	private Layer[] layers;
	
	public Network(Layer[] layers)
	{
		this.layers = layers;
	}
	
	private double[] getInputLayerOutput(double[] input) throws NeuronException
	{
		double[] output = new double[input.length];

		for (int i = 0; i < output.length; i++)
		{
			output[i] = layers[0].getOutput(new double[] {input[i]}, i);
		}
		
		return output; 
	}
	
	public double[] getOutput(double[] input) throws NeuronException
	{
		double[] output = getInputLayerOutput(input);
		
		for (int i = 1; i < layers.length; i++)
		{
			output = layers[i].getOutput(output);
		}
		
		return output; 
	}
	
	public Layer[] getLayers()
	{
		return this.layers;
	}
}
