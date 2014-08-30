package nl.uitdehoogte.ann;

import java.io.Serializable;

public class PerceptronException extends Exception implements Serializable
{
	public PerceptronException(String message) 
	{
		super(message);
	}
}
