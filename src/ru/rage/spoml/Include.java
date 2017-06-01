package ru.rage.spoml;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Include
{
    public static final String  FILE_EXT     = ".smi";
    public static final Charset FILE_CHARSET = StandardCharsets.UTF_8;

    private String _lib, _name;
    private int _addr;

    public Include(String include)
    {
        String[] inc = include.split(" ");
        _lib = inc[0];
        _name = inc[1];
        _addr = Integer.parseInt(inc[2]);
    }

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

    public List<Byte> extractCode(String libPath) throws Exception
    {
        byte[] file;
        Path p = Paths.get(libPath, _lib + Extern.LIBRARY_EXT);
        if (Files.exists(p))
            file = Files.readAllBytes(p);
        else
        {
            p = Paths.get(libPath, _lib + Command.EXECUTABLE_EXT);
            if (Files.exists(p))
                file = Files.readAllBytes(p);
            else
                throw new Exception(String.format("Library \"%s\" not found", _lib));
        }
        ByteBuffer bb = ByteBuffer.wrap(file, 0, Integer.BYTES * 4);
        bb.order(Command.BYTE_ORDER);

        int codeOffset = Integer.BYTES * 4 + bb.getInt() + bb.getInt(); // Смещение кода
        int externsOffset = codeOffset + bb.getInt();                   // Смещение внешних символов
        int externsSize = bb.getInt();

        String[] externs = new String(file, externsOffset, externsSize, FILE_CHARSET).split("[\\r\\n]+");
        for (String extern : externs)
        {
            if (extern.length() < 5)
                continue;

            String[] e = extern.split(" ");
            if (e[0].equals(_name))
            {
                int start = Integer.parseInt(e[1]) + codeOffset;
                int end = Integer.parseInt(e[2]) + codeOffset;
                ArrayList<Byte> result = new ArrayList<>(end - start);

                for (int i = start; i < end; i++)
                    result.add(file[i]);
                return result;
            }
        }
        throw new Exception(String.format("Extern \"%s\" not found in \"%s\"", _name, _lib));
    }
}
