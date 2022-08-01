package com.jab125.apoint.impl;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;

public class ClassAccess {
    public static void fieldStatic(APointRuntime aPoint, String[] split) {
        var clazz = (String) aPoint.varTable.get(0);
        var method = (String) aPoint.varTable.get(1);
        var type = (String) aPoint.varTable.get(2);
        var out = aPoint.out;
        try {
            var clazzO = Class.forName(clazz);
            var method0 = MethodHandles.publicLookup().findStaticGetter(clazzO, method, parseType(type));
            var result = method0.invoke(new Object[0]);
            if (out != null) {
                aPoint.pointerTable.put(out, result);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void methodStatic(APointRuntime aPoint, String[] split) {
        var clazz = (String) aPoint.varTable.get(0);
        var method = (String) aPoint.varTable.get(1);
        var returnType = (String) aPoint.varTable.get(2);
        var a = new ArrayList<Class<?>>();
        var c = new ArrayList<Object>();
        boolean fp = false;
        for (int i = 3; i < aPoint.varTable.size(); i++) {
            var t = aPoint.varTable.get(i);
            if (!fp) {
                a.add(parseType((String)t));
            } else {
                c.add(t);
            }
            fp = !fp;
        }
        var out = aPoint.out;
        try {
            var clazzO = Class.forName(clazz);
            var method0 = MethodHandles.publicLookup().findStatic(clazzO, method, MethodType.methodType(parseType(returnType), a));
            var result = method0.invokeWithArguments(c.toArray());
            if (out != null) {
                aPoint.pointerTable.put(out, result);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void method(APointRuntime aPoint, String[] split) {
        var object = aPoint.varTable.get(0);
        var clazz = (String) aPoint.varTable.get(1);
        var method = (String) aPoint.varTable.get(2);
        var returnType = (String) aPoint.varTable.get(3);
        var a = new ArrayList<Class<?>>();
        var c = new ArrayList<Object>();
        boolean fp = false;
        for (int i = 4; i < aPoint.varTable.size(); i++) {
            var t = aPoint.varTable.get(i);
            if (!fp) {
                a.add(parseType((String)t));
            } else {
                c.add(t);
            }
            fp = !fp;
        }
        var out = aPoint.out;
        try {
            var clazzO = Class.forName(clazz);
            var method0 = MethodHandles.publicLookup().findVirtual(clazzO, method, MethodType.methodType(parseType(returnType), a));
            method0.bindTo(object);
            c.add(0, object);
            var result = method0.invokeWithArguments(c.toArray());
            if (out != null) {
                aPoint.pointerTable.put(out, result);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void scream(String c) {
        System.out.println("AAAAAAA " + c);
    }

    // https://stackoverflow.com/questions/5032898/how-to-instantiate-class-class-for-a-primitive-type
    public static Class<?> parseType(final String className) {
        switch (className) {
            case "boolean":
                return boolean.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            case "void":
                return void.class;
            default:
                String fqn = className.contains(".") ? className : "java.lang.".concat(className);
                try {
                    return Class.forName(fqn);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + fqn);
                }
        }
    }
}
