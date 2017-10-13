package com.dylanscode;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dylanscode.terrain.TerrainGenerator;

public class Main
{
	TerrainGenerator generator;

	public void start()
	{
		generator = new TerrainGenerator(1920, 1080);
		try
		{
			//saves the image to a png file
			ImageIO.write(generator.generateImage(), "png", new File("Terrain.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		new Main().start();
	}
}
