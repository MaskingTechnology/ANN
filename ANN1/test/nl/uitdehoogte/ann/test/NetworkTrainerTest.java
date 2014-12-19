package nl.uitdehoogte.ann.test;

import java.awt.Dimension;

import org.junit.Test;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.Perceptron;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.activation.ActivationFunction;
import nl.uitdehoogte.ann.activation.SigmoidActivationFunction;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkBuilder;
import nl.uitdehoogte.ann.trainer.SampleSigmoidNetworkTrainer;
import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;
import junit.framework.TestCase;

public class NetworkTrainerTest extends TestCase
{
	private SampleSigmoidNetworkTrainer networkTrainer;
	private Sample sample;
	private Network network;
	
	@Override
	public void setUp() throws PerceptronException
	{
		sample = new Sample((byte)1, new byte[]{0,0,0,0,0,0,0,0,0,0,0}, new Dimension(1,1));

		//input layer, hidden layer, output layer
		int[] perceptroncount = new int[] {2, 2, 1};
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		
		network = NetworkBuilder.build(perceptroncount, activationFunction);
		
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
		
		networkTrainer = new SampleSigmoidNetworkTrainer(network, new SigmoidErrorCalculator());
	}
	
	//@Test
	public void testTrain()
	{
		
	}
	
	//@Test
	public void testRunSample()
	{
		
	}
	
	//@Test
	public void testSetExpectedOutput()
	{
		Layer[] layers = network.getLayers();
		Layer outputLayer = layers[layers.length - 1];
		
		networkTrainer.setExpectedOutput(outputLayer, new double[]{0.5});
		
		Perceptron[] perceptrons = outputLayer.getPerceptrons();
		
		assertEquals(0.5, perceptrons[0].getLastExpectedOutput());
	}
	
	//@Test
	public void testSetLastOutputErrors() throws PerceptronException
	{
		double[] input = new double[] {0.35, 0.9};
		double[] output = network.getOutput(input);
		
		Layer[] layers = network.getLayers();
		Layer outputLayer = layers[layers.length - 1];
		
		networkTrainer.setExpectedOutput(outputLayer, new double[]{0.5});
		networkTrainer.setLastOutputErrors(outputLayer);
		
		assertEquals(-0.0406, outputLayer.getPerceptrons()[0].getLastError(), 0.0005);
	}
	
	//@Test
	public void testSetWeights() throws PerceptronException
	{
		double[] input = new double[] {0.35, 0.9};
		double[] output = network.getOutput(input);
		
		Layer[] layers = network.getLayers();
		Layer outputLayer = layers[2];
		Layer hiddenLayer = layers[1];
		
		networkTrainer.setExpectedOutput(outputLayer, new double[]{0.5});
		networkTrainer.setLastOutputErrors(outputLayer);
		networkTrainer.setWeights(outputLayer, hiddenLayer);
		
		double[] weights = outputLayer.getPerceptrons()[0].getWeights();
		
		/*
		 * Input A = 0.35 * 0.1 + 0.9 * 0.8 = 0.755  => Output A = 0.6802671967
		 * Input B = 0.9 * 0.6 + 0.35 * 0.4 = 0.68   => Output B = 0.6637386974
		 * Input C = 0.3 * Output A + 0.9 * Output B => Output C = 0.6902834929
		 * 
		 * Error C = (0.5 - Output C)(1 - Output C)Output C => -0.04068112511
		 * 
		 * Weights[0] = 0   + Error C              => -0.04068112511
		 * Weights[1] = 0.3 + (Error C * Output A) => 0.2723259651
		 * Weights[2] = 0.9 + (Error C * Output B) => 0.872998363
		 */
		
		assertEquals(-0.04068112511, weights[0], 0.00001);
		assertEquals(0.2723259651, weights[1], 0.00001);
		assertEquals(0.872998363, weights[2], 0.00001);
	}

	//@Test
	public void testSetHiddenLayerErrors() throws PerceptronException
	{
		double[] input = new double[] {0.35, 0.9};
		double[] output = network.getOutput(input);
		
		Layer[] layers = network.getLayers();
		Layer outputLayer = layers[2];
		Layer hiddenLayer = layers[1];
		
		networkTrainer.setExpectedOutput(outputLayer, new double[]{0.5});
		networkTrainer.setLastOutputErrors(outputLayer);
		networkTrainer.setWeights(outputLayer, hiddenLayer);
		networkTrainer.setHiddenLayerErrors(hiddenLayer, outputLayer);
		
		/*
		 * Input A = 0.35 * 0.1 + 0.9 * 0.8 = 0.755  => Output A = 0.6802671967
		 * Input B = 0.9 * 0.6 + 0.35 * 0.4 = 0.68   => Output B = 0.6637386974
		 * Input C = 0.3 * Output A + 0.9 * Output B => Output C = 0.6902834929
		 * 
		 * Error C = (0.5 - Output C)(1 - Output C)Output C => -0.04068112511
		 * 
		 * Weights[0] = 0   + Error C              => -0.04068112511
		 * Weights[1] = 0.3 + (Error C * Output A) => 0.2723259651
		 * Weights[2] = 0.9 + (Error C * Output B) => 0.872998363
		 * 
		 * Error A = Error C * weights[1] * (1 - Output A) * Output A => -0,002409621
		 * Error B = Error C * weights[2] * (1 - Output B) * Output B => -0,007926481
		 */

		assertEquals(-0.002409621, hiddenLayer.getPerceptrons()[0].getLastError(), 0.00001);
		assertEquals(-0.007926481, hiddenLayer.getPerceptrons()[1].getLastError(), 0.00001);
	}
	
	@Test
	public void testSetHiddenLayerWeights() throws PerceptronException
	{
		double[] input = new double[] {0.35, 0.9};
		double[] output = network.getOutput(input);
		
		Layer[] layers = network.getLayers();
		Layer outputLayer = layers[2];
		Layer hiddenLayer = layers[1];
		Layer inputLayer  = layers[0];
		
		networkTrainer.setExpectedOutput(outputLayer, new double[]{0.5});
		networkTrainer.setLastOutputErrors(outputLayer);
		networkTrainer.setWeights(outputLayer, hiddenLayer);
		networkTrainer.setHiddenLayerErrors(hiddenLayer, outputLayer);
		networkTrainer.setWeights(hiddenLayer, inputLayer);
		
		/*
		 * Input A = 0.35 * 0.1 + 0.9 * 0.8 = 0.755  => Output A = 0.6802671967
		 * Input B = 0.9 * 0.6 + 0.35 * 0.4 = 0.68   => Output B = 0.6637386974
		 * Input C = 0.3 * Output A + 0.9 * Output B => Output C = 0.6902834929
		 * 
		 * Error C = (0.5 - Output C)(1 - Output C)Output C => -0.04068112511
		 * 
		 * Weights[0] = 0   + Error C              => -0.04068112511
		 * Weights[1] = 0.3 + (Error C * Output A) => 0.2723259651
		 * Weights[2] = 0.9 + (Error C * Output B) => 0.872998363
		 * 
		 * Error A = Error C * weights[1] * (1 - Output A) * Output A => -0,002409621
		 * Error B = Error C * weights[2] * (1 - Output B) * Output B => -0,007926481
		 * 
		 * Perceptron 1
		 * Weights[0] = 0   + Error A              => -0,002409621
		 * Weights[1] = 0.1 + (Error A * Output X) =>  0,099156633
		 * Weights[2] = 0.8 + (Error A * Output Z) =>  0,797831341
		 * 
		 * Perceptron 2
		 * Weights[0] = 0   + Error B              => -0,007926481
		 * Weights[1] = 0.4 + (Error B * Output X) =>  0,397225732
		 * Weights[2] = 0.6 + (Error B * Output Z) =>  0,592866167
		 */
		double[] weights = hiddenLayer.getPerceptrons()[0].getWeights();
		assertEquals(-0.002409621, weights[0], 0.00001);
		assertEquals(0.099156633, weights[1], 0.00001);
		assertEquals(0.797831341, weights[2], 0.00001);
		
		weights = hiddenLayer.getPerceptrons()[1].getWeights();
		assertEquals(-0.007926481, weights[0], 0.00001);
		assertEquals(0.397225732, weights[1], 0.00001);
		assertEquals(0.592866167, weights[2], 0.00001);
	}
}
