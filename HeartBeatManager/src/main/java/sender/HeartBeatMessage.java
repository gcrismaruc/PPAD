package sender;

import java.util.UUID;

public class HeartBeatMessage {
    private UUID uuid;
    private String name;
    private String version;
    private String services;

    public UUID getUuid() {
        return uuid;
    }

    public HeartBeatMessage setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeartBeatMessage setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public HeartBeatMessage setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getServices() {
        return services;
    }

    public HeartBeatMessage setServices(String services) {
        this.services = services;
        return this;
    }

    @Override
    public String toString() {
        return "uuid=" + uuid + "," + "name=" + name + "," +  "version="
                + version + "," + "services=" + services;
    }
}
