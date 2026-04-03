package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "mission")
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем неизвестные поля при парсинге
public class Mission {
    @JsonProperty("missionId")
    @JacksonXmlProperty(localName = "missionId")
    private String missionId;

    @JsonProperty("date")
    @JacksonXmlProperty(localName = "date")
    private String date;

    @JsonProperty("location")
    @JacksonXmlProperty(localName = "location")
    private String location;

    @JsonProperty("outcome")
    @JacksonXmlProperty(localName = "outcome")
    private String outcome;

    @JsonProperty("damageCost")
    @JacksonXmlProperty(localName = "damageCost")
    private long damageCost;

    @JsonProperty("curse")
    @JacksonXmlProperty(localName = "curse")
    private Curse curse;

    @JsonProperty("sorcerers")
    @JacksonXmlElementWrapper(localName = "sorcerers")
    @JacksonXmlProperty(localName = "sorcerer")
    private List<Sorcerer> sorcerers;

    @JsonProperty("techniques")
    @JacksonXmlElementWrapper(localName = "techniques")
    @JacksonXmlProperty(localName = "technique")
    private List<Technique> techniques;

    @JsonProperty("note")
    @JacksonXmlProperty(localName = "note")
    private String note;

    @JsonProperty("comment")
    @JacksonXmlProperty(localName = "comment")
    private String comment;

    public Mission() {}

    public Mission(String missionId, String date, String location, String outcome,
                   long damageCost, Curse curse, List<Sorcerer> sorcerers,
                   List<Technique> techniques, String note, String comment) {
        this.missionId = missionId;
        this.date = date;
        this.location = location;
        this.outcome = outcome;
        this.damageCost = damageCost;
        this.curse = curse;
        this.sorcerers = sorcerers;
        this.techniques = techniques;
        this.note = note;
        this.comment = comment;
    }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public long getDamageCost() { return damageCost; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }

    public Curse getCurse() { return curse; }
    public void setCurse(Curse curse) { this.curse = curse; }

    public List<Sorcerer> getSorcerers() { return sorcerers; }
    public void setSorcerers(List<Sorcerer> sorcerers) { this.sorcerers = sorcerers; }

    public List<Technique> getTechniques() { return techniques; }
    public void setTechniques(List<Technique> techniques) { this.techniques = techniques; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getAdditionalInfo() {
        if (note != null && !note.isEmpty()) {
            return note;
        }
        if (comment != null && !comment.isEmpty()) {
            return comment;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Миссия %s от %s в %s - %s, Урон: %d",
                missionId, date, location, outcome, damageCost);
    }
}