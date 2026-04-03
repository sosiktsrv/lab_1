package org.example.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Curse {
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("threatLevel")
    @JacksonXmlProperty(localName = "threatLevel")
    private String threatLevel;

    public Curse() {}

    public Curse(String name, String threatLevel) {
        this.name = name;
        this.threatLevel = threatLevel;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getThreatLevel() { return threatLevel; }
    public void setThreatLevel(String threatLevel) { this.threatLevel = threatLevel; }
}
