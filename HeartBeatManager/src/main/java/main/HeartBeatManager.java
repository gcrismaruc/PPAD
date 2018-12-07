package main;

import heartbeat.Host;

import java.util.HashMap;
import java.util.Map;

public class HeartBeatManager {
    private Map<String, Host> monitoredHosts;

    public HeartBeatManager() {
        this.monitoredHosts = new HashMap<String, Host>();
    }

    public HeartBeatManager(Map<String, Host> monitoredHosts) {
        this.monitoredHosts = monitoredHosts;
    }

    public void addHost(String name, Host host) {
        if(monitoredHosts.get(name) == null) {
            monitoredHosts.put(name, host);
        }
    }

    public void removeHost(String name) {
        monitoredHosts.remove(name);
    }
}
