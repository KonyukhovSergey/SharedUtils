package ru.serjik.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5
{
	private static MessageDigest md;

	public static final String get(String text)
	{
		if (md == null)
		{
			try
			{
				md = MessageDigest.getInstance("MD5");
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				return "";
			}
		}
		
		return String.format("%1$032x", new BigInteger(1, md.digest(text.getBytes())));	 
	}
}
