package ru.rage.spoml;

public class Extern
{
    public static final String FILE_EXT = ".sme";
    public static final String LIBRARY_EXT = ".sml";

    private String _name;
    private int    _start, _end;

    public Extern(String extern)
    {
        String[] e = extern.split(" ");
        _name = e[0];
        _start = Integer.parseInt(e[1]);
        _end = Integer.parseInt(e[2]);
    }

    public Extern(String name, int start)
    {
        this(name, start, -1);
    }

    public Extern(String name, int start, int end)
    {
        _name = name;
        _start = start;
        _end = end;
    }

    public String getName()
    {
        return _name;
    }

    public int getStart()
    {
        return _start;
    }

    public int getEnd()
    {
        return _end;
    }

    public void setEnd(int end)
    {
        _end = end;
    }

    @Override
    public String toString()
    {
        return String.format("%s %d %d", _name, _start, _end);
    }
}
