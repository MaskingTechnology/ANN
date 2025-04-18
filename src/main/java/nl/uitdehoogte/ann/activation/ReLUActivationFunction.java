package nl.uitdehoogte.ann.activation;

import nl.uitdehoogte.ann.activation.error.LinearErrorCalculator;
import nl.uitdehoogte.ann.activation.error.ErrorCalculator;

public class ReLUActivationFunction implements ActivationFunction
{
    private static final long serialVersionUID = -1421072142407441918L;

    public double execute(double input)
    {
        return Math.max(0, input);
    }

    public ErrorCalculator getErrorCalculator()
    {
        return new LinearErrorCalculator();
    }
}
