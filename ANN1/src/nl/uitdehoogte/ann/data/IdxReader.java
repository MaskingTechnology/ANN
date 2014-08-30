package nl.uitdehoogte.ann.data;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IdxReader 
{
	private File idx1File;
	private File idx3File;
	private RandomAccessFile idx1Reader;
	private RandomAccessFile idx3Reader;

	private static final int IDX1_MAGIC_NUMBER = 0x00000801;
	private static final int IDX3_MAGIC_NUMBER = 0x00000803;
	
	public IdxReader(String idx1FileName, String idx3FileName) 
	{
		this.idx1File = new File(idx1FileName);
		this.idx3File = new File(idx3FileName);
	}
	
	public Sample[] read() throws IOException 
	{
		Sample[] samples = null;
		
		idx1Reader = new RandomAccessFile(idx1File, "r");
		idx3Reader = new RandomAccessFile(idx3File, "r");
		
		samples = readSamples();
		
		idx1Reader.close();
		idx3Reader.close();
		
		return samples;
	}
	
	private Sample[] readSamples() throws IOException 
	{
		validateIDXFiles();
		int numberOfItems = getNumberOfItems();
		
		Sample[] samples = new Sample[numberOfItems];
		
		Dimension dimension = readDimension();
		int sampleLength = dimension.width * dimension.height;
		
		for (int i = 0; i < samples.length; i++) 
		{
			samples[i] = readSample(sampleLength, dimension);
		}
		
		return samples;
	}
	
	private void validateIDXFiles() throws IOException 
	{
		int idx1MagicNumber = idx1Reader.readInt(), 
		    idx3MagicNumber = idx3Reader.readInt();
		
		if(idx1MagicNumber != IDX1_MAGIC_NUMBER || idx3MagicNumber != IDX3_MAGIC_NUMBER) 
		{
			throw new InvalidIdxFileException("Magic numbers incorrect");
		}
	}
	
	private int getNumberOfItems() throws IOException 
	{
		int idx1NumberOfItems = idx1Reader.readInt(),
		    idx3NumberOfItems = idx3Reader.readInt();
		
		if(idx1NumberOfItems != idx3NumberOfItems) 
		{
			throw new NotMatchingIdxFiles("Number of items not equal");
		}
		
		return idx1NumberOfItems;
	}
	
	private Dimension readDimension() throws IOException 
	{
		int width = idx3Reader.readInt(),
			height = idx3Reader.readInt();
		
		return new Dimension(width, height);
	}
	
	private Sample readSample(int sampleLength, Dimension dimension) throws IOException
	{
		byte number = idx1Reader.readByte();
		byte[] data = new byte[sampleLength];
		
		int bytesRead = idx3Reader.read(data);
		
		if(bytesRead != sampleLength) 
		{
			throw new InvalidIdxFileException("Incomplete sample list");
		}
		
		return new Sample(number, data, dimension);
	}
}
