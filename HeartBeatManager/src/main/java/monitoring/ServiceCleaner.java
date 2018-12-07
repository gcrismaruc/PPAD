package monitoring;

import main.HeartBeatManager;

import java.time.Duration;
import java.time.Instant;

public class ServiceCleaner implements Runnable {
    private HeartBeatManager manager;

    @Override
    public void run() {

        System.out.println("Starting cleaning...");
        Instant now = Instant.now();

        manager.getMonitoredHosts()
                .entrySet()
                .removeIf(uuidHostEntry -> {

                    System.out.println("Duration: " + Duration.between(uuidHostEntry.getValue()
                            .getUpdatedTime(), now)
                            .getSeconds());

                    return Duration.between(uuidHostEntry.getValue()
                            .getUpdatedTime(), now)
                            .getSeconds() >= 10;
                });
    }

    public HeartBeatManager getManager() {
        return manager;
    }

    public ServiceCleaner setManager(HeartBeatManager manager) {
        this.manager = manager;
        return this;
    }
}
