package com.example.maemory.multiplayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadTCP extends Thread{

    public String portNummerField;

    public ThreadTCP(String portNummerField) {
        this.portNummerField = portNummerField;
    }

    @Override
    public void run(){
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(Integer.parseInt(portNummerField));
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                //Send message to client
                PrintWriter out = new PrintWriter(connectionSocket.getOutputStream(), true);
                out.println("Test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
