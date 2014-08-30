package nl.uitdehoogte.ann;

import java.io.IOException;
import java.util.Random;

import nl.uitdehoogte.ann.activation.*;
import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.trainer.NetworkTrainer;
import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;

public class Main 
{

	public static void main(String[] args) 
	{
		int[] perceptrons = new int[] {784, 96, 10};
		//int[] perceptrons = new int[] {3, 4, 3};
		ActivationFunction activationFunction = new SigmoidActivationFunction();
			
		try
		{
			Network network = NetworkBuilder.build(perceptrons, activationFunction);

			//printNetwork(network);
			
			//double[] input = generateRandomInput(perceptrons[0]);
			Sample[] samples = readIdxFiles("data/train-labels.idx1-ubyte",
					                        "data/train-images.idx3-ubyte"); 
			
			double[] input = samples[0].getDoubleData();
			double[] output = network.getOutput(input);
			printOutput(output);
			System.out.println();
			
			
			NetworkTrainer networkTrainer = new NetworkTrainer(network, new SigmoidErrorCalculator());
			
			long start = System.currentTimeMillis();
			
			for(int i=0; i<1; i++)
			{
				networkTrainer.train(samples[i]);
			}
			
			long end = System.currentTimeMillis();
			
			output = network.getOutput(input);
			printOutput(output);
			
			System.out.println(end - start);
		}
		catch(PerceptronException pe)
		{
			pe.printStackTrace();
		}
	}
	
	private static double[] generateRandomInput(int inputCount)
	{
		double[] input = new double[inputCount];
		Random random = new Random();
		
		for (int i = 0; i < input.length; i++)
		{
			input[i] = random.nextGaussian();
		}
		
		return input;
	}

	private static void printNetwork(Network network)
	{
		Layer[] layers = network.getLayers();
		
		for (int i = 0; i < layers.length; i++)
		{
			Perceptron[] perceptrons = layers[i].getPerceptrons();
			
			for (int j = 0; j < perceptrons.length; j++)
			{
				printOutput(perceptrons[j].getWeights());
			}
		}
	}
	
	private static void printOutput(double[] output)
	{
		for (int i = 0; i < output.length; i++)
		{
			System.out.printf("%d: %f\r\n", i, output[i]);
		}
	}
	
	private static Sample[] readIdxFiles(String Idx1FileName, String Idx3FileName)
	{
		IdxReader reader = new IdxReader(Idx1FileName, Idx3FileName);
		
		try 
		{
			return reader.read();			
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
		}
		
		return null;
	}
}
