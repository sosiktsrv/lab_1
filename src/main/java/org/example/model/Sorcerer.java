package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Sorcerer {
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("rank")
    @JacksonXmlProperty(localName = "rank")
    private String rank;

    public Sorcerer() {}

    public Sorcerer(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
}

