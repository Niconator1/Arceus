package org.nico.data.light;

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
@JsonPropertyOrder({"ctmax", "ctmin", "etag", "hascolor", "lastseen", "manufacturername", "modelid", "name", "state", "swversion", "type", "uniqueid"})
public class Light {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("ctmax")
    public Integer ctmax;
    @JsonProperty("ctmin")
    public Integer ctmin;
    @JsonProperty("etag")
    public String etag;
    @JsonProperty("hascolor")
    public Boolean hascolor;
    @JsonProperty("lastseen")
    public String lastseen;
    @JsonProperty("manufacturername")
    public String manufacturername;
    @JsonProperty("modelid")
    public String modelid;
    @JsonProperty("name")
    public String name;
    @JsonProperty("state")
    public LightState state;
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
        synchronizeState(id);
    }

    private void synchronizeState(String id) {
        List<String> modifiedList = state.getModifiedFields();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("lightActionFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(modifiedList)));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(filterProvider);
        String payload = null;
        try {
            payload = mapper.writeValueAsString(state);
            RestRequest restRequest = new RestRequest("lights/" + id + "/state", "PUT", payload);
            restRequest.send();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        modifiedList.clear();
    }

}