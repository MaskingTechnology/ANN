package nl.uitdehoogte.ann.data;

import java.awt.Dimension;
import java.io.IOException;

public class Main 
{
	public static void main(String[] args) 
	{
		IdxFileReader reader = new IdxFileReader(
				"C:/Users/Bas Meeuwissen/Documents/Prog/handwritten dataset/train-labels.idx1-ubyte",
				"C:/Users/Bas Meeuwissen/Documents/Prog/handwritten dataset/train-images.idx3-ubyte");
		
		try 
		{
			reader.read();
			Sample[] samples = reader.getAllSamples();
			
			printSample(samples[1]);
			
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
		}
	}
	
	private static void printSample(Sample sample) 
	{
		byte[] data = sample.getData();
		Dimension dimension = sample.getDimension();
		
		int index = 0;
		
		System.out.println(sample.getNumber());
		
		for (int y = 0; y < dimension.height; y++) 
		{
			for (int x = 0; x < dimension.width; x++) 
			{
				System.out.printf("%02X ", data[index++]);
			}
			
			System.out.println();
		}		
	}
}
