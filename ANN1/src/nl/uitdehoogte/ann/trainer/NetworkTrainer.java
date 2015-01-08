package nl.uitdehoogte.ann.trainer;

import java.util.Random;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Perceptron;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;

public abstract class NetworkTrainer
{
	private Network network;
	private ErrorCalculator errorCalculator;
	private double learningRate;
	
	public NetworkTrainer(Network network, ErrorCalculator errorCalculator)
	{
		this.network = network;
		this.errorCalculator = errorCalculator;
		this.learningRate = 0.35;
	}
	
	public void train(Sample[] samples) throws PerceptronException
	{
		for (int i = 0; i < samples.length; i++)
		{
			train(samples[i]);
		}
	}
	
	public void setLearningRate(double learningRate)
	{
		this.learningRate = learningRate;
	}
	
	public void train(Sample sample) throws PerceptronException
	{
		runSample(sample);
		
		Layer[] layers = network.getLayers();	
		Layer outputLayer = layers[layers.length - 1];
		Layer inputLayer = layers[0];
		
		double[] expectedOutput = createExpectedValues(sample, outputLayer);
		
		setExpectedOutput(outputLayer, expectedOutput);
		setLastOutputErrors(outputLayer);
		
		for(int i = layers.length - 1; i > 1; i--)
		{
			setWeights(layers[i], layers[i - 1]);
			setHiddenLayerErrors(layers[i - 1], layers[i]);
		}
		
		setWeights(layers[1], inputLayer);
	}
	
	public void runSample(Sample sample) throws PerceptronException
	{
		double[] input = sample.getNormalizedDoubleData();
		
		network.getOutput(input);
	}
	
	protected abstract double[] createExpectedValues(Sample sample, Layer layer);
	
	public void setExpectedOutput(Layer outputLayer, double[] expectedOutput)
	{
		Perceptron[] perceptrons = outputLayer.getPerceptrons();
		
		for (int i = 0; i < perceptrons.length; i++)
		{
			perceptrons[i].setLastExpectedOutput(expectedOutput[i]);
		}
	}
	
	public void setLastOutputErrors(Layer outputLayer)
	{
		Perceptron[] perceptrons = outputLayer.getPerceptrons();
		
		for (int i = 0; i < perceptrons.length; i++)
		{
			perceptrons[i].setLastError(calculateOutputError(perceptrons[i].getLastOutput(), perceptrons[i].getLastExpectedOutput()));
		}
	}
	
	private double calculateOutputError(double actualOutput, double expectedOutput)
	{
		return errorCalculator.calculateOutputError(actualOutput, expectedOutput);
	}
	
	public void setWeights(Layer targetLayer, Layer sourceLayer) throws PerceptronException
	{
		Perceptron[] targetPerceptrons = targetLayer.getPerceptrons();
		Perceptron[] sourcePerceptrons = sourceLayer.getPerceptrons();
		
		for (int i = 0; i < targetPerceptrons.length; i++)
		{
			double[] currentWeights = targetPerceptrons[i].getWeights();
			double[] newWeights = new double[currentWeights.length];
			
			//update own bias
			newWeights[0] = currentWeights[0] + (targetPerceptrons[i].getLastError() * learningRate); 
			
			for (int j = 0; j < sourcePerceptrons.length; j++)
			{
				newWeights[j + 1] = calculateWeight(currentWeights[j + 1], targetPerceptrons[i].getLastError(), sourcePerceptrons[j].getLastOutput(), learningRate);
			}
			
			targetPerceptrons[i].setWeights(newWeights);
		}
	}
	
	private double calculateWeight(double weight, double error, double output, double learningRate)
	{
		return weight + (learningRate * error * output);
	}
	
	public void setHiddenLayerErrors(Layer currentLayer, Layer previousLayer)
	{
		//current = hidden, previous = output
		Perceptron[] previousPerceptrons = previousLayer.getPerceptrons();
		Perceptron[] currentPerceptrons = currentLayer.getPerceptrons();
		
		for (int i = 0; i < previousPerceptrons.length; i++)
		{
			double[] weights = previousPerceptrons[i].getWeights();
			
			for (int j = 0; j < weights.length - 1; j++)
			{
				currentPerceptrons[j].setLastError(calculateHiddenError(currentPerceptrons[j].getLastOutput(), weights[j + 1], previousPerceptrons[i].getLastError()));
			}
		}
	}
	
	private double calculateHiddenError(double actualOutput, double weight, double error)
	{
		return errorCalculator.calculateHiddenError(actualOutput, weight, error);
	}
}
