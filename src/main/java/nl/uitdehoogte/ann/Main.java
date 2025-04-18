package nl.uitdehoogte.ann;

import nl.uitdehoogte.ann.activation.*;
import nl.uitdehoogte.ann.data.IdxFileReader;
import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkBuilder;
import nl.uitdehoogte.ann.repository.NetworkReader;
import nl.uitdehoogte.ann.repository.NetworkWriter;
import nl.uitdehoogte.ann.trainer.NetworkTrainer;
import nl.uitdehoogte.ann.trainer.NumberNetworkTrainer;

public class Main
{
    final static ActivationFunction SIGMOID = new SigmoidActivationFunction();

    final static String BASE_MODEL_FILE = "data/output/784_96_10_sigmoid.dat";
    final static String OUTPUT_MODEL_FILE = "data/output/784_96_10_sigmoid_1r.dat";

    public static void main(String[] args)
    {
//        createNetwork(new int[]{784, 96, 10}, SIGMOID, BASE_MODEL_FILE);

        Network network = NetworkReader.read(BASE_MODEL_FILE);

        trainAndTestNetwork(network, 1, 0.39);

        NetworkWriter.write(network, OUTPUT_MODEL_FILE);
    }

    private static void trainAndTestNetwork(Network network, int rounds, double learningRate)
    {
        trainNetwork(network, rounds, learningRate);

        double successPercentage = testNetwork(network);

        System.out.println("Success: " + successPercentage + "%");
    }

    private static void createNetwork(int[] neurons, ActivationFunction activationFunction, String modelFilename)
    {
        try
        {
            Network network = NetworkBuilder.build(neurons, activationFunction);

            NetworkWriter.write(network, modelFilename);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void trainNetwork(Network network, int rounds, double learningRate)
    {
        try
        {
            IdxReader reader = new IdxFileReader("data/input/train-labels.idx1-ubyte",
                    "data/input/train-images.idx3-ubyte");

            reader.read();

            NetworkTrainer networkTrainer = new NumberNetworkTrainer(network);

            for(int round = 0; round < rounds; round++)
            {
                networkTrainer.setLearningRate(learningRate);

                int iterations = reader.getAllSamples().length;

                while (--iterations > 0)
                {
                    networkTrainer.train(reader.getNextSample());
                }

                learningRate *= 0.5;
            }
        }
        catch(Exception pe)
        {
            pe.printStackTrace();
        }
    }

    private static double testNetwork(Network network)
    {
        int[] correctValues   = new int[10],
                incorrectValues = new int[10];

        double successRatio = 0.0;

        try
        {
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
