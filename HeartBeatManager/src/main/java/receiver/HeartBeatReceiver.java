package receiver;

import main.HeartBeatManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class HeartBeatReceiver implements Runnable{

    private HeartBeatManager manager;
    private int port;
    private InetAddress address;

    public void run() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            byte[] buffer;
            try {
                buffer = new byte[256];

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                String responseAsString = new String(datagramPacket.getData(),
                        datagramPacket.getOffset(), datagramPacket.getLength());

                System.out.println(responseAsString);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HeartBeatManager getManager() {
        return manager;
    }

    public HeartBeatReceiver setManager(HeartBeatManager manager) {
        this.manager = manager;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HeartBeatReceiver setPort(int port) {
        this.port = port;
        return this;
    }

    public InetAddress getAddress() {
        return address;
    }

    public HeartBeatReceiver setAddress(InetAddress address) {
        this.address = address;
        return this;
    }
}
