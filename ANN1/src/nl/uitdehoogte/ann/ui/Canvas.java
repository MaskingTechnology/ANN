package nl.uitdehoogte.ann.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
	private static final int WIDTH = 28,
			                 HEIGHT = 28,
			                 SCALING_FACTOR = 10,
			                 COMPONENT_WIDTH = WIDTH * SCALING_FACTOR,
			                 COMPONENT_HEIGHT = HEIGHT * SCALING_FACTOR;
	
	private byte[][] pixels;
	private boolean drawing;
	
	public Canvas()
	{
		setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		pixels = new byte[HEIGHT][WIDTH];
		drawing = false;
	}

	public void mouseClicked(MouseEvent e) 
	{ 
		//Not implemented
	}
	
	public void mousePressed(MouseEvent e) 
	{		
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			drawing = true;
		
			setPixel(calculatePoint(e));
		}
		else
		{
			clearDrawing();
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		drawing = false;		
	}

	public void mouseEntered(MouseEvent e) 
	{
		//Not implemented
	}

	public void mouseExited(MouseEvent e) 
	{
		//Not implemented	
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(drawing)
		{
			setPixel(calculatePoint(e));
		}
	}

	public void mouseMoved(MouseEvent e) 
	{
		//Not implemented
	}	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				if(pixels[y][x] != 0)
				{
					g.fillRect(x * SCALING_FACTOR, y * SCALING_FACTOR, SCALING_FACTOR, SCALING_FACTOR);
				}
			}
		}
	}
	
	public byte[][] getPixels()
	{
		return pixels;
	}
	
	public void setPixels(byte[][] pixels)
	{
		this.pixels = pixels;
	}
	
	private Point calculatePoint(MouseEvent e)
	{
		Point point = new Point();
		
		point.x = e.getX() / SCALING_FACTOR;
		point.y = e.getY() / SCALING_FACTOR;
		
		return point;
	}
	
	private void setPixel(Point point)
	{
		if(point.y < HEIGHT && point.x < WIDTH)
		{
			pixels[point.y][point.x] = (byte) 0xFF;
			
			repaint();
		}
	}
	
	private void clearDrawing()
	{
		pixels = new byte[HEIGHT][WIDTH];
		
		repaint();
	}
}
