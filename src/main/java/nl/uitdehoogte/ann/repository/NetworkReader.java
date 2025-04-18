package nl.uitdehoogte.ann.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import nl.uitdehoogte.ann.Network;

public class NetworkReader 
{
	public static Network read(String fileName)
	{
		try
		{
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName));

			Network network = (Network) reader.readObject();

			reader.close();

			return network;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
