package com.example.maemory.multiplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.regex.Pattern;

public class HostServer extends Thread {
    //FXML DATA
    @FXML
    private TextField serverNameField;
    @FXML
    private TextField portNummerField;
    @FXML
    private PasswordField passwordFieldText;
    //CONNECTION FIND SERVER
    public DatagramSocket socket;
    private boolean running;
    private String name;
    private int port;

    public void run() throws NumberFormatException {
        name = serverNameField.getText();

        port = Integer.parseInt(portNummerField.getText());

        System.out.println(port + " " + name);

        try {
            socket = new DatagramSocket(4000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        running = true;

        while (running) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int clientPort = packet.getPort();
                String received = new String(packet.getData(), packet.getOffset(), packet.getLength()).trim();
                System.out.println(received);

                if (received.equals("?")) {
                    byte[] msgbuf = ("Port: " + port + " Name: " + name).getBytes();
                    DatagramPacket replypacket = new DatagramPacket(msgbuf, msgbuf.length, address, clientPort);
                    socket.send(replypacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onCreateButtonClick() {
        Multiplayer.getHostServerScene().close();
        System.out.println("WARTE JETZT");

        try {
            Integer.parseInt(portNummerField.getText());
            this.start();
            ThreadTCP threadTCP = new ThreadTCP(portNummerField.getText());
            threadTCP.start();
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You can not use letters for your Port!");
                alert.setContentText("Please only use numbers!");
                alert.showAndWait();
            });
            //Create TCP Server
        }
    }
}
