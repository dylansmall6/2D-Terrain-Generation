package com.dylanscode.math;

public class FloatVector
{
	public float x, y, z;

	public FloatVector(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public FloatVector(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.z = Integer.MIN_VALUE;
	}
}
