package com.example.maemory.multiplayer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ResourceBundle;

public class JoinServer implements Initializable {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    public TableView tableView;
    ObservableList<SrvInfo> srvInfoList = FXCollections.observableArrayList();

    @FXML
    public void findServers() {
        System.out.println("Test");
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            String msg = "?";
            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4000);

            socket.send(packet);

            System.out.println("Sent");
            socket.setSoTimeout(2000);
            byte[] buffer = new byte[256];
            DatagramPacket rcvpacket = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(rcvpacket);

                String received = new String(rcvpacket.getData(), rcvpacket.getOffset(), rcvpacket.getLength()).trim();
                System.out.println(received);
                //Split ""Port: " + port + " Name: " + name" into port and name
                String[] split = received.split(" ");
                int port = Integer.parseInt(split[1]);
                String name = split[3];
                //If element already exists in list, block it
                for (SrvInfo srvInfo : srvInfoList) {
                    if (srvInfo.getName().equals(name)) {
                        return;
                    }
                }
                //Put port and name into table
                srvInfoList.add(new SrvInfo(port, name));
                //socket.close();
            } catch (SocketTimeoutException e) {
                Multiplayer.getJoinServerScene().close();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Keine Server gefunden!");
                    alert.setContentText("Erstelle einfach einen Server");
                    alert.showAndWait();
                });
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(srvInfoList);

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    startTCPConnection(tableView.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startTCPConnection(Object selectedItem) throws IOException {
        int port = ((SrvInfo) selectedItem).getPort();
        Socket clientSocket = new Socket("localhost", port);
        System.out.println("Connected to server");
        //recieve message from server
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = in.readLine();
        System.out.println(message);
    }
}