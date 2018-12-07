package heartbeat;

import java.net.InetAddress;

public class Host {
    InetAddress inetAddress;
    int port;
    State state;

    public int getPort() {
        return port;
    }

    public Host setPort(int port) {
        this.port = port;
        return this;
    }

    public State getState() {
        return state;
    }

    public Host setState(State state) {
        this.state = state;
        return this;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public Host setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
        return this;
    }
}
