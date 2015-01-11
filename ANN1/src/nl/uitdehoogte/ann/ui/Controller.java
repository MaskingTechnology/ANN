package nl.uitdehoogte.ann.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.PerceptronException;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkReader;

public class Controller implements ActionListener
{
	private Window window;
	private Network network;
	
	public Controller()
	{
		try
		{
			window = new Window();
		
			window.getButton().addActionListener(this);
		
			network = NetworkReader.read("data/test13.dat");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		execute();
	}	
	
	private void execute()
	{
		try
		{
			byte[][] data = getData();
		
			Sample sample = createSample(data);
		
			double[] output = runSample(sample);
			
			double max = 0;
			int index = 0;
			
			for(int i = 0; i < output.length; i++)
			{
				if(output[i] > max)
				{
					max = output[i];
					index = i;
				}
			}
			
			System.out.println(index);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private byte[][] getData()
	{
		return window.getCanvas().getPixels();
	}
	
	private Sample createSample(byte[][] data)
	{
		return new Sample(flattenData(data), new Dimension(28, 28));
	}
	
	private byte[] flattenData(byte[][] data)
	{
		byte[] result = new byte[data.length * data[0].length];
		int index = 0;
		
		for(int y = 0; y < data.length; y++)
		{
			for(int x = 0; x < data[0].length; x++)
			{
				result[index++] = data[y][x];
			}
		}
		
		return result;
	}
	
	private byte[][] inflateData(byte[] data, Dimension dimension)
	{
		byte[][] result = new byte[dimension.height][dimension.width];
		
		return result;
	}
	
	private double[] runSample(Sample sample) throws PerceptronException
	{
		return network.getOutput(sample.getNormalizedDoubleData());
	}
	
	public static void main(String[] args)
	{
		new Controller();
	}
}
