package org.nico.data.sensor;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.nico.rest.RestRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"config", "ep", "etag", "lastseen", "manufacturername", "modelid", "name", "state", "swversion", "type", "uniqueid"})
public class Sensor {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("config")
    public SensorConfig config;
    @JsonProperty("ep")
    public Integer ep;
    @JsonProperty("etag")
    public String etag;
    @JsonProperty("lastseen")
    public String lastseen;
    @JsonProperty("manufacturername")
    public String manufacturername;
    @JsonProperty("modelid")
    public String modelid;
    @JsonProperty("name")
    public String name;
    @JsonProperty("state")
    public SensorState state;
    @JsonProperty("swversion")
    public String swversion;
    @JsonProperty("type")
    public String type;
    @JsonProperty("uniqueid")
    public String uniqueid;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void synchronize(String id) {
        synchronizeConfig(id);
    }

    private void synchronizeConfig(String id) {
        List<String> modifiedList = config.getModifiedFields();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("sensorConfigFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(modifiedList)));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(filterProvider);
        String payload = null;
        try {
            payload = mapper.writeValueAsString(config);
            RestRequest restRequest = new RestRequest("sensors/" + id + "/config", "PUT", payload);
            restRequest.send();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        modifiedList.clear();
    }

}