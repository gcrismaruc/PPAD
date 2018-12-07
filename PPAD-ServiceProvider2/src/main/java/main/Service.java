package main;

import com.google.gson.Gson;
import sender.OperationRequestMessage;
import sender.OperationResponseMessage;
import sender.OperationState;
import utils.ServiceUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Service implements Runnable {

    @Override
    public void run() {
        DatagramPacket request, response;
        DatagramSocket socket = null;
        Gson gson = new Gson();

        try {
            socket = new DatagramSocket(Client2.PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true) {
            byte[] buffer = new byte[2024];

            request = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(request);

                InetAddress address = request.getAddress();
                int requestPort = request.getPort();

                String requestAsString = new String(request.getData(), request.getOffset(),
                        request.getLength());

                OperationRequestMessage operationMessage = gson.fromJson(requestAsString,
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

                buffer = gson.toJson(responseMessage)
                        .getBytes();
                response = new DatagramPacket(buffer, buffer.length, address, requestPort);

                socket.send(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
