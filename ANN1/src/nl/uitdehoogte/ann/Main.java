package nl.uitdehoogte.ann;

import java.io.IOException;
import java.util.Random;

import nl.uitdehoogte.ann.activation.*;
import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkBuilder;
import nl.uitdehoogte.ann.repository.NetworkReader;
import nl.uitdehoogte.ann.repository.NetworkWriter;
import nl.uitdehoogte.ann.trainer.NetworkTrainer;
import nl.uitdehoogte.ann.trainer.NumberNetworkTrainer;
import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;

public class Main 
{

	public static void main(String[] args)  
	{	
		//createAndTrainNetwork("data/test2.dat");
		readAndExecuteNetwork("data/test2.dat");
	}
	
	private static void readAndExecuteNetwork(String inputFileName)
	{
		try
		{
			Network network = NetworkReader.read(inputFileName);
			
			Sample[] samples = readIdxFiles("data/t10k-labels.idx1-ubyte",
                                            "data/t10k-images.idx3-ubyte");
			
			double[] input,
			         output;
			int correct = 0,
			    incorrect = 0;
			
			for(int i = 0; i < samples.length; i++)
			{
				input = samples[i].getDoubleData();
				output = network.getOutput(input);
				
				if(checkOutput(samples[i].getNumber(), output))
				{
					correct++;
				}
				else
				{
					incorrect++;
				}
				
				//printOutput(output);
			}
			
			System.out.println();
			System.out.println("Samples  : " + samples.length);
			System.out.println("Correct  : " + correct);
			System.out.println("Incorrect: " + incorrect);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static boolean checkOutput(byte number, double[] output)
	{
		double max = 0;
		int index = 0;
		
		for(int i = 0; i < output.length; i++)
		{
			if(output[i] > max)
			{
				max = output[i];
				index = i;
			}
		}
		//System.out.println("max: " + max + " index: " + index + " number: " + number);
		
		return index == ((int)number & 0x000000FF);
	}
	
	private static void createAndTrainNetwork(String outputFileName)
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
			
			NetworkTrainer networkTrainer = new NumberNetworkTrainer(network, new SigmoidErrorCalculator());
			
			long start = System.currentTimeMillis();
			
			for(int i = 0; i < samples.length; i++)
			{
				networkTrainer.train(samples[i]);
			}
			
			for(int i = 0; i < samples.length; i++)
			{
				networkTrainer.train(samples[i]);
			}
			
			long end = System.currentTimeMillis();
			
			output = network.getOutput(input);
			
			System.out.println(end - start);
			
			NetworkWriter.write(network, outputFileName);
		}
		catch(Exception pe)
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
