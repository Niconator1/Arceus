package org.nico.gateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.nico.rest.RequestType;
import org.nico.rest.RestRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "internalipaddress",
        "macaddress",
        "internalport",
        "name",
        "publicipaddress"
})
public class Gateway {

    @JsonProperty("id")
    public String id;
    @JsonProperty("internalipaddress")
    public String internalIpAddress;
    @JsonProperty("macaddress")
    public String macAddress;
    @JsonProperty("internalport")
    public Integer internalPort;
    @JsonProperty("name")
    public String name;
    @JsonProperty("publicipaddress")
    public String publicIpAddress;

    public String sendMessage(String suffix, String payload, RequestType type) {
        String address = "http://" + internalIpAddress + ":" + internalPort + "/api/A89250547D/" + suffix;
        RestRequest request = new RestRequest(address, type);
        System.out.println(type + " " + address);
        request.setPayload(payload);
        return request.getResult();
    }
}