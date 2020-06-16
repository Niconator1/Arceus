package org.nico.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "action",
        "devicemembership",
        "etag",
        "id",
        "lights",
        "name",
        "scenes",
        "state",
        "type"
})
public class Group {

    @JsonProperty("lights")
    public List<String> lights = null;
    @JsonProperty("name")
    public String name;

    @JsonIgnore
    public Object action;
    @JsonIgnore
    public List<Object> devicemembership = null;
    @JsonIgnore
    public String etag;
    @JsonIgnore
    public String id;
    @JsonIgnore
    public List<Object> scenes = null;
    @JsonIgnore
    public Object state;
    @JsonIgnore
    public String type;

}