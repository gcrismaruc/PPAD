package main.actions;

import main.Client;
import sender.Service;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServiceListener implements TreeSelectionListener {

    Client client;

    public ServiceListener(Client client) {
        this.client = client;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath treePath = client.getHostsTree()
                .getSelectionPath();
        if (treePath.getPathCount() > 2) {

            UUID uuid = UUID.fromString(treePath.getPathComponent(1)
                    .toString()
                    .split(":")[1].replace(" ", ""));
            String serviceName = treePath.getLastPathComponent()
                    .toString();

            List<Service> services = client.getHosts()
                    .get(uuid)
                    .getServices();

            Service service = services.stream()
                    .filter(service1 -> service1.getServiceName()
                            .equals(serviceName))
                    .collect(Collectors.toList())
                    .get(0);

            client.updateInputParametersTextArea(service.getInputParameters());
        }
    }
}
