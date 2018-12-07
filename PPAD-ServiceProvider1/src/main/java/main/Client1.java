package main;

import monitoring.ServiceCleaner;
import receiver.HeartBeatReceiver;
import sender.HeartBeatMessage;
import sender.HeartBeatSender;
import sender.Service;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client1 {
    public static final int PORT = 5566;

    public static void main(String[] args) throws IOException {
        startClustering();
    }

    public static void startClustering() throws SocketException, UnknownHostException {
        InetAddress address = InetAddress.getByName("230.0.0.1");
        int port = 4444;

        HeartBeatManager manager = new HeartBeatManager();
        HeartBeatSender heartBeatSender = new HeartBeatSender();
        heartBeatSender.setAddress(address);
        heartBeatSender.setPort(port);

        ServiceCleaner serviceCleaner = new ServiceCleaner();
        serviceCleaner.setManager(manager);

        ClusterManager clusterManager = new ClusterManager();
        clusterManager.setManager(manager);
        clusterManager.setPort(4499);

        heartBeatSender.setMessage(createHeartBeatMessage());
        DatagramSocket sendSocket = new DatagramSocket();
        heartBeatSender.setSocket(sendSocket);

        HeartBeatReceiver heartBeatReceiver = new HeartBeatReceiver();

        heartBeatReceiver.setManager(manager);
        heartBeatReceiver.setAddress(address);
        heartBeatReceiver.setPort(port);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        main.Service service = new main.Service();

        executorService.execute(heartBeatReceiver);
        executorService.execute(heartBeatSender);
        executorService.execute(clusterManager);
        executorService.execute(service);
        scheduledExecutorService.scheduleWithFixedDelay(serviceCleaner, 10, 3, TimeUnit.SECONDS);
    }

    private static HeartBeatMessage createHeartBeatMessage() {
        Service addService = new Service();
        addService.setServiceName("addTwoIntegers");
        addService.setInputParameters("int a; int b");
        addService.setOutputParameter("int");

        Service multiplyService = new Service();
        multiplyService.setServiceName("multiplyTwoIntegers");
        multiplyService.setInputParameters("int a; int b");
        multiplyService.setOutputParameter("int");

        HeartBeatMessage message = new HeartBeatMessage();
        message.setName("Client 1");
        message.setVersion("v1");
        message.setPort(PORT);
        message.setUuid(UUID.randomUUID());
        message.setServices(Arrays.asList(multiplyService, addService));

        return message;
    }
}
