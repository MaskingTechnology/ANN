package nl.uitdehoogte.ann.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.uitdehoogte.ann.Network;
import nl.uitdehoogte.ann.NeuronException;
import nl.uitdehoogte.ann.data.IdxReader;
import nl.uitdehoogte.ann.data.IdxFileReader;
import nl.uitdehoogte.ann.data.RandomIdxReader;
import nl.uitdehoogte.ann.data.Sample;
import nl.uitdehoogte.ann.repository.NetworkReader;

public class Controller implements ActionListener
{
	private Window window;
	private Network network;
	private IdxReader reader;
	
	public Controller()
	{
		try
		{
			window = new Window();
		
			window.getExecuteButton().addActionListener(this);
			window.getReadButton().addActionListener(this);
		
			network = NetworkReader.read("data/output/784_96_10_sigmoid_1r_83p.dat");
			
			reader = new RandomIdxReader(new IdxFileReader("data/input/t10k-labels.idx1-ubyte",
                    									   "data/input/t10k-images.idx3-ubyte"));
			
			reader.read();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
	    if(e.getSource() == window.getExecuteButton())
	    {
	    	execute();
	    }
	    else
	    {
	    	readNextSample();
	    	execute();
	    }
	}	
	
	private void readNextSample()
	{
		Sample sample = reader.getNextSample();
		
		window.getCanvas().setPixels(inflateData(sample.getData(), sample.getDimension()));
		
		window.getCanvas().repaint();
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
			StringBuffer statistics = new StringBuffer();
			
			for(int i = 0; i < output.length; i++)
			{
				statistics.append(String.format("%d: %.10f\n", i, output[i]));
				if(output[i] > max)
				{
					max = output[i];
					index = i;
				}
			}
			
			window.getResultLabel().setText("" + index);
			window.getStatisticsTextArea().setText(statistics.toString());
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
		
		for(int y = 0; y < dimension.height; y++)
		{
			for(int x = 0; x < dimension.width; x++)
			{
				result[y][x] = data[y * dimension.width + x];
			}
		}
		
		return result;
	}
	
	private double[] runSample(Sample sample) throws NeuronException
	{
		return network.getOutput(sample.getNormalizedDoubleData());
	}
	
	public static void main(String[] args)
	{
		new Controller();
	}
}
