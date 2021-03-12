package org.nico.gateway;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GatewayManager implements Runnable {

    private final static String DISCOVER_MESSAGE_ROOTDEVICE = "M-SEARCH * HTTP/1.1\r\n" + "ST: upnp:rootdevice\r\n" + "MX: 0\r\n" + "MAN: \"ssdp:discover\"\r\n" + "HOST: 239.255.255.250:1900\r\n\r\n";

    private volatile Gateway gateway;

    @Override
    public void run() {
        while (true) {
            try {
                List<Gateway> listGateways = multicast();
                if (!listGateways.isEmpty()) {
                    if (gateway != null) {
                        if (!listGateways.contains(gateway)) {
                            gateway = listGateways.get(0);
                        }
                    } else {
                        gateway = listGateways.get(0);
                    }
                } else {
                    System.out.println("Gatewaymanager konnte kein Gateway finden");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long delay = 60000L;
            if (gateway != null) {
                System.out.println("Gateway: " + gateway.getFullAddress());
                delay = 300000L;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Gateway getGateway() {
        return this.gateway;
    }

    public void resetGateway() {
        this.gateway = null;
    }

    private List<NetworkInterface> listAllAvailableNetworkInterfaces() throws SocketException {
        List<NetworkInterface> networkInterfaceList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (!networkInterface.isUp()) {
                continue;
            }
            networkInterfaceList.add(networkInterface);
        }
        return networkInterfaceList;
    }

    public List<Gateway> multicast() throws IOException {
        List<Gateway> gatewayList = new ArrayList<>();
        List<NetworkInterface> listNetworkInterfaces = listAllAvailableNetworkInterfaces();
        try {
            InetAddress multicastAddress = InetAddress.getByName("239.255.255.250");
            final int port = 1900;
            InetSocketAddress group = new InetSocketAddress(multicastAddress, port);
            MulticastSocket socket = new MulticastSocket(port);
            socket.setReuseAddress(true);
            socket.setSoTimeout(15000);
            listNetworkInterfaces.forEach(networkInterface -> {
                try {
                    socket.joinGroup(group, networkInterface);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            byte[] txbuf = DISCOVER_MESSAGE_ROOTDEVICE.getBytes(StandardCharsets.UTF_8);
            DatagramPacket discoveryPacket = new DatagramPacket(txbuf, txbuf.length, multicastAddress, port);
            socket.send(discoveryPacket);
            System.out.println("SSDP discovery sent");
            long sendTime = System.currentTimeMillis();
            do {
                byte[] rxbuf = new byte[8192];
                DatagramPacket responsePacket = new DatagramPacket(rxbuf, rxbuf.length);
                socket.receive(responsePacket);
                Gateway gateway = analyzePacket(responsePacket);
                if (gateway != null) {
                    if (!gatewayList.contains(gateway)) {
                        gatewayList.add(gateway);
                    }
                }
            } while (System.currentTimeMillis() - sendTime < 5000 || gatewayList.size() == 0);
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout");
        }
        return gatewayList;
    }

    private Gateway analyzePacket(DatagramPacket packet) {
        InetAddress address = packet.getAddress();
        System.out.println("Response from: " + address);
        String response = new String(packet.getData());
        if (response.contains("GWID.phoscon.de:")) {
            System.out.println("Gateway Discovered");
            return new Gateway(address.getHostAddress(), 80);
        }
        return null;
    }
}
