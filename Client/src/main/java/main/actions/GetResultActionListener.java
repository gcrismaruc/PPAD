package main.actions;

import com.google.gson.Gson;
import main.Client;
import sender.OperationRequestMessage;
import sender.OperationResponseMessage;
import sender.OperationState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

public class GetResultActionListener implements ActionListener {
    Client client;

    public GetResultActionListener(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OperationRequestMessage operationRequestMessage = new OperationRequestMessage();
        List<String> inputParameters = Arrays.asList(client.getInputParameters()
                .getText()
                .split(" "));

        operationRequestMessage.setServiceName(client.getSelectedServiceName());
        operationRequestMessage.setParameters(inputParameters);

        InetAddress serviceClientAddress;
        int serviceClientPort;

        try {
            serviceClientAddress = client.getSelectedHost()
                    .getAddress();
            serviceClientPort = client.getSelectedHost()
                    .getPort();

            DatagramPacket request, response;
            DatagramSocket socket = null;
            Gson gson = new Gson();

            byte[] buffer = gson.toJson(operationRequestMessage)
                    .getBytes();

            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(2000);

                request = new DatagramPacket(buffer, buffer.length, serviceClientAddress,
                        serviceClientPort);
                socket.send(request);

                buffer = new byte[2048];
                response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                String responseAsString = new String(response.getData(), response.getOffset(),
                        response.getLength());

                System.err.println(responseAsString);
                OperationResponseMessage responseMessage = gson.fromJson(responseAsString,
                        OperationResponseMessage.class);

                if (responseMessage.getState() == OperationState.SUCCESS) {

                    client.updateResultTextArea(responseMessage.getResponse());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), responseMessage.getErrorMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                if (ex instanceof SocketTimeoutException) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "The service you are trying to use may not be available. Please "
                                    + "update the services in the services tree.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } finally {
                socket.close();
            }
        } catch (Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "The service you are trying to use may not be available. Please "
                                + "update the services in the services tree.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
