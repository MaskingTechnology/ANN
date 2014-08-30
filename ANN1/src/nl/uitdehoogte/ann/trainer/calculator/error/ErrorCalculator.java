package nl.uitdehoogte.ann.trainer.calculator.error;

public interface ErrorCalculator 
{
	public double calculateOutputError(double actualOutput, double expectedOutput);
	
	public double calculateHiddenError(double actualOutput, double weight, double error);
}
