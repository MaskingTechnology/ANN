package nl.uitdehoogte.ann.trainer;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Neuron;
import nl.uitdehoogte.ann.NeuronException;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public abstract class NetworkTrainer
{
	private Network network;
	private double learningRate;
	
	public NetworkTrainer(Network network)
	{
		this.network = network;
		this.learningRate = 0.35;
	}

	public void setLearningRate(double learningRate)
	{
		this.learningRate = learningRate;
	}
	
	public void train(Sample sample) throws NeuronException
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
	
	public void runSample(Sample sample) throws NeuronException
	{
		double[] input = sample.getNormalizedDoubleData();
		
		network.getOutput(input);
	}
	
	protected abstract double[] createExpectedValues(Sample sample, Layer layer);
	
	public void setExpectedOutput(Layer outputLayer, double[] expectedOutput)
	{
		Neuron[] neurons = outputLayer.getNeurons();
		
		for (int i = 0; i < neurons.length; i++)
		{
			neurons[i].setLastExpectedOutput(expectedOutput[i]);
		}
	}
	
	public void setLastOutputErrors(Layer outputLayer)
	{
		Neuron[] neurons = outputLayer.getNeurons();
		ErrorCalculator errorCalculator = outputLayer.getErrorCalculator();
		
		for (int i = 0; i < neurons.length; i++)
		{
			double errorValue = errorCalculator.calculateOutputError(neurons[i].getLastOutput(), neurons[i].getLastExpectedOutput());

			neurons[i].setLastError(errorValue);
		}
	}

	public void setWeights(Layer targetLayer, Layer sourceLayer) throws NeuronException
	{
		Neuron[] targetNeurons = targetLayer.getNeurons();
		Neuron[] sourceNeurons = sourceLayer.getNeurons();
		
		for (int i = 0; i < targetNeurons.length; i++)
		{
			double[] currentWeights = targetNeurons[i].getWeights();
			double[] newWeights = new double[currentWeights.length];
			
			//update own bias
			newWeights[0] = currentWeights[0] + (targetNeurons[i].getLastError() * learningRate);
			
			for (int j = 0; j < sourceNeurons.length; j++)
			{
				newWeights[j + 1] = calculateWeight(currentWeights[j + 1], targetNeurons[i].getLastError(), sourceNeurons[j].getLastOutput(), learningRate);
			}
			
			targetNeurons[i].setWeights(newWeights);
		}
	}
	
	private double calculateWeight(double weight, double error, double output, double learningRate)
	{
		return weight + (learningRate * error * output);
	}
	
	public void setHiddenLayerErrors(Layer currentLayer, Layer previousLayer)
	{
		//current = hidden, previous = output
		Neuron[] previousNeurons = previousLayer.getNeurons();
		Neuron[] currentNeurons = currentLayer.getNeurons();
		ErrorCalculator errorCalculator = currentLayer.getErrorCalculator();
		
		for (int i = 0; i < previousNeurons.length; i++)
		{
			double[] weights = previousNeurons[i].getWeights();
			
			for (int j = 0; j < weights.length - 1; j++)
			{
				currentNeurons[j].setLastError(calculateHiddenError(errorCalculator, currentNeurons[j].getLastOutput(), weights[j + 1], previousNeurons[i].getLastError()));
			}
		}
	}
	
	private double calculateHiddenError(ErrorCalculator errorCalculator, double actualOutput, double weight, double error)
	{
		return errorCalculator.calculateHiddenError(actualOutput, weight, error);
	}
}
