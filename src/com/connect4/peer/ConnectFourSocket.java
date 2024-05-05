package com.connect4.peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFourSocket {
    private ServerSocket serverSocket;
    private Socket peerSocket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean isServer;

    /**
     * Default construct
     *
     * @param port Port to connect
     * @throws IOException thrown if fails to establish either a server or socket connection
     */
    public ConnectFourSocket(int port) throws IOException {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            // Port is not available, player is running, connect as peer
            System.out.println("Connecting as player 2");
            this.peerSocket = new Socket("localhost", port);
        }
    }

    public boolean isServer() {
        return this.serverSocket != null;
    }

    public void accept() throws IOException {
        this.peerSocket = this.serverSocket.accept();
    }

    public void setPeerSocket(Socket peerSocket) {
        this.peerSocket = peerSocket;
    }

    public void close() throws IOException {
        input.close();
        output.close();
        if (peerSocket != null) {
            peerSocket.close();
        }
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
