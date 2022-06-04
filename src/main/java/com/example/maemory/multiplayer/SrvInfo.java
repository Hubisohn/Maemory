package com.example.maemory.multiplayer;

public class SrvInfo {
    int port;
    String name;

    public SrvInfo(int port, String name) {
        this.port = port;
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
