package ru.rage.spoml;

import java.nio.ByteBuffer;

public class Data
{
    public static final String FILE_EXT = ".smd";

    private String _name;
    private int    _val;
    private int    _len;

    public Data(String name, String val)
    {
        _name = name;
        _val = (val.matches("'.'")) ? val.codePointAt(1) : Integer.parseInt(val);
        _len = 1;
    }

    public Data(String name, String val, String len)
    {
        this(name, val);
        _len = Integer.parseInt(len);
    }

    public String getName()
    {
        return _name;
    }

    public int getValue()
    {
        return _val;
    }

    public int getLength()
    {
        return _len;
    }

    public byte[] toByteArray()
    {
        ByteBuffer bb = ByteBuffer.allocate(_len * Integer.BYTES);
        bb.order(Command.BYTE_ORDER);

        for (int i = 0; i < _len; i++)
            bb.putInt(_val);

        return bb.array();
    }
}
