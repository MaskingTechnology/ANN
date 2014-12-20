package nl.uitdehoogte.ann.activation;

public class TangentActivationFunction implements ActivationFunction {

	private static final long serialVersionUID = -1944770031002105160L;

	public double execute(double input) 
	{
		return Math.tanh(input);
	}

}
