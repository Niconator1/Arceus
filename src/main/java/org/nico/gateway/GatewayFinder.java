package org.nico.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.rest.RequestType;
import org.nico.rest.RestRequest;

import java.util.List;
import java.util.Objects;

public class GatewayFinder implements Runnable {

    private Gateway gateway;

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            RestRequest discoverRequest = new RestRequest("https://dresden-light.appspot.com/discover", RequestType.GET);
            try {
                List<Gateway> listGateway = mapper.readValue(discoverRequest.getResult(), new TypeReference<List<Gateway>>() {
                });
                if (!listGateway.isEmpty()) {
                    Gateway gateway = listGateway.get(listGateway.size() - 1);
                    if (Objects.isNull(this.gateway)) {
                        this.gateway = gateway;
                    }
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
