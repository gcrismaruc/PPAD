import main.HeartBeatManager;
import receiver.HeartBeatReceiver;
import sender.HeartBeatMessage;
import sender.HeartBeatSender;
import sender.Service;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client2 {
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName("230.0.0.1");
        int port = 4444;

        HeartBeatManager manager = new HeartBeatManager();
        HeartBeatSender heartBeatSender = new HeartBeatSender();
        heartBeatSender.setAddress(address);
        heartBeatSender.setPort(port);


        Service subService = new Service();
        subService.setServiceName("subTwoIntegers");
        subService.setInputParameters("int a; int b");
        subService.setOutputParameter("int");

        HeartBeatMessage message = new HeartBeatMessage();
        message.setName("Client 2");
        message.setVersion("v1");
        message.setServices(subService.toString());
        message.setUuid(UUID.randomUUID());

        heartBeatSender.setMessage(message);
        DatagramSocket sendSocket = new DatagramSocket();
        heartBeatSender.setSocket(sendSocket);

        HeartBeatReceiver heartBeatReceiver = new HeartBeatReceiver();

        heartBeatReceiver.setManager(manager);
        heartBeatReceiver.setAddress(address);
        heartBeatReceiver.setPort(port);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(heartBeatReceiver);
        executorService.execute(heartBeatSender);
    }
}
