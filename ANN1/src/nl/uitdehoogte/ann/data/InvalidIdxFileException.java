package nl.uitdehoogte.ann.data;

import java.io.IOException;

@SuppressWarnings("serial")
public class InvalidIdxFileException extends IOException 
{
	public InvalidIdxFileException(String message) 
	{
		super(message);
	}
}
