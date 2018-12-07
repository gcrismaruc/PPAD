package receiver;

import heartbeat.Host;
import main.HeartBeatManager;
import sender.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


                Instant now = Instant.now();

                String[] response = responseAsString.split(",");
                UUID uuid = UUID.fromString(response[0].split("=")[1]);
                String name = response[1].split("=")[1];
                String version = response[2].split("=")[1];
                String[] services = response[3].split("=")[1].split("\\$");

                List<Service> serviceList = new ArrayList<>();

                for(String service : services) {
                    String[] serviceSplit = service.split("#");

                    String serviceName = serviceSplit[0].replace("{", "");
                    String inputParameters = serviceSplit[1];
                    String outputParameter = serviceSplit[2].replace("}", "");

                    Service service1 = new Service();
                    service1.setOutputParameter(outputParameter);
                    service1.setServiceName(serviceName);
                    service1.setInputParameters(inputParameters);
                    serviceList.add(service1);
                }



                Host host = new Host();
                host.setUpdatedTime(now);
                host.setName(name);
                host.setVersion(version);
                host.setServices(serviceList);
                host.setPort(datagramPacket.getPort());
                host.setAddress(datagramPacket.getAddress());
                manager.addHost(uuid, host);


                manager.listHosts();

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
