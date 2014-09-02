package nl.uitdehoogte.ann.test;

import java.awt.Dimension;

import junit.framework.TestCase;
import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Perceptron;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.activation.ActivationFunction;
import nl.uitdehoogte.ann.activation.SigmoidActivationFunction;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkBuilder;

import org.junit.Test;

public class SimpleTrainerTest extends TestCase
{
	@Override
	public void setUp()
	{
		
	}
	
	@Override
	public void tearDown()
	{
		
	}
	
	@Test
	public void testTrainer() throws PerceptronException
	{
		//input layer, hidden layer, output layer
		int[] iPerceptrons = new int[] {2, 2, 1};
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		
		Network network = NetworkBuilder.build(iPerceptrons, activationFunction);
		
		double[] input = new double[] {0.35, 0.6};
		
		Layer[] layers = network.getLayers();
		
		//Get the hidden layer perceptrons
		Perceptron[] perceptrons = layers[1].getPerceptrons();
		
		//Set weights to known values
		perceptrons[0].setWeights(new double[] {0.0, 0.1, 0.8});
		perceptrons[1].setWeights(new double[] {0.0, 0.4, 0.6});
		
		//Get the output layer perceptrons
		perceptrons = layers[2].getPerceptrons();
		
		//Set the weights to known values
		perceptrons[0].setWeights(new double[] {0.0, 0.3, 0.9});
		
		double[] output = network.getOutput(input);
		
		
		
		assertEquals(0.685, output[0], 0.001);
	}
}
