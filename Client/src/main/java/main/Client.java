package main;

import heartbeat.Host;
import main.actions.GetClusterTopologyActionListener;
import main.actions.GetResultActionListener;
import main.actions.ServiceListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class Client extends JFrame {
    protected JPanel panel;
    protected JTextField nodeIpTextField;
    protected JButton getClusterTopology;
    protected JTextField nodePortTextField;
    protected JTree hostsTree;
    private JTextField inputParameters;
    private JTextArea responseTextArea;
    private JButton getResultFromService;
    private JTextArea inputParametersTextArea;

    private String selectedServiceName;
    private Host selectedHost;

    protected Map<UUID, Host> hosts;

    public Client() {
        hostsTree.setVisible(false);
        add(panel);
        setTitle("Client Application");
        setSize(new Dimension(1203, 766));

        inputParametersTextArea.setEditable(false);
        responseTextArea.setEditable(false);

        getClusterTopology.addActionListener(new GetClusterTopologyActionListener(this));
        hostsTree.addTreeSelectionListener(new ServiceListener(this));
        getResultFromService.addActionListener(new GetResultActionListener(this));
    }

    public void updateResultTextArea(String result) {
        this.responseTextArea.append("\n" + result);
    }

    public void updateSelectedHost(Host host) {
        this.selectedHost = host;
    }

    public void updateInputParametersTextArea(String parameters) {
        inputParametersTextArea.setText(parameters);
    }

    public Map<UUID, Host> getHosts() {
        return hosts;
    }

    public Client setHosts(Map<UUID, Host> hosts) {
        this.hosts = hosts;
        return this;
    }

    public Host getSelectedHost() {
        return selectedHost;
    }

    public Client setSelectedHost(Host selectedHost) {
        this.selectedHost = selectedHost;
        return this;
    }

    public void updateTree(DefaultTreeModel treeModel) {
        hostsTree.setModel(treeModel);
        hostsTree.setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Client setPanel(JPanel panel) {
        this.panel = panel;
        return this;
    }

    public JTextField getNodeIpTextField() {
        return nodeIpTextField;
    }

    public Client setNodeIpTextField(JTextField nodeIpTextField) {
        this.nodeIpTextField = nodeIpTextField;
        return this;
    }

    public JButton getGetClusterTopology() {
        return getClusterTopology;
    }

    public Client setGetClusterTopology(JButton getClusterTopology) {
        this.getClusterTopology = getClusterTopology;
        return this;
    }

    public JTextField getNodePortTextField() {
        return nodePortTextField;
    }

    public Client setNodePortTextField(JTextField nodePortTextField) {
        this.nodePortTextField = nodePortTextField;
        return this;
    }

    public JTree getHostsTree() {
        return hostsTree;
    }

    public Client setHostsTree(JTree hostsTree) {
        this.hostsTree = hostsTree;
        return this;
    }

    public void updateSelectedServiceName(String serviceName) {
        this.selectedServiceName = serviceName;
    }

    public JTextField getInputParameters() {
        return inputParameters;
    }

    public Client setInputParameters(JTextField inputParameters) {
        this.inputParameters = inputParameters;
        return this;
    }

    public JTextArea getResponseTextArea() {
        return responseTextArea;
    }

    public Client setResponseTextArea(JTextArea responseTextArea) {
        this.responseTextArea = responseTextArea;
        return this;
    }

    public JButton getGetResultFromService() {
        return getResultFromService;
    }

    public Client setGetResultFromService(JButton getResultFromService) {
        this.getResultFromService = getResultFromService;
        return this;
    }

    public JTextArea getInputParametersTextArea() {
        return inputParametersTextArea;
    }

    public Client setInputParametersTextArea(JTextArea inputParametersTextArea) {
        this.inputParametersTextArea = inputParametersTextArea;
        return this;
    }

    public String getSelectedServiceName() {
        return selectedServiceName;
    }

    public Client setSelectedServiceName(String selectedServiceName) {
        this.selectedServiceName = selectedServiceName;
        return this;
    }
}
