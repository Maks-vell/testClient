package com.psu.testclient.client;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class);
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    public Client() {
    }

    public void initConnection(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        log.info("Connected to server");

        this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void request(String request) {
        try {
            this.outputStream.write(request);
            this.outputStream.newLine();
            this.outputStream.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public String requestWithResponse(String request) throws IOException {
        try {
            this.outputStream.write(request);
            this.outputStream.newLine();
            this.outputStream.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage());
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
