package nl.uitdehoogte.ann.data;

import java.io.IOException;

public interface IdxReader 
{
	public void read() throws IOException;
	
	public Sample getNextSample();
	
	public Sample[] getAllSamples();	
}
