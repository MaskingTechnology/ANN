package nl.uitdehoogte.ann;

import java.io.Serializable;

import nl.uitdehoogte.ann.activation.ActivationFunction;

public class Neuron implements Serializable
{
	private static final long serialVersionUID = 5931709128178149730L;
	private ActivationFunction activationFunction;
	private int inputCount;
	private double[] weights;
	private double lastOutput;
	private double lastError;
	private double lastExpectedOutput;
	
	public Neuron(ActivationFunction activationFunction, int inputCount)
	{
		this.activationFunction = activationFunction;
		this.inputCount = inputCount;
	}

	public double getOutput(double[] input) throws NeuronException
	{
		// First weight is bias for this neuron, input not provided by Network Layer
		if(input.length != inputCount)
		{
			throw new NeuronException("Invalid number of input values");
		}
		
		double transferInput = sum(input);
		
		lastOutput = this.activationFunction.execute(transferInput);
		
		return lastOutput;
	}
	
	public void setWeights(double[] weights) throws NeuronException
	{
		if(weights.length != inputCount + 1)
		{
			throw new NeuronException("Invalid weight count");
		}
		
		this.weights = weights;
	}
	
	public double[] getWeights()
	{
		return this.weights;
	}
	
	private double sum(double[] input) 
	{
		double result = weights[0];
		
		for (int i = 0; i < input.length; i++) {
			result += this.weights[i + 1] * input[i];
		}
		
		return result;
	}
	
	public double getLastOutput()
	{
		return this.lastOutput;
	}
	
	public double getLastError()
	{
		return this.lastError;
	}
	
	public void setLastError(double lastError)
	{
		this.lastError = lastError;
	}
	
	public double getLastExpectedOutput()
	{
		return this.lastExpectedOutput;
	}
	
	public void setLastExpectedOutput(double lastExpectedOutput)
	{
		this.lastExpectedOutput = lastExpectedOutput;
	}
}
