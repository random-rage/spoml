package ru.rage.spoml;

public class Include {
    private String lib, name;
    private int addr;

    public Include(String lib, String name, int address)
    {
        this.lib = lib;
        this.name = name;
        this.addr = address;
    }

    public String getLib() {
        return lib;
    }

    public String getName() {
        return name;
    }

    public int getAddr() {
        return addr;
    }
}
