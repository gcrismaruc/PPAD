package main.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import heartbeat.Host;
import main.Client;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetClusterTopologyActionListener implements ActionListener {
    Client client;

    public GetClusterTopologyActionListener(Client client) {
        this.client = client;
    }

    Pattern pattern;
    Matcher matcher;

    final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                    + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    void IPAddressValidator() {
        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InetAddress nodeAddress;
        int nodePort;

        IPAddressValidator();

        try {

            System.out.println("Getting cluster topology.");
            String ipAddress = client.getNodeIpTextField()
                    .getText().replace(" ","");

            matcher = pattern.matcher(ipAddress);
            if (!matcher.matches()) {
                throw new RuntimeException("This is not a valid ip address.");
            }

            nodeAddress = InetAddress.getByName(ipAddress);
            nodePort = Integer.parseInt(client.getNodePortTextField()
                    .getText());

            DatagramPacket request, response;
            DatagramSocket socket = null;
            Gson gson = new Gson();

            byte[] buffer = new byte[1024];

            response = new DatagramPacket(buffer, buffer.length);

            try {

                socket = new DatagramSocket();
                socket.setSoTimeout(1000);

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
                if (ex instanceof SocketTimeoutException) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "The service you are trying to use may not be available. Please "
                                    + "try another node in the cluster or check again the node "
                                    + "port.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                ex.printStackTrace();
            } finally {
                socket.close();
            }
        } catch (Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "The service you are trying to use may not be available. Please "
                                + "try another node in the cluster or check again the node port.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }
}

