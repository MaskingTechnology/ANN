package nl.uitdehoogte.ann.trainer;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.data.Sample;

public class SampleSigmoidNetworkTrainer extends NetworkTrainer
{
	public SampleSigmoidNetworkTrainer(Network network)
	{
		super(network);
	}
	
	protected double[] createExpectedValues(Sample sample, Layer layer)
	{
		double[] values = new double[1];
		
		values[0] = 0.5;
		
		return values;
	}

}
