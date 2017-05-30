package ru.rage.spoml;

import java.util.ArrayList;

public class Command
{
    private CmdType _type;
    private Argument _arg;
    private String _label;

    private static final ArrayList<String> _strs = new ArrayList<String>()
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

    public Command(int opcode, int arg)
    {
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

    /**
     * Супербыдлокод
     * @param type Тип команды
     * @return Код операции
     */
    private static int getCode(CmdType type)
    {
        switch (type) {
            case ADD:
                return 1;
            case SUB:
                return 2;
            case INC:
                return 3;
            case DEC:
                return 4;
            case LDR:
                return 5;
            case STR:
                return 6;
            case IN:
                return 7;
            case OUT:
                return 8;
            case JMP:
                return 9;
            case IFZ:
                return 10;
            case IFN:
                return 11;
            case HLT:
                return 12;
            default:
                return 0;
        }
    }

    public int getOpcode()
    {
        int opcode = getCode(_type);

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
        return _type.toString();
    }
}