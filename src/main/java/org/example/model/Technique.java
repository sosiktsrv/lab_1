package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Technique {
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    private String type;

    @JsonProperty("owner")
    @JacksonXmlProperty(localName = "owner")
    private String owner;

    @JsonProperty("damage")
    @JacksonXmlProperty(localName = "damage")
    private long damage;

    public Technique() {}

    public Technique(String name, String type, String owner, long damage) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.damage = damage;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public long getDamage() { return damage; }
    public void setDamage(long damage) { this.damage = damage; }
}
