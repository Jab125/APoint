package com.jab125.apoint.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APointRuntime implements com.jab125.apoint.api.APointRuntime {
    public final HashMap<String, Object> pointerTable = new HashMap<>();
    public final ArrayList<Object> varTable = new ArrayList<>();
    private final HashMap<String, com.jab125.apoint.api.APointRuntime.Provider> providers = new HashMap<>();
    public String out = null;

    public void parse(String program) {
        var lines = program.lines().toList();
        loop:
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            var split = split(line, ' ');
            //System.out.println(i+1 + ", " + line);
            switch (split[0]) {
                case "VAL" -> val(split);
                case "OUT" -> out(split);
                case "IADD" -> iadd(split);
                case "FLUSH" -> flush(split);
                case "ST:OUT" -> stOut(split);
                case "CONCAT" -> concat(split);
                case "GOTO" -> {
                    if (split.length >= 2) i = (Integer) getValue("int", split[1])-2;
                    else i = ((int) (Integer) this.varTable.get(0))-2;flush(split);
                }
                case "AND" -> and(split);
                case "IF" -> {
                    if (!_if(split)) {
                        i++;
                    } else {
                        this.varTable.remove(this.varTable.size()-1);
                    }
                }
                case "FINISH" -> {break loop;}
                case "EQUALS" -> _equals();
                case "METHOD:STATIC" -> ClassAccess.methodStatic(this, split);
                case "METHOD" -> ClassAccess.method(this, split);
                case "FIELD:STATIC" -> ClassAccess.fieldStatic(this, split);
                case "PUT" -> put(split);
                default -> providers.get(split[0]).run(split);
            }
        }
//        System.out.println(pointerTable);
//        System.out.println(varTable);
//        System.out.println(out);
    }

    @Override
    public Object getPointer(String p) {
        return this.pointerTable.get(p);
    }

    @Override
    public void setPointer(String p, Object val) {
        this.pointerTable.put(p, val);
    }

    @Override
    public List<Object> getVals() {
        return this.varTable;
    }

    @Override
    public String getOut() {
        return this.out;
    }

    private void put(String[] split) {
        this.pointerTable.put(this.out, this.varTable.get(0));
    }

    void _equals() {
        var bool = true;
        Object prevVal = null;
        for (Object o : this.varTable) {
            if (prevVal != null) {
                bool = bool && o.equals(prevVal);
            }
            prevVal = o;
        }
        this.pointerTable.put(out, bool);
    }

    boolean _if(String[] split) {
        var bool = true;
        for (Object o : this.varTable) {
            if (o instanceof Boolean b) {
                bool = bool && b;
            }
        }
        return bool;
    }

    void and(String[] split) {
        boolean bool = true;
        for (Object o : this.varTable) {
            if (o instanceof Boolean b) {
                bool = bool && b;
            }
        }
        this.pointerTable.put(out, bool);
    }

    void concat(String[] split) {
        StringBuilder s = new StringBuilder();
        for (Object o : this.varTable) {
            s.append(o);
        }
        this.pointerTable.put(out, s.toString());
    }

    void stOut(String[] split) {
        varTable.forEach(System.out::println);
    }

    void flush(String[] split) {
        this.varTable.clear();
        this.out = null;
    }

    void iadd(String[] split) {
        int i = 0;
        for (Object o : this.varTable) {
            if (o instanceof Integer integer) {
                i += integer;
            }
        }
        this.pointerTable.put(out, i);
    }

    void out(String[] split) {
        var type = split[1];
        var pointer = split[2];
        if (!pointer.startsWith("*")) return;
        pointer = pointer.substring(1);
        this.out = pointer;
    }

    void val(String[] split) {
        var type = split[1];
        var val = getValue(type, split[2]);
        this.varTable.add(val);
    }

    Object getValue(String type, String s) {
        if (s.startsWith("&")) {
            return getPointingValue(type, s.substring(1));
        } else {
            return getLiteralValue(type, s);
        }
    }

    Object getLiteralValue(String type, String s) {
        return switch (type) {
            case "int" -> Integer.parseInt(s);
            case "str" -> s;
            case "bool" -> Boolean.parseBoolean(s);
            default -> throw new IllegalStateException();
        };
    }

    Object getPointingValue(String type, String s) {
        return this.pointerTable.get(s);
    }

    public static String[] split(String string, char on) {
        boolean ignore = false;
        boolean literal = false;
        boolean potentialComment = false;
        StringBuilder currentStr = new StringBuilder();
        ArrayList<String> strings = new ArrayList<>();
        for (char c : string.toCharArray()) {
            if (c == '\\' && !literal) {literal = true;continue;}
            if (c == '"' && !literal) {ignore = !ignore;continue;}
            if (c == '/' && !ignore && !literal) {
                if (potentialComment) {currentStr.deleteCharAt(currentStr.length()-1);break;}
                potentialComment = true;
            } else potentialComment = false;
            if (c != on || ignore || literal) {
                currentStr.append(c);
            } else {
                strings.add(currentStr.toString());
                currentStr = new StringBuilder();
            }
            literal = false;
        }
        strings.add(currentStr.toString());
        return strings.toArray(new String[0]);
    }

    @Override
    public void addCommand(String name, Provider provider) {
        this.providers.put(name, provider);
    }
}
