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
	
	public Sample(byte[] data, Dimension dimension)
	{
		this((byte)0, data, dimension);
	}
	
	public byte getNumber() 
	{
		return this.number;
	}

	public byte[] getData() 
	{
		return this.data;
	}
	
	public void setData(byte[] data)
	{
		this.data = data;
	}

	public double[] getNormalizedDoubleData()
	{
		double[] result = new double[this.data.length];
		int temp;
		
		for (int i = 0; i < result.length; i++)
		{
			temp = (int)data[i] & 0x000000FF;
			
			result[i] = (double)temp / 0xFF;
		}
		
		return result;	
	}
	
	public Dimension getDimension() 
	{
		return this.dimension;
	}
}
