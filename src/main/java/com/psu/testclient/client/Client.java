package com.psu.testclient.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    public Client() {
    }

    public void initConnection(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        System.out.println("Connected to server");

        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void request(String request) {
        try {
            this.outputStream.write(request);
            this.outputStream.newLine();
            this.outputStream.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String requestWithResponse(String request) throws IOException {
        try {
            this.outputStream.write(request);
            this.outputStream.newLine();
            this.outputStream.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return getResponse();
    }

    private String getResponse() throws IOException {
        while (true) {
            if (this.inputStream.ready()) {
                return this.inputStream.readLine();
            }
        }
    }

    public void closeConnection() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }
}
