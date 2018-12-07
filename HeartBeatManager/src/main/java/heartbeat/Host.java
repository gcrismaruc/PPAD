package heartbeat;

import sender.Service;

import java.time.Instant;
import java.util.List;

public class Host {

    Instant updatedTime;
    String name;
    List<Service> services;
    String version;

    public String getName() {
        return name;
    }

    public Host setName(String name) {
        this.name = name;
        return this;
    }

    public List<Service> getServices() {
        return services;
    }

    public Host setServices(List<Service> services) {
        this.services = services;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Host setVersion(String version) {
        this.version = version;
        return this;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public Host setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "Host{" + "updatedTime=" + updatedTime + ", name='" + name + '\'' + ", services="
                + services + ", version='" + version + '\'' + '}';
    }
}
