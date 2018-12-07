package sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HeartBeatSender implements Runnable {

    private InetAddress address;
    private  int port;
    String message;
    DatagramSocket socket;


    public void run() {

        while(true) {
            DatagramPacket datagramPacket;
            byte[] buffer = message.getBytes();
            try {
                datagramPacket = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(datagramPacket);
                System.out.println("Heart beat sent!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public HeartBeatSender setAddress(InetAddress address) {
        this.address = address;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HeartBeatSender setPort(int port) {
        this.port = port;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HeartBeatSender setMessage(String message) {
        this.message = message;
        return this;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public HeartBeatSender setSocket(DatagramSocket socket) {
        this.socket = socket;
        return this;
    }


}
