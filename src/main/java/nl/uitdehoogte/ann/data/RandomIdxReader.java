package nl.uitdehoogte.ann.data;

import java.io.IOException;
import java.util.*;

public class RandomIdxReader implements IdxReader
{
	private IdxReader reader;
	private List<Integer> indexes = new ArrayList<Integer>();

	private Sample[] samples;
	private int currentIndex = 0;

	public RandomIdxReader(IdxReader reader)
	{
		this.reader = reader;
	}
	
	public void read() throws IOException
	{
		reader.read();

		samples = reader.getAllSamples();

		for (int index = 0; index < samples.length; index++)
		{
			indexes.add(index);
		}

		Collections.shuffle(indexes);
	}
	
	public Sample getNextSample()
	{
		int index = currentIndex++ % samples.length;

		return samples[index];
	}

	public Sample[] getAllSamples()
	{
		return samples;
	}
}
