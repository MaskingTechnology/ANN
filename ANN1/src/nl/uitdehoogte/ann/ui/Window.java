package nl.uitdehoogte.ann.ui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window extends JFrame
{
	private Canvas canvas;
	private JButton button;
	
	public Window()
	{
		setTitle("ANN");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		canvas = new Canvas();
		canvas.setLocation(40, 40);
		add(canvas);
		
		button = new JButton("Execute");
		button.setSize(100, 30);
		button.setLocation(350, 200);
		add(button);
		
		setVisible(true);
	}
	
	public JButton getButton()
	{
		return button;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
}
