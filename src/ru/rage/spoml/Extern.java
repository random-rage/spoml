package ru.rage.spoml;

public class Extern
{
    private String name;
    private int    start, end;

    public Extern(String name, int start)
    {
        this.name = name;
        this.start = start;
        this.end = -1;
    }

    public String getName()
    {
        return name;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    public void setEnd(int end)
    {
        this.end = end;
    }
}
