package com.dylanscode.math;

public class GameMath
{
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static float norm(float o, float b, float e)
	{
		return (o - b) / (e - b);
	}
}
