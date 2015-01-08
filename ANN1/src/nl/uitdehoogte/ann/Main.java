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
import nl.uitdehoogte.ann.trainer.calculator.error.BinairyErrorCalculator;
import nl.uitdehoogte.ann.trainer.calculator.error.BogoErrorCalculator;
import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;
import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;
import nl.uitdehoogte.ann.trainer.calculator.error.TangentErrorCalculator;

public class Main 
{
	//Momentum ??
	//http://en.wikibooks.org/wiki/Artificial_Neural_Networks/Neural_Network_Basics
	//http://neuralnetworksanddeeplearning.com/chap1.html
	
	public static void main(String[] args)  
	{	
		createAndTrainNetwork("data/test15.dat");
		readAndExecuteNetwork("data/test15.dat");
	}
	
	private static void readAndExecuteNetwork(String inputFileName)
	{
		int[] correctValues   = new int[10],
			  incorrectValues = new int[10];
		
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
				input = samples[i].getNormalizedDoubleData();
				output = network.getOutput(input);
				
				if(checkOutput(samples[i].getNumber(), output, correctValues, incorrectValues))
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
			System.out.println();
			
			System.out.println("Correct numbers:");
			for(int i = 0; i < correctValues.length; i++)
			{
				System.out.println(i + ": " + correctValues[i]);
			}
			
			System.out.println();
			System.out.println("Incorrect numbers:");
			for(int i = 0; i < correctValues.length; i++)
			{
				System.out.println(i + ": " + incorrectValues[i]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static boolean checkOutput(byte number, double[] output, int[] correctValues, int[] incorrectValues)
	{
		double max = 0;
		int index = 0,
		    intNumber = ((int)number & 0x000000FF);
		
		boolean returnValue = false;
		
		for(int i = 0; i < output.length; i++)
		{
			if(output[i] > max)
			{
				max = output[i];
				index = i;
			}
		}
		//System.out.println("max: " + max + " index: " + index + " number: " + number);
		
		returnValue = index == intNumber;
		
		if(returnValue)
		{
			correctValues[intNumber]++;
		}
		else
		{
			incorrectValues[intNumber]++;
		}
		
		return returnValue;
	}
	
	private static void createAndTrainNetwork(String outputFileName)
	{
		int[] perceptrons = new int[] {784, 32, 10};
		
		//ActivationFunction activationFunction = new BinairyActivationFunction();
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		//ActivationFunction activationFunction = new TangentActivationFunction();
		//ActivationFunction activationFunction = new BogoActivationFunction();
		
		try
		{
			Network network = NetworkBuilder.build(perceptrons, activationFunction);

			Sample[] samples = readIdxFiles("data/train-labels.idx1-ubyte",
					                        "data/train-images.idx3-ubyte"); 
			
			double[] input = samples[0].getNormalizedDoubleData();
			double[] output = network.getOutput(input);
			
			NetworkTrainer networkTrainer = new NumberNetworkTrainer(network);
			networkTrainer.setLearningRate(0.39);
			
			long start = System.currentTimeMillis();
			
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
