package nl.uitdehoogte.ann.data;

import java.io.IOException;

@SuppressWarnings("serial")
public class NotMatchingIdxFiles extends IOException 
{
	public NotMatchingIdxFiles(String message) 
	{
		super(message);
	}
}
