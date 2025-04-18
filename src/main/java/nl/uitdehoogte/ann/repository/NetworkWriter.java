package nl.uitdehoogte.ann.repository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import nl.uitdehoogte.ann.Network;

public class NetworkWriter 
{
	public static void write(Network network, String fileName)
	{
		try
		{
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName));

			writer.writeObject(network);

			writer.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
