package nl.uitdehoogte.ann.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Window extends JFrame
{
	private Canvas canvas;
	private JLabel resultLabel;
	private JTextArea statisticsTextArea;
	private JButton executeButton;
	private JButton readButton;
	
	public Window()
	{
		setTitle("ANN");
		setSize(715, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		canvas = new Canvas();
		canvas.setLocation(40, 40);
		add(canvas);
		
		executeButton = new JButton("Execute");
		executeButton.setSize(100, 30);
		executeButton.setLocation(350, 50);
		add(executeButton);

		readButton = new JButton("Read next");
		readButton.setSize(100, 30);
		readButton.setLocation(350, 250);
		add(readButton);
		
		resultLabel = new JLabel();
		resultLabel.setSize(100, 100);
		resultLabel.setFont(new Font("Arial", Font.BOLD, 36));
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultLabel.setText("D");
		resultLabel.setLocation(350, 115);
		resultLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(resultLabel);
		
		statisticsTextArea = new JTextArea();
		statisticsTextArea.setSize(180, 280);
		statisticsTextArea.setMargin(new Insets(5, 5, 5, 5));
		statisticsTextArea.setFont(new Font("Arial", Font.PLAIN, 22));
		statisticsTextArea.setForeground(Color.BLACK);
		statisticsTextArea.setLocation(480, 40);
		statisticsTextArea.setEditable(false);
		add(statisticsTextArea);
		
		setVisible(true);
	}
	
	public JButton getExecuteButton()
	{
		return executeButton;
	}
	
	public JButton getReadButton()
	{
		return readButton;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public JLabel getResultLabel()
	{
		return resultLabel;
	}
	
	public JTextArea getStatisticsTextArea()
	{
		return statisticsTextArea;
	}
}
