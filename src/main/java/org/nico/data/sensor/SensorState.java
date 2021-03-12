package org.nico.data.sensor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"lastupdated", "on", "temperature", "valve"})
public class SensorState {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("lastupdated")
    public String lastupdated;
    @JsonProperty("on")
    public Boolean on;
    @JsonProperty("temperature")
    public Integer temperature;
    @JsonProperty("valve")
    public Integer valve;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}