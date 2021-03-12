package org.nico.data.group;

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
@JsonPropertyOrder({"action", "devicemembership", "etag", "id", "lights", "name", "scenes", "state", "type"})
public class Group {

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("action")
    public Action action;
    @JsonProperty("devicemembership")
    public List<Object> devicemembership = null;
    @JsonProperty("etag")
    public String etag;
    @JsonProperty("id")
    public String id;
    @JsonProperty("lights")
    public List<String> lights = null;
    @JsonProperty("name")
    public String name;
    @JsonProperty("scenes")
    public List<Object> scenes = null;
    @JsonProperty("state")
    public GroupState state;
    @JsonProperty("type")
    public String type;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void synchronize() {
        synchronizeAction();
    }

    private void synchronizeAction() {
        List<String> modifiedList = action.getModifiedFields();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("groupActionFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(modifiedList)));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(filterProvider);
        String payload = null;
        try {
            payload = mapper.writeValueAsString(action);
            RestRequest restRequest = new RestRequest("groups/" + id + "/action", "PUT", payload);
            restRequest.send();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        modifiedList.clear();
    }
}