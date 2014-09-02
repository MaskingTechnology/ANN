package nl.uitdehoogte.ann.test;

import nl.uitdehoogte.ann.Perceptron;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.activation.*;

import org.junit.Test;

import junit.framework.TestCase;

public class PerceptronTest extends TestCase
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
	public void testGetOutputLinearPositive() throws PerceptronException
	{
		//sum of the weights * input should be greater than zero
		ActivationFunction activationFunction = new LinearActivationFunction();
		Perceptron perceptron = new Perceptron(activationFunction, 2);
		
		double[] weights = new double[] {0.5, 1, 0.5};
		double[] input = new double[] {1, 1};
		double output;
		
		perceptron.setWeights(weights);
		output = perceptron.getOutput(input);
		
		assertEquals(1.0, output, 0.0);
	}
	
	public void testGetOutputLinearNegative() throws PerceptronException
	{
		ActivationFunction activationFunction = new LinearActivationFunction();
		Perceptron perceptron = new Perceptron(activationFunction, 2);
		
		double[] weights = new double[] {0.5, 1.0, 0.5};
		double[] input = new double[] {-1, 1.0};
		double output;
		
		perceptron.setWeights(weights);
		output = perceptron.getOutput(input);
		
		assertEquals(-1.0, output, 0.0);
	}
	
	@Test
	public void testGetOutputSigmoid1() throws PerceptronException
	{
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		Perceptron perceptron = new Perceptron(activationFunction, 2);
		
		double[] weights = new double[] {0.5, 1.0, 0.5};
		double[] input = new double[] {-1, 1};
		double output;
		
		perceptron.setWeights(weights);
		output = perceptron.getOutput(input);
		
		assertEquals(0.5, output, 0.0);
	}
	
	@Test
	public void testGetOutputSigmoid2() throws PerceptronException
	{
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		Perceptron perceptron = new Perceptron(activationFunction, 2);
		
		double[] weights = new double[] {0.5, 1.0, 0.5};
		double[] input = new double[] {-1, 1};
		double output;
		
		perceptron.setWeights(weights);
		output = perceptron.getOutput(input);
		
		assertEquals(0.5, output, 0.0);
	}
	
	@Test
	public void testGetOutputSigmoid3() throws PerceptronException
	{
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		Perceptron perceptron = new Perceptron(activationFunction, 2);
		
		double[] weights = new double[] {0.5, 0.5, 0.5};
		double[] input = new double[] {1, 1};
		double output;
		
		perceptron.setWeights(weights);
		output = perceptron.getOutput(input);
		
		assertEquals(0.815, output, 0.05);
	}
}
