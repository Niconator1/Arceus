package org.nico.gateway;

import java.util.Objects;

public class Gateway {

    private final String ip;
    private final int port;

    public Gateway(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getFullAddress() {
        return this.ip + ":" + this.port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Gateway) {
            Gateway gateway = (Gateway) o;
            return port == gateway.port && Objects.equals(ip, gateway.ip);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
