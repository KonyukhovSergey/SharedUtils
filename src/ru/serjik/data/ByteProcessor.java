package ru.serjik.data;

public class ByteProcessor
{
	private byte[] data;
	private int position;

	public void setup(byte[] data, int position)
	{
		this.data = data;
		this.position = position;
	}

	public void position(int position)
	{
		this.position = position;
	}

	public int position()
	{
		return position;
	}

	public void writeByte(byte value)
	{
		data[position++] = value;
	}

	public void writeShort(short value)
	{
		data[position++] = (byte) (value & 0xff);
		value >>= 8;
		data[position++] = (byte) (value & 0xff);
	}

	public void writeInt(int value)
	{
		data[position++] = (byte) (value & 0xff);
		value >>= 8;
		data[position++] = (byte) (value & 0xff);
		value >>= 8;
		data[position++] = (byte) (value & 0xff);
		value >>= 8;
		data[position++] = (byte) (value & 0xff);
	}

	public void writeULeb(int value)
	{
		int remaining = value >>> 7;

		while (remaining != 0)
		{
			data[position++] = (byte) ((value & 0x7f) | 0x80);
			value = remaining;
			remaining >>>= 7;
		}

		data[position++] = (byte) (value & 0x7f);
	}

	public void write(String value)
	{
		byte[] bytes = value.getBytes();
		writeULeb(bytes.length);
		System.arraycopy(bytes, 0, data, position, bytes.length);
		position += bytes.length;
	}

	public byte readByte()
	{
		return data[position++];
	}

	public short readShort()
	{
		short value = (short) ((data[position + 1] & 0xFF) << 8 | (data[position + 0] & 0xFF));
		position += 2;
		return value;
	}

	public int readInt()
	{
		int value = (data[position + 3] & 0xFF) << 24 | (data[position + 2] & 0xFF) << 16
				| (data[position + 1] & 0xFF) << 8 | (data[position + 0] & 0xFF);
		position += 4;
		return value;
	}

	public int readULeb()
	{
		int currentByte;
		int count = 0;

		int result = 0;

		do
		{
			currentByte = data[position++] & 0xff;
			result |= (currentByte & 0x7f) << (count * 7);
			count++;
		}
		while (((currentByte & 0x80) == 0x80) && count < 5);

		if ((currentByte & 0x80) == 0x80)
		{
			return -1;
		}

		return result;
	}

	public String readString()
	{
		int length = readULeb();
		String result = new String(data, position, length);
		position += length;
		return result;
	}
}
