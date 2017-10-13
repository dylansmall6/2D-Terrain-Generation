package com.dylanscode.terrain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.dylanscode.math.GameMath;
import com.dylanscode.perlin.NoiseGenerator;

public class TerrainGenerator
{
	public int width;
	public int height;
	private Board board;

	public TerrainGenerator(int width, int height)
	{
		this.width = width;
		this.height = height;
		board = new Board(width, height);
	}

	public BufferedImage generateImage()
	{
		BufferedImage terrain = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) terrain.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		board.draw(g);
		return terrain;
	}

	private class Cell
	{
		protected int x, y, size;
		protected Color color;
		protected Cell(int x, int y, int size,float noise)
		{
			this.x = x;
			this.y = y;
			this.size = size;
			color = generateColor(noise);
		}
		/**
		 * noise is between 0 and 1, so we will assign colors to different noise values
		 * @param noise
		 * @return color of noise value
		 */
		private Color generateColor(float noise) {
			if(noise <= .55) {
				//deep blue
				return new Color(1,40,104);
			}else if(noise > .55 && noise <= .62) {
				//light blue
				return new Color(3,65,165);
			}else if(noise > .62 && noise <= .65) {
				//sandy color
				return new Color(242, 240, 152);
			}else if(noise >.65 && noise <=.7) {
				//light green
				return new Color(48, 168, 42);
			}else if(noise > .7 && noise <= .8) {
				//middle green
				return new Color(4, 135, 0);
			}else if(noise > .8 && noise <= .85) {
				//dark green
				return new Color(9,89,5);
			}
			else if(noise > .85 && noise <= .89) {
				//dark gray
				return new Color(178, 178, 178);
			}else if(noise > .89 && noise <= .92) {
				//light gray
				return new Color(91, 91, 89);
			}
			else if(noise > .92 && noise <= .95) {
				//very light gray
				return new Color(224, 219, 219);
			}else {
				//white
				return new Color(255,255,255);
			}
		}
		protected void draw(Graphics2D g) {
			g.setColor(color);
			Rectangle rect = new Rectangle(x,y,x+size,y+size);
			g.fill(rect);
			g.draw(rect);
		}
	}

	private class Board
	{
		protected int scale = 1, rows, cols, increment = 2;
		private NoiseGenerator generator;
		private Cell[][] cells;
		protected Board(int width, int height)
		{
			this.rows = height / scale;
			this.cols = width / scale;
			generator = new NoiseGenerator((int) (System.currentTimeMillis() & Integer.MAX_VALUE));
			cells = generateCells();
		}

		protected Cell[][] generateCells()
		{
			Cell[][] cells = new Cell[cols][rows];
			int xOffset = 0;
			for (int x = 0; x < cells.length; x++)
			{
				int yOffset = 0;
				for (int y = 0; y < cells[x].length; y++)
				{
					cells[x][y] = new Cell(x * scale, y * scale, scale,GameMath.norm(generator.noise(xOffset, yOffset), -1, 1));
					yOffset += increment;
				}
				yOffset = 0;
				xOffset += increment;
			}
			return cells;
		}
		public void draw(Graphics2D g) {
			for (int x = 0; x < cells.length; x++)
			{
				for (int y = 0; y < cells[x].length; y++)
				{
					cells[x][y].draw(g);
				}
			}
		}
	}
}
