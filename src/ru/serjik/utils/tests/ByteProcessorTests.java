package ru.serjik.utils.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import ru.serjik.data.ByteProcessor;

public class ByteProcessorTests
{
	private byte[] buffer = new byte[512];
	private ByteProcessor bp = new ByteProcessor();

	@Test
	public void testBytes()
	{
		bp.setup(buffer, 0);

		for (int i = 0; i < 256; i++)
		{
			bp.writeByte((byte) i);
		}

		bp.position(0);

		for (int i = 0; i < 256; i++)
		{
			assertEquals(bp.readByte() & 0xFF, i);
		}
	}

	@Test
	public void testShort()
	{
		bp.setup(buffer, 0);

		bp.writeShort((short) 2839);
		bp.writeShort(Short.MAX_VALUE);
		bp.writeShort(Short.MIN_VALUE);
		bp.writeShort((short) 0);
		bp.writeShort((short) -1);
		bp.writeShort((short) 1);

		bp.position(0);

		assertEquals(2839, bp.readShort());
		assertEquals(Short.MAX_VALUE, bp.readShort());
		assertEquals(Short.MIN_VALUE, bp.readShort());
		assertEquals(0, bp.readShort());
		assertEquals(-1, bp.readShort());
		assertEquals(1, bp.readShort());
	}

	@Test
	public void testInt()
	{
		bp.setup(buffer, 0);

		bp.writeInt((int) 28394856);
		bp.writeInt(Integer.MAX_VALUE);
		bp.writeInt(Integer.MIN_VALUE);
		bp.writeInt((int) 0);
		bp.writeInt((int) -1);
		bp.writeInt((int) 1);

		bp.position(0);

		assertEquals(28394856, bp.readInt());
		assertEquals(Integer.MAX_VALUE, bp.readInt());
		assertEquals(Integer.MIN_VALUE, bp.readInt());
		assertEquals(0, bp.readInt());
		assertEquals(-1, bp.readInt());
		assertEquals(1, bp.readInt());
	}

	@Test
	public void testULeb()
	{
		bp.setup(buffer, 0);

		bp.writeULeb(28394856);
		bp.writeULeb(Integer.MAX_VALUE);
		bp.writeULeb(0);
		bp.writeULeb(1);
		bp.writeULeb(127);
		bp.writeULeb(128);
		bp.writeULeb(32768);
		bp.writeULeb(0xffffff);

		bp.position(0);

		assertEquals(28394856, bp.readULeb());
		assertEquals(Integer.MAX_VALUE, bp.readULeb());
		assertEquals(0, bp.readULeb());
		assertEquals(1, bp.readULeb());
		assertEquals(127, bp.readULeb());
		assertEquals(128, bp.readULeb());
		assertEquals(32768, bp.readULeb());
		assertEquals(0xffffff, bp.readULeb());
	}

	@Test
	public void testString()
	{
		bp.setup(buffer, 0);

		bp.write("test");
		bp.write("qwert asdfg zxcvb");
		bp.write("");
		bp.write("123456789 123456789 123456789 1234567899 123456789 русская кодировка");

		bp.position(0);

		assertEquals("test", bp.readString());
		assertEquals("qwert asdfg zxcvb", bp.readString());
		assertEquals("", bp.readString());
		assertEquals("123456789 123456789 123456789 1234567899 123456789 русская кодировка", bp.readString());
	}

}
