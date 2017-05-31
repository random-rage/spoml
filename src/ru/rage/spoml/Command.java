package ru.rage.spoml;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Command
{
    private CmdType  _type;
    private Argument _arg;
    private String   _label;

    private static final ArrayList<String>  _strs = new ArrayList<String>()
    {{
        add("NOP");
        add("ADD");
        add("SUB");
        add("INC");
        add("DEC");
        add("LDR");
        add("STR");
        add("IN");
        add("OUT");
        add("JMP");
        add("IFZ");
        add("IFN");
        add("HLT");
    }};
    private static final ArrayList<CmdType> _cmds = new ArrayList<CmdType>()
    {{
        add(CmdType.NOP);
        add(CmdType.ADD);
        add(CmdType.SUB);
        add(CmdType.INC);
        add(CmdType.DEC);
        add(CmdType.LDR);
        add(CmdType.STR);
        add(CmdType.IN);
        add(CmdType.OUT);
        add(CmdType.JMP);
        add(CmdType.IFZ);
        add(CmdType.IFN);
        add(CmdType.HLT);
    }};

    /**
     * Создаёт команду из массива байтов
     *
     * @param bytes массив из 5-ти байтов
     */
    public Command(byte[] bytes)
    {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        int opcode = bb.get();
        int arg = bb.getInt();

        ArgType argType = getArgType(opcode);
        _type = _cmds.get(opcode);
        _arg = new Argument(arg, argType);
    }

    public Command(String type, String arg)
    {
        this(type, arg, null);
    }

    public Command(String type, String arg, String label)
    {
        _type = _cmds.get(_strs.indexOf(type));
        _arg = new Argument(arg);
        _label = label;
    }

    public static ArgType getArgType(int opcode)
    {
        if ((opcode & 0x10) > 0)
            return ArgType.IMMEDIATE;

        CmdType cmd = _cmds.get(opcode);
        switch (cmd)
        {
            case ADD:
            case SUB:
            case LDR:
            case STR:
            case JMP:
                return ArgType.INDIRECT;
            default:
                return ArgType.NONE;
        }
    }

    public int getOpcode()
    {
        int opcode = _cmds.indexOf(_type);

        if (_arg.getType() == ArgType.IMMEDIATE)
            opcode |= 0x10;

        return opcode;
    }

    public Argument getArg()
    {
        return _arg;
    }

    public void setArg(Argument arg)
    {
        _arg = arg;
    }

    public boolean hasLabel()
    {
        return _label != null;
    }

    public String getLabel()
    {
        return _label;
    }

    public CmdType getType()
    {
        return _type;
    }

    @Override
    public String toString()
    {
        switch (_arg.getType())
        {
            case IMMEDIATE:
                return _type.toString() + "\t" + _arg.toString();
            case INDIRECT:
                return _type.toString() + "\t[" + _arg.toString() + "]";
            case CHAR:
                return _type.toString() + "\t'" + ((char)_arg.getValue()) + "'";
            default:
                return _type.toString();
        }
    }

    public byte[] toByteArray()
    {
        if (_arg.getType() == ArgType.NONE)
            return new byte[] { (byte)getOpcode() };
        else
        {
            ByteBuffer bb = ByteBuffer.allocate(Byte.BYTES + Integer.BYTES);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            bb.put((byte)getOpcode());
            bb.putInt(_arg.getValue());

            return bb.array();
        }
    }
}