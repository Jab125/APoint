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
        return new com.jab125.apoint.impl.APointRuntime();
    }
}
