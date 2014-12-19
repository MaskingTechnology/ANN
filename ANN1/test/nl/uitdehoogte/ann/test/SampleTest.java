package nl.uitdehoogte.ann.test;

import java.io.IOException;

import org.junit.Test;

import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.Sample;
import junit.framework.TestCase;

public class SampleTest extends TestCase
{
	private Sample[] samples;
	
	public SampleTest() throws IOException
	{
        IdxReader reader = new IdxReader("data/train-labels.idx1-ubyte", "data/train-images.idx3-ubyte");

		samples = reader.read();
	}
	
	@Override
	public void setUp()
	{
		
	}
	
	@Test
	public void testSampleData()
	{
		byte[] bytes = samples[0].getData();
		double[] doubleData = samples[0].getDoubleData();
		int width = samples[0].getDimension().width;
		
		System.out.println(samples[0].getNumber());
		
		for(int i = 0; i < bytes.length; i++)
		{
			System.out.printf("%02X ", bytes[i]);
			if((i + 1) % width == 0)
			{
				System.out.println();
			}
		}
		
		System.out.println();
		
		for(int i = 0; i < doubleData.length; i++)
		{
			System.out.print(doubleData[i] + " ");
			if((i + 1) % width == 0)
			{
				System.out.println();
			}
		}		
	}
}
