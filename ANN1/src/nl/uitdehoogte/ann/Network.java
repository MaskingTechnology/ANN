package nl.uitdehoogte.ann;


public class Network 
{
	private Layer[] layers;
	
	public Network(Layer[] layers)
	{
		this.layers = layers;
	}
	
	private double[] getInputLayerOutput(double[] input) throws PerceptronException
	{
		double[] output = new double[input.length];
		
		for (int i = 0; i < output.length; i++)
		{
			output[i] = layers[0].getOutput(new double[] {input[i]})[0];
		}
		
		return output; 
	}
	
	public double[] getOutput(double[] input) throws PerceptronException
	{
		double[] output = getInputLayerOutput(input);
		
		for (int i = 1; i < layers.length; i++)
		{
			output = layers[i].getOutput(output);
		}
		
		return output; 
	}
	
	public Layer[] getLayers()
	{
		return this.layers;
	}
}
