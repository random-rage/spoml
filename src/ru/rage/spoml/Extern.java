package ru.rage.spoml;

public class Extern
{
    private String _name;
    private int    _start, _end;

    public Extern(String name, int start)
    {
        _name = name;
        _start = start;
        _end = -1;
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
