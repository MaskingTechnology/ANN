package nl.uitdehoogte.ann.test;

import java.io.IOException;

import org.junit.Test;

import nl.uitdehoogte.ann.data.IdxFileReader;
import nl.uitdehoogte.ann.data.Sample;
import junit.framework.TestCase;

public class SampleTest extends TestCase
{
	private Sample[] samples;
	
	public SampleTest() throws IOException
	{
        IdxFileReader reader = new IdxFileReader("data/train-labels.idx1-ubyte", "data/train-images.idx3-ubyte");

        reader.read();
		samples = reader.getAllSamples();
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
		int width = samples[0].getDimension().width,
		    height = samples[0].getDimension().height;
		    
		System.out.println("Width: " + width + " height: " + height);
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
