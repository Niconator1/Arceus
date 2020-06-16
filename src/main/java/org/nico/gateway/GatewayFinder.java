package org.nico.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.rest.RequestType;
import org.nico.rest.RestRequest;

import java.util.Objects;

public class GatewayFinder implements Runnable {

    private Gateway gateway;

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            RestRequest discoverRequest = new RestRequest("https://dresden-light.appspot.com/discover", RequestType.GET);
            try {
                Gateway gateway = mapper.readValue(discoverRequest.getResult().substring(1, discoverRequest.getResult().length() - 1), Gateway.class);
                if (Objects.isNull(this.gateway)) {
                    this.gateway = gateway;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10000L);
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
}
