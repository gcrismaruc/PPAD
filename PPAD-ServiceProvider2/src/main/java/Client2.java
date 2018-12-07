import main.ClusterManager;
import main.HeartBeatManager;
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

public class Client2 {
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
        clusterManager.setPort(4498);

        heartBeatSender.setMessage(createHeartBeatMessage());
        DatagramSocket sendSocket = new DatagramSocket();
        heartBeatSender.setSocket(sendSocket);

        HeartBeatReceiver heartBeatReceiver = new HeartBeatReceiver();

        heartBeatReceiver.setManager(manager);
        heartBeatReceiver.setAddress(address);
        heartBeatReceiver.setPort(port);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        executorService.execute(heartBeatReceiver);
        executorService.execute(heartBeatSender);
        executorService.execute(clusterManager);
        scheduledExecutorService.scheduleWithFixedDelay(serviceCleaner, 10, 10, TimeUnit.SECONDS);

    }

    private static HeartBeatMessage createHeartBeatMessage(){
        Service subService = new Service();
        subService.setServiceName("subTwoIntegers");
        subService.setInputParameters("int a; int b");
        subService.setOutputParameter("int");

        HeartBeatMessage message = new HeartBeatMessage();
        message.setName("Client 2");
        message.setVersion("v1");
        message.setServices(Arrays.asList(subService));
        message.setUuid(UUID.randomUUID());

        return message;
    }


}
