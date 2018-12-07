package main.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import heartbeat.Host;
import main.Client;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

public class GetClusterTopologyActionListener implements ActionListener {
    Client client;

    public GetClusterTopologyActionListener(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InetAddress nodeAddress;
        int nodePort;

        try {
            nodeAddress = InetAddress.getByName(client.getNodeIpTextField()
                    .getText());
            nodePort = Integer.parseInt(client.getNodePortTextField()
                    .getText());

            DatagramPacket request, response;
            DatagramSocket socket;
            Gson gson = new Gson();

            byte[] buffer = new byte[1024];

            response = new DatagramPacket(buffer, buffer.length);

            try {

                socket = new DatagramSocket();
                request = new DatagramPacket(buffer, buffer.length, nodeAddress, nodePort);
                socket.send(request);
                socket.receive(response);

                String responseAsString = new String(response.getData(), response.getOffset(),
                        response.getLength());

                java.lang.reflect.Type type = new TypeToken<Map<UUID, Host>>() {
                }.getType();
                Map<UUID, Host> hosts = gson.fromJson(responseAsString, type);

                System.out.println(hosts);

                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Hosts");
                hosts.entrySet()
                        .forEach(uuidHostEntry -> {
                            Host host = uuidHostEntry.getValue();
                            DefaultMutableTreeNode clientNode = new DefaultMutableTreeNode(
                                    host.getName() + " : " + uuidHostEntry.getKey());

                            host.getServices()
                                    .forEach(service -> {
                                        DefaultMutableTreeNode serviceNode = new
                                                DefaultMutableTreeNode(
                                                service.getServiceName());
                                        clientNode.add(serviceNode);
                                    });
                            root.add(clientNode);
                        });

                client.setHosts(hosts);
                client.updateTree(new DefaultTreeModel(root));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

