package com.dylanscode.perlin;

import com.dylanscode.math.FloatVector;

public class NoiseGenerator
{
	private int $_seed;
	private final static int X_PRIME = 1619;
	private final static int Y_PRIME = 31337;
	private final static int Z_PRIME = 6971;

	public NoiseGenerator(int seed)
	{
		this.$_seed = seed;
	}

	public float noise(float x, float y)
	{
		return Perlin_2D($_seed, x * (float) 0.01, y * (float) 0.01);
	}

	public float noise(float x, float y, float z)
	{
		return Perlin_3D($_seed, x * (float) 0.01, y * (float) 0.01, z * (float) 0.01);
	}
	/*
	 * Perlin Noise methods
	 */

	private static final FloatVector[] GRADIENT2D =
	{ new FloatVector(-1, -1), new FloatVector(1, -1), new FloatVector(-1, 1), new FloatVector(1, 1),
			new FloatVector(0, -1), new FloatVector(-1, 0), new FloatVector(0, 1), new FloatVector(1, 0), };

	private static final FloatVector[] GRADIENT3D =
	{ new FloatVector(1, 1, 0), new FloatVector(-1, 1, 0), new FloatVector(1, -1, 0), new FloatVector(-1, -1, 0),
			new FloatVector(1, 0, 1), new FloatVector(-1, 0, 1), new FloatVector(1, 0, -1), new FloatVector(-1, 0, -1),
			new FloatVector(0, 1, 1), new FloatVector(0, -1, 1), new FloatVector(0, 1, -1), new FloatVector(0, -1, -1),
			new FloatVector(1, 1, 0), new FloatVector(0, -1, 1), new FloatVector(-1, 1, 0),
			new FloatVector(0, -1, -1), };

	private static float Lerp(float a, float b, float t)
	{
		return a + t * (b - a);
	}

	private static float QuinticInterpretation(float t)
	{
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	private static int floor(float f)
	{
		return (f >= 0 ? (int) f : (int) f - 1);
	}

	private static float GradCoord2D(int seed, int x, int y, float xd, float yd)
	{
		int hash = seed;
		hash ^= X_PRIME * x;
		hash ^= Y_PRIME * y;

		hash = hash * hash * hash * 60493;
		hash = (hash >> 13) ^ hash;

		FloatVector gradient = GRADIENT2D[hash & 7];

		return xd * gradient.x + yd * gradient.y;
	}

	private static float GradCoord3D(int seed, int x, int y, int z, float xd, float yd, float zd)
	{
		int hash = seed;
		hash ^= X_PRIME * x;
		hash ^= Y_PRIME * y;
		hash ^= Z_PRIME * z;

		hash = hash * hash * hash * 60493;
		hash = (hash >> 13) ^ hash;

		FloatVector gradient = GRADIENT3D[hash & 15];

		return xd * gradient.x + yd * gradient.y + zd * gradient.z;
	}

	private float Perlin_3D(int seed, float x, float y, float z)
	{
		int x0 = floor(x);
		int y0 = floor(y);
		int z0 = floor(z);
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		int z1 = z0 + 1;

		float xs = QuinticInterpretation(x - x0);
		float ys = QuinticInterpretation(y - y0);
		float zs = QuinticInterpretation(z - z0);

		float xd0 = x - x0;
		float yd0 = y - y0;
		float zd0 = z - z0;

		float xd1 = xd0 - 1;
		float yd1 = yd0 - 1;
		float zd1 = zd0 - 1;

		float xf00 = Lerp(GradCoord3D(seed, x0, y0, z0, xd0, yd0, zd0), GradCoord3D(seed, x1, y0, z0, xd1, yd0, zd0),
				xs);
		float xf10 = Lerp(GradCoord3D(seed, x0, y1, z0, xd0, yd1, zd0), GradCoord3D(seed, x1, y1, z0, xd1, yd1, zd0),
				xs);
		float xf01 = Lerp(GradCoord3D(seed, x0, y0, z1, xd0, yd0, zd1), GradCoord3D(seed, x1, y0, z1, xd1, yd0, zd1),
				xs);
		float xf11 = Lerp(GradCoord3D(seed, x0, y1, z1, xd0, yd1, zd1), GradCoord3D(seed, x1, y1, z1, xd1, yd1, zd1),
				xs);

		float yf0 = Lerp(xf00, xf10, ys);
		float yf1 = Lerp(xf01, xf11, ys);

		return Lerp(yf0, yf1, zs);
	}

	private float Perlin_2D(int seed, float x, float y)
	{
		int x0 = floor(x);
		int y0 = floor(y);
		int x1 = x0 + 1;
		int y1 = y0 + 1;

		float xs = QuinticInterpretation(x - x0);
		float ys = QuinticInterpretation(y - y0);

		float xd0 = x - x0;
		float yd0 = y - y0;
		float xd1 = xd0 - 1;
		float yd1 = yd0 - 1;

		float xf0 = Lerp(GradCoord2D(seed, x0, y0, xd0, yd0), GradCoord2D(seed, x1, y0, xd1, yd0), xs);
		float xf1 = Lerp(GradCoord2D(seed, x0, y1, xd0, yd1), GradCoord2D(seed, x1, y1, xd1, yd1), xs);

		return Lerp(xf0, xf1, ys);
	}

}
