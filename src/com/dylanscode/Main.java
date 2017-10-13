package com.dylanscode;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dylanscode.terrain.TerrainGenerator;

public class Main
{
	TerrainGenerator generator;

	public void start()
	{
		generator = new TerrainGenerator(1920, 1080);
		BufferedImage terrain = null;
		try
		{
			terrain = generator.generateImage();
			//saves the image to a png file
			ImageIO.write(terrain, "png", new File("Terrain.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}finally {
			JFrame frame = new JFrame("Terrain");
			frame.setMinimumSize(new Dimension(generator.width, generator.height));
			frame.setMaximumSize(new Dimension(generator.width, generator.height));
			frame.setPreferredSize(new Dimension(generator.width, generator.height));
			frame.setSize(generator.width, generator.height);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.pack();
			Graphics2D g = (Graphics2D) frame.getGraphics();
			g.drawImage(terrain, 0, 0, null);
			
		}
	}

	public static void main(String args[])
	{
		new Main().start();
	}
}
