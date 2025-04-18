package nl.uitdehoogte.ann.repository;

import java.util.Random;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Neuron;
import nl.uitdehoogte.ann.NeuronException;
import nl.uitdehoogte.ann.activation.ActivationFunction;
import nl.uitdehoogte.ann.activation.LinearActivationFunction;

public class NetworkBuilder 
{
	public static Network build(int[] neurons, ActivationFunction activationFunction) throws NeuronException
	{
		Layer[] layers = new Layer[neurons.length];
		
		layers[0] = buildInputLayer(neurons[0], new LinearActivationFunction());
		
		int i;
		
		for (i = 1; i < layers.length - 1; i++)
		{
			layers[i] = buildHiddenLayer(neurons[i], neurons[i - 1], activationFunction);
		}
		
		layers[layers.length - 1] = buildOutputLayer(neurons[i], neurons[i - 1], activationFunction);
		
		return new Network(layers);
	}
	
	private static Layer buildInputLayer(int neuronCount, ActivationFunction activationFunction) throws NeuronException
	{
		double[] weights = new double[] {0.0, 1.0};
		
		Neuron[] neurons = createNeurons(neuronCount, activationFunction, weights);
		
		return new Layer(neurons, activationFunction.getErrorCalculator());
	}
	
	private static Layer buildHiddenLayer(int neuronCount, int inputCount, ActivationFunction activationFunction) throws NeuronException
	{		
		Neuron[] neurons = createNeurons(neuronCount, inputCount, activationFunction);
		
		return new Layer(neurons, activationFunction.getErrorCalculator());
	}
	
	private static Layer buildOutputLayer(int neuronCount, int inputCount, ActivationFunction activationFunction) throws NeuronException
	{		
		Neuron[] neurons = createNeurons(neuronCount, inputCount, activationFunction);
		
		return new Layer(neurons, activationFunction.getErrorCalculator());
	}
	
	private static double[] generateRandomWeights(int inputCount)
	{
		double[] weights = new double[inputCount];
		Random random = new Random();
		
		for (int i = 0; i < weights.length; i++)
		{
			weights[i] = random.nextGaussian();
		}
		
		return weights;
	}
	
	private static Neuron[] createNeurons(int neuronCount, ActivationFunction activationFunction, double[] weights) throws NeuronException
	{
		Neuron[] neurons = new Neuron[neuronCount];
		
		for (int i = 0; i < neuronCount; i++) 
		{
			neurons[i] = new Neuron(activationFunction, weights.length - 1);
			neurons[i].setWeights(weights);
		}
		
		return neurons;
	}
	
	private static Neuron[] createNeurons(int neuronCount, int inputCount, ActivationFunction activationFunction) throws NeuronException
	{
		Neuron[] neurons = new Neuron[neuronCount];
		
		for (int i = 0; i < neuronCount; i++) 
		{
			neurons[i] = new Neuron(activationFunction, inputCount);
			neurons[i].setWeights(generateRandomWeights(inputCount + 1));
		}
		
		return neurons;
	}
}
