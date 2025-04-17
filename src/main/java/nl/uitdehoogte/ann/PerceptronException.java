package nl.uitdehoogte.ann;

import java.io.Serializable;

public class PerceptronException extends Exception implements Serializable
{
	private static final long serialVersionUID = 6984649122634013047L;

	public PerceptronException(String message) 
	{
		super(message);
	}
}
