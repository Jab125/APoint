package com.jab125.apoint.api;

import java.util.List;

public interface APointRuntime {
    public void addCommand(String name, Provider provider);
    public void removeCommand(String name);
    public void parse(String program);
    public static interface Provider {
        void run(String[] params);
    }
    public Object getPointer(String p);
    public void setPointer(String p, Object val);
    public List<Object> getVals();

    public String getOut();
}
