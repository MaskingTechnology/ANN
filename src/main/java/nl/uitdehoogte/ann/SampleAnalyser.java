package nl.uitdehoogte.ann;

import nl.uitdehoogte.ann.data.Sample;

public class SampleAnalyser
{
	private Network network;
	
	public SampleAnalyser(Network network)
	{
		this.network = network;
	}
	
	public double[] analyse(Sample sample) throws PerceptronException
	{
		double[] input = sample.getNormalizedDoubleData();
		double[] output = network.getOutput(input);
		
		setNumber(sample, output);
		
		return output;
	}
	
	private void setNumber(Sample sample, double[] output)
	{
		byte number = 0;
		double value = output[0];
		
		for (int i = 1; i < output.length; i++)
		{
			if(output[i] > value)
			{
				number = (byte) i;
				value = output[i];
			}
		}
		
		sample.setNumber(number);
	}
}
