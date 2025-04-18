package nl.uitdehoogte.ann;

import java.io.IOException;
import java.util.Random;

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
    public static void main(String[] args)
    {
        int maxCorrect = 0,
                correct = 0,
                dataIndex = 20;

        for(int i = 0; i < 10000; i++)
        {
            createAndTrainNetwork("data/test" + dataIndex + ".dat");
            correct = readAndExecuteNetwork("data/test" + dataIndex + ".dat");

            System.out.print("Done\t" + (i + 1));

            if(correct > maxCorrect)
            {
                maxCorrect = correct;
                dataIndex++;
                System.out.print("\tCorrect: " + correct + " ");
            }

            System.out.println();
        }

        System.out.println(maxCorrect);
    }

    private static int readAndExecuteNetwork(String inputFileName)
    {
        int[] correctValues   = new int[10],
                incorrectValues = new int[10];

        int correct = 0;

        try
        {
            Network network = NetworkReader.read(inputFileName);

            Sample[] samples = readIdxFiles("data/t10k-labels.idx1-ubyte",
                    "data/t10k-images.idx3-ubyte");

            double[] input,
                    output;

            for(int i = 0; i < samples.length; i++)
            {
                input = samples[i].getNormalizedDoubleData();
                output = network.getOutput(input);

                if(checkOutput(samples[i].getNumber(), output, correctValues, incorrectValues))
                {
                    correct++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return correct;
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

    private static void createAndTrainNetwork(String outputFileName)
    {
        int[] perceptrons = new int[] {784, 96, 10};
        int iterations = 350001;

        //ActivationFunction activationFunction = new BinairyActivationFunction();
        ActivationFunction activationFunction = new SigmoidActivationFunction();
        //ActivationFunction activationFunction = new TangentActivationFunction();
        //ActivationFunction activationFunction = new BogoActivationFunction();

        try
        {
            Network network = NetworkBuilder.build(perceptrons, activationFunction);

            IdxReader reader = createIdxReader("data/train-labels.idx1-ubyte",
                    "data/train-images.idx3-ubyte");

            reader = new RandomIdxReader(reader);

            NetworkTrainer networkTrainer = new NumberNetworkTrainer(network);
            networkTrainer.setLearningRate(0.39);

            //long start = System.currentTimeMillis();

            while(--iterations > 0)
            {
                networkTrainer.train(reader.getNextSample());
            }

            //long end = System.currentTimeMillis();

            //System.out.println(end - start);

            NetworkWriter.write(network, outputFileName);
        }
        catch(Exception pe)
        {
            pe.printStackTrace();
        }
    }

    private static Sample[] readIdxFiles(String Idx1FileName, String Idx3FileName)
    {
        IdxFileReader reader = new IdxFileReader(Idx1FileName, Idx3FileName);

        try
        {
            reader.read();
            return reader.getAllSamples();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private static IdxReader createIdxReader(String Idx1FileName, String Idx3FileName)
    {
        IdxReader reader = new IdxFileReader(Idx1FileName, Idx3FileName);

        try
        {
            reader.read();
            return reader;
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
