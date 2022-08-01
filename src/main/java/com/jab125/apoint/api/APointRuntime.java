package com.jab125.apoint.api;

public interface APointRuntime {
    public void addCommand(String name, Provider provider);
    public void parse(String program);
    public static interface Provider {
        void run(String[] params);
    }
}
