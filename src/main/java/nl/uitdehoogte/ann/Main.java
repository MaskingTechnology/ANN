package nl.uitdehoogte.ann;

import java.io.IOException;

import nl.uitdehoogte.ann.activation.*;
import nl.uitdehoogte.ann.data.IdxFileReader;
import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.RandomIdxReader;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkBuilder;
import nl.uitdehoogte.ann.repository.NetworkReader;
import nl.uitdehoogte.ann.repository.NetworkWriter;
import nl.uitdehoogte.ann.trainer.NetworkTrainer;
import nl.uitdehoogte.ann.trainer.NumberNetworkTrainer;

public class Main
{
    final static ActivationFunction SIGMOID = new SigmoidActivationFunction();
    final static ActivationFunction BINARY = new BinairyActivationFunction();
    final static ActivationFunction TANGENT = new TangentActivationFunction();
    final static ActivationFunction BOGO = new BogoActivationFunction();

    public static void main(String[] args)
    {
        int iterations = 60000;

        trainAndTest(new int[]{784, 96, 10}, SIGMOID, iterations, "data/output/784_96_10_sigmoid_60000.dat");
    }

    private static void trainAndTest(int[] perceptrons, ActivationFunction activationFunction, int iterations, String modelFilename)
    {
        createAndTrainNetwork(perceptrons, activationFunction, iterations, modelFilename);

        double successPercentage = readAndExecuteNetwork(modelFilename);

        System.out.println(modelFilename + " - success: " + successPercentage + "%");
    }

    private static void createAndTrainNetwork(int[] perceptrons, ActivationFunction activationFunction, int iterations, String outputFileName)
    {
        try
        {
            Network network = NetworkBuilder.build(perceptrons, activationFunction);

            IdxReader fileReader = new IdxFileReader("data/input/train-labels.idx1-ubyte",
                    "data/input/train-images.idx3-ubyte");

            IdxReader randomReader = new RandomIdxReader(fileReader);
            randomReader.read();

            NetworkTrainer networkTrainer = new NumberNetworkTrainer(network);
            networkTrainer.setLearningRate(0.39);

            while(--iterations > 0)
            {
                networkTrainer.train(randomReader.getNextSample());
            }

            NetworkWriter.write(network, outputFileName);
        }
        catch(Exception pe)
        {
            pe.printStackTrace();
        }
    }

    private static double readAndExecuteNetwork(String inputFileName)
    {
        int[] correctValues   = new int[10],
                incorrectValues = new int[10];

        double successRatio = 0.0;

        try
        {
            Network network = NetworkReader.read(inputFileName);

            IdxReader fileReader = new IdxFileReader("data/input/t10k-labels.idx1-ubyte",
                    "data/input/t10k-images.idx3-ubyte");

            fileReader.read();

            Sample[] samples = fileReader.getAllSamples();

            double[] input,
                    output;
            int correct = 0;

            for(int i = 0; i < samples.length; i++)
            {
                input = samples[i].getNormalizedDoubleData();
                output = network.getOutput(input);

                if(checkOutput(samples[i].getNumber(), output, correctValues, incorrectValues))
                {
                    correct++;
                }
            }

            successRatio = (double)correct / (double)samples.length;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return successRatio * 100;
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
}
