package com.jab125.apoint.api;

// sample code:
// INS  str  hello  HELLO WORLD
// ST:OUT  &hello
// INS  int  po  9
// INS  int  ps  24
// ADD  &po  &ps  po
// ST:OUT  &po

import com.jab125.apoint.impl.ClassAccess;

import java.util.ArrayList;
import java.util.HashMap;

// lang 2
// VAL int 5
// VAL int 7
// OUT int *p
// ADD
// FLUSH
// VAL int &p
// ST:OUT
// FLUSH
public class APoint {

    public static APointRuntime createRuntime() {
        var runtime = new com.jab125.apoint.impl.APointRuntime();
        runtime.addCommand("VAL", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("OUT", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("IADD", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("FLUSH", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("ST:OUT", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("CONCAT", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("GOTO", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("AND", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("IF", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("FINISH", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("EQUALS", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("METHOD:STATIC", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("METHOD", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("FIELD:STATIC", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        runtime.addCommand("PUT", new com.jab125.apoint.impl.APointRuntime.BuiltIn());
        return runtime;
    }
}
