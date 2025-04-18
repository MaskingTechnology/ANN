package nl.uitdehoogte.ann.data;

import java.io.IOException;
import java.util.Random;

public class RandomIdxReader implements IdxReader
{
	private IdxReader reader;
	private Random random;

	public RandomIdxReader(IdxReader reader)
	{
		this.reader = reader;

		random = new Random();
	}
	
	public void read() throws IOException
	{
		reader.read();
	}
	
	public Sample getNextSample()
	{
		Sample[] samples = getAllSamples();

		int index = random.nextInt(samples.length);

		return samples[index];
	}

	public Sample[] getAllSamples()
	{
		return reader.getAllSamples();
	}
}
