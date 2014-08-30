package nl.uitdehoogte.ann.data;

import java.awt.Dimension;

public class Sample 
{
	private byte number;
	private byte[] data;
	private Dimension dimension;
	
	public Sample(byte number, byte[] data, Dimension dimension) 
	{
		this.number = number;
		this.data = data;
		this.dimension = dimension;
	}
	
	public byte getNumber() 
	{
		return this.number;
	}
	
	public byte[] getData() 
	{
		return this.data;
	}
	
	public double[] getDoubleData()
	{
		double[] result = new double[this.data.length]
				;
		for (int i = 0; i < result.length; i++)
		{
			result[i] = (double) data[i];
		}
		
		return result;
	}
	
	public Dimension getDimension() 
	{
		return this.dimension;
	}
}
