package nl.uitdehoogte.ann.test;

import nl.uitdehoogte.ann.trainer.calculator.error.SigmoidErrorCalculator;
import org.junit.Test;

import junit.framework.TestCase;

public class SigmoidErrorCalculatorTest extends TestCase
{
	private SigmoidErrorCalculator calculator;
	
	public SigmoidErrorCalculatorTest()
	{
		calculator = new SigmoidErrorCalculator();
	}
	
	@Test
	public void testCalculateOutputError()
	{
		double output = calculator.calculateOutputError(0.685, 0.5);
		
		assertEquals(-0.039918375, output, 0.000001);
	}
	
	@Test
	public void testCalculateHiddenError()
	{
		double output = calculator.calculateHiddenError(0.685, 0.272189, -0.0406);
		
		assertEquals(-0.0023845022, output, 0.00001);
	}
}
