package org.example.parser;

import org.apache.commons.configuration2.io.FileHandler;
import org.example.exception.InvalidMissionFormatException;
import org.example.model.*;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TxtMissionParser implements MissionParser {

    @Override
    public Mission parse(File file) throws InvalidMissionFormatException {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration();

            FileHandler fileHandler = new FileHandler(config);
            fileHandler.setEncoding(StandardCharsets.UTF_8.name());
            fileHandler.load(file);

            Mission mission = new Mission();

            mission.setMissionId(config.getString("missionId"));
            mission.setDate(config.getString("date"));
            mission.setLocation(config.getString("location"));
            mission.setOutcome(config.getString("outcome"));
            mission.setDamageCost(config.getLong("damageCost", 0L));

            mission.setNote(config.getString("note"));
            mission.setComment(config.getString("comment"));

            //проклятия
            Curse curse = new Curse();
            curse.setName(config.getString("curse.name"));
            curse.setThreatLevel(config.getString("curse.threatLevel"));
            mission.setCurse(curse);

            //маги
            List<Sorcerer> sorcerers = new ArrayList<>();
            int sorcererIndex = 0;
            while (config.containsKey("sorcerer[" + sorcererIndex + "].name")) {
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(config.getString("sorcerer[" + sorcererIndex + "].name"));
                sorcerer.setRank(config.getString("sorcerer[" + sorcererIndex + "].rank"));
                sorcerers.add(sorcerer);
                sorcererIndex++;
            }
            mission.setSorcerers(sorcerers);

            //техники
            List<Technique> techniques = new ArrayList<>();
            int techniqueIndex = 0;
            while (config.containsKey("technique[" + techniqueIndex + "].name")) {
                Technique technique = new Technique();
                technique.setName(config.getString("technique[" + techniqueIndex + "].name"));
                technique.setType(config.getString("technique[" + techniqueIndex + "].type"));
                technique.setOwner(config.getString("technique[" + techniqueIndex + "].owner"));
                technique.setDamage(config.getLong("technique[" + techniqueIndex + "].damage", 0L));
                techniques.add(technique);
                techniqueIndex++;
            }
            mission.setTechniques(techniques);

            validateMission(mission);
            return mission;

        } catch (ConfigurationException e) {
            throw new InvalidMissionFormatException("Ошибка парсинга TXT файла: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean supportsFormat(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".txt");
    }

    private void validateMission(Mission mission) throws InvalidMissionFormatException {
        if (mission.getMissionId() == null || mission.getMissionId().trim().isEmpty()) {
            throw new InvalidMissionFormatException("Отсутствует missionId");
        }
        if (mission.getCurse() == null || mission.getCurse().getName() == null) {
            throw new InvalidMissionFormatException("Отсутствует информация о проклятии");
        }
        if (mission.getSorcerers() == null || mission.getSorcerers().isEmpty()) {
            throw new InvalidMissionFormatException("Отсутствуют маги в миссии");
        }
    }
}