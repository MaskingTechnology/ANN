package nl.uitdehoogte.ann.trainer;

import nl.uitdehoogte.ann.Layer;
import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.trainer.calculator.error.ErrorCalculator;

public class SampleSigmoidNetworkTrainer extends NetworkTrainer
{
	public SampleSigmoidNetworkTrainer(Network network, ErrorCalculator errorCalculator) {
		super(network, errorCalculator);
	}
	
	protected double[] createExpectedValues(Sample sample, Layer layer)
	{
		double[] values = new double[1];
		
		values[0] = 0.5;
		
		return values;
	}

}
