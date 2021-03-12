package org.nico.data.sensor;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"battery", "displayflipped", "heatsetpoint", "locked", "mode", "offset", "on", "reachable"})
@JsonFilter("sensorConfigFilter")
public class SensorConfig {

    private final List<String> modifiedList = new ArrayList<>();
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("battery")
    public Integer battery;
    @JsonProperty("displayflipped")
    public Object displayflipped;
    @JsonProperty("heatsetpoint")
    public Integer heatsetpoint;
    @JsonProperty("locked")
    public Object locked;
    @JsonProperty("mode")
    public String mode;
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("on")
    public Boolean on;
    @JsonProperty("reachable")
    public Boolean reachable;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void setModifiedHeatSetPoint(Integer heatsetpoint) {
        this.heatsetpoint = heatsetpoint;
        this.modifiedList.add("heatsetpoint");
    }

    public List<String> getModifiedFields() {
        return this.modifiedList;
    }

}