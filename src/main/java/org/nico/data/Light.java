package org.nico.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ctmax",
        "ctmin",
        "etag",
        "hascolor",
        "lastseen",
        "manufacturername",
        "modelid",
        "name",
        "state",
        "swversion",
        "type",
        "uniqueid"
})
public class Light {

    @JsonIgnore
    public Integer ctmax;
    @JsonIgnore
    public Integer ctmin;
    @JsonIgnore
    public String etag;
    @JsonIgnore
    public Boolean hascolor;
    @JsonIgnore
    public String lastseen;
    @JsonIgnore
    public String manufacturername;
    @JsonIgnore
    public String modelid;
    @JsonProperty("name")
    public String name;
    @JsonIgnore
    public Object state;
    @JsonIgnore
    public String swversion;
    @JsonIgnore
    public String type;
    @JsonIgnore
    public String uniqueid;

}