package org.nico.data.light;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"alert", "bri", "colormode", "ct", "effect", "hue", "on", "reachable", "sat", "xy"})
@JsonFilter("lightActionFilter")
public class LightState {

    private final List<String> modifiedList = new ArrayList<>();
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("alert")
    public String alert;
    @JsonProperty("bri")
    public Integer bri;
    @JsonProperty("colormode")
    public String colormode;
    @JsonProperty("ct")
    public Integer ct;
    @JsonProperty("effect")
    public String effect;
    @JsonProperty("hue")
    public Integer hue;
    @JsonProperty("on")
    public Boolean on;
    @JsonProperty("reachable")
    public Boolean reachable;
    @JsonProperty("sat")
    public Integer sat;
    @JsonProperty("xy")
    public List<Double> xy = null;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void setModifiedBri(Integer bri) {
        this.bri = bri;
        this.modifiedList.add("bri");
    }

    public void setModifiedSat(Integer sat) {
        this.sat = sat;
        this.modifiedList.add("sat");
    }

    public void setModifiedHue(Integer hue) {
        this.hue = hue;
        this.modifiedList.add("hue");
    }

    public void setModifiedOn(Boolean on) {
        this.on = on;
        this.modifiedList.add("on");
    }

    public List<String> getModifiedFields() {
        return this.modifiedList;
    }

}