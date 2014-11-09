package ru.serjik.hex;

public class HexLocation
{
	public int q, r;
	public int dir;

	public void rotate(int hexAngle)
	{
		dir = (dir + hexAngle + 6) % 6;
	}

	public void move(int deltaForward)
	{
		q += HexUtils.dq[dir] * deltaForward;
		r += HexUtils.dr[dir] * deltaForward;
	}

	public String location()
	{
		return ";" + q + ";" + r + ";" + dir;
	}
}
