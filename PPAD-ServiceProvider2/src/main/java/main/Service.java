package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

public class Service implements Runnable {
    private ExecutorService executorService;

    @Override
    public void run() {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(Client2.PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        byte[] buffer = new byte[2024];

        while (true) {
            try {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);


                InetAddress address = request.getAddress();
                int requestPort = request.getPort();

                String requestAsString = new String(request.getData(), request.getOffset(),
                        request.getLength());

                ServiceExecutor serviceExecutor = new ServiceExecutor();
                serviceExecutor.setRequestAsString(requestAsString);
                serviceExecutor.setPort(requestPort);
                serviceExecutor.setAddress(address);
                serviceExecutor.setSocket(socket);


                executorService.execute(serviceExecutor);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Service setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }
}
