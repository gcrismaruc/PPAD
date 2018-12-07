package main;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClusterManager implements Runnable{
    HeartBeatManager manager;
    int port;


    @Override
    public void run() {
        DatagramPacket request, response;
        DatagramSocket socket = null;
        Gson gson = new Gson();

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(true) {
            byte[] buffer = new byte[1024];

            request = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(request);

                InetAddress address = request.getAddress();
                int requestPort = request.getPort();

                System.out.println("Getting cluster data.");

                buffer = gson.toJson(manager.getMonitoredHosts()).getBytes();
                response = new DatagramPacket(buffer, buffer.length, address, requestPort);

                socket.send(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HeartBeatManager getManager() {
        return manager;
    }

    public ClusterManager setManager(HeartBeatManager manager) {
        this.manager = manager;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ClusterManager setPort(int port) {
        this.port = port;
        return this;
    }
}
