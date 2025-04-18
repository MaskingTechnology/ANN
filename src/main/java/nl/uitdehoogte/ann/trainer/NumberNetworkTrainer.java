package nl.uitdehoogte.ann.trainer;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.data.Sample;

public class NumberNetworkTrainer extends NetworkTrainer
{
	public NumberNetworkTrainer(Network network)
	{
		super(network);
	}

	protected double[] createExpectedValues(Sample sample, Layer layer)
	{
		double[] values = new double[layer.getNeurons().length];
		
		values[(int) sample.getNumber()] = 1;
		
		return values;
	}
}
