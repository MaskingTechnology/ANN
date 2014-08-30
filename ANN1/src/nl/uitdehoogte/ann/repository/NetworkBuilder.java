package nl.uitdehoogte.ann.repository;

import java.util.Random;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Perceptron;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.activation.ActivationFunction;

public class NetworkBuilder 
{
	public static Network build(int[] perceptrons, ActivationFunction activationFunction) throws PerceptronException
	{
		Layer[] layers = new Layer[perceptrons.length];
		
		layers[0] = buildInputLayer(perceptrons[0], activationFunction);
		
		int i;
		
		for (i = 1; i < layers.length - 1; i++)
		{
			layers[i] = buildHiddenLayer(perceptrons[i], perceptrons[i - 1], activationFunction);
		}
		
		layers[layers.length - 1] = buildOutputLayer(perceptrons[i], perceptrons[i - 1], activationFunction);
		
		return new Network(layers);
	}
	
	private static Layer buildInputLayer(int perceptronCount, ActivationFunction activationFunction) throws PerceptronException
	{
		double[] weights = new double[] {0.0, 1.0};
		
		Perceptron[] perceptrons = createPerceptrons(perceptronCount, activationFunction, weights);
		
		return new Layer(perceptrons);
	}
	
	private static Layer buildHiddenLayer(int perceptronCount, int inputCount, ActivationFunction activationFunction) throws PerceptronException
	{		
		Perceptron[] perceptrons = createPerceptrons(perceptronCount, inputCount, activationFunction);
		
		return new Layer(perceptrons);
	}
	
	private static Layer buildOutputLayer(int perceptronCount, int inputCount, ActivationFunction activationFunction) throws PerceptronException
	{		
		Perceptron[] perceptrons = createPerceptrons(perceptronCount, inputCount, activationFunction);
		
		return new Layer(perceptrons);
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
	
	private static Perceptron[] createPerceptrons(int perceptronCount, ActivationFunction activationFunction, double[] weights) throws PerceptronException 
	{
		Perceptron[] perceptrons = new Perceptron[perceptronCount];
		
		for (int i = 0; i < perceptronCount; i++) 
		{
			perceptrons[i] = new Perceptron(activationFunction, weights.length - 1);
			perceptrons[i].setWeights(weights);
		}
		
		return perceptrons;
	}
	
	private static Perceptron[] createPerceptrons(int perceptronCount, int inputCount, ActivationFunction activationFunction) throws PerceptronException 
	{
		Perceptron[] perceptrons = new Perceptron[perceptronCount];
		
		for (int i = 0; i < perceptronCount; i++) 
		{
			perceptrons[i] = new Perceptron(activationFunction, inputCount);
			perceptrons[i].setWeights(generateRandomWeights(inputCount + 1));
		}
		
		return perceptrons;
	}
}
