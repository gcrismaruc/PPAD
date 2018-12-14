package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import sender.OperationRequestMessage;
import sender.OperationResponseMessage;
import sender.OperationState;
import utils.ServiceUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServiceExecutor implements Runnable {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private String requestAsString;

    @Override
    public void run() {
        ObjectMapper objectMapper = new ObjectMapper();

        byte[] buffer = new byte[2024];
        try {
            OperationRequestMessage operationMessage = objectMapper.readValue(requestAsString,
                    OperationRequestMessage.class);

            OperationResponseMessage responseMessage = new OperationResponseMessage();

            String opResponse;

            try {
                opResponse = ServiceUtils.getResult(operationMessage);
                responseMessage.setResponse(opResponse);
                responseMessage.setState(OperationState.SUCCESS);
            } catch (Exception e) {
                responseMessage.setState(OperationState.FAILED);
                responseMessage.setErrorMessage(e.getMessage());
            }

            buffer = objectMapper.writeValueAsBytes(responseMessage);
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, port);

            socket.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public ServiceExecutor setSocket(DatagramSocket socket) {
        this.socket = socket;
        return this;
    }

    public InetAddress getAddress() {
        return address;
    }

    public ServiceExecutor setAddress(InetAddress address) {
        this.address = address;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ServiceExecutor setPort(int port) {
        this.port = port;
        return this;
    }

    public String getRequestAsString() {
        return requestAsString;
    }

    public ServiceExecutor setRequestAsString(String requestAsString) {
        this.requestAsString = requestAsString;
        return this;
    }
}
