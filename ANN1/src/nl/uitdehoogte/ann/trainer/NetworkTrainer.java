package nl.uitdehoogte.ann.trainer;

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
	
	public NetworkTrainer(Network network, ErrorCalculator errorCalculator)
	{
		this.network = network;
		this.errorCalculator = errorCalculator;
	}
	
	public void train(Sample[] samples) throws PerceptronException
	{
		for (int i = 0; i < samples.length; i++)
		{
			train(samples[0]);
		}
	}
	
	public void train(Sample sample) throws PerceptronException
	{
		runSample(sample);
		
		Layer[] layers = network.getLayers();	
		Layer outputLayer = layers[layers.length - 1];
		Layer inputLayer = layers[0];
		Layer[] hiddenLayers = new Layer[layers.length - 2];
		
		System.arraycopy(layers, 1, hiddenLayers, 0, hiddenLayers.length);
		
		double[] expectedOutput = createExpectedValues(sample, outputLayer);
		
		setExpectedOutput(outputLayer, expectedOutput);
		setLastOutputErrors(outputLayer);
		
		setWeights(outputLayer, hiddenLayers[0]);
		setHiddenLayerErrors(hiddenLayers[0], outputLayer);
		
		setWeights(hiddenLayers[0], inputLayer);
	}
	
	public void runSample(Sample sample) throws PerceptronException
	{
		double[] input = sample.getDoubleData();
		
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
			newWeights[0] = currentWeights[0] + targetPerceptrons[i].getLastError(); 
			
			for (int j = 0; j < sourcePerceptrons.length; j++)
			{
				newWeights[j + 1] = calculateWeight(currentWeights[j + 1], targetPerceptrons[i].getLastError(), sourcePerceptrons[j].getLastOutput());
			}
			
			targetPerceptrons[i].setWeights(newWeights);
		}
	}
	
	private double calculateWeight(double weight, double error, double output)
	{
		return weight + (error * output);
	}
	
	public void setHiddenLayerErrors(Layer currentLayer, Layer previousLayer)
	{
		//current = hidden, previous = output
		Perceptron[] previousPerceptrons = previousLayer.getPerceptrons();
		Perceptron[] currentPerceptrons = currentLayer.getPerceptrons();
		
		//for (int i = 0; i < currentPerceptrons.length; i++)
		//{
		//	currentPerceptrons[i].setLastError(0.0);
		//}
		
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
