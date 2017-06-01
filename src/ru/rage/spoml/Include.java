package ru.rage.spoml;

public class Include
{
    public static final String FILE_EXT = ".smi";

    private String _lib, _name;
    private int _addr;

    public Include(String lib, String name, int address)
    {
        _lib = lib;
        _name = name;
        _addr = address;
    }

    public String getLib()
    {
        return _lib;
    }

    public String getName()
    {
        return _name;
    }

    public int getAddr()
    {
        return _addr;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %d", _lib, _name, _addr);
    }
}
