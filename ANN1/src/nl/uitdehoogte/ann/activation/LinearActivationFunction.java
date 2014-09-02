package nl.uitdehoogte.ann.activation;

public class LinearActivationFunction implements ActivationFunction 
{
	private static final long serialVersionUID = -1421072142407441918L;

	public double execute(double input) 
	{
		return input > 0 ? 1.0: -1.0;
	}
}
