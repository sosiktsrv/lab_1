package org.example.ui;

import org.example.exception.InvalidMissionFormatException;
import org.example.model.Mission;
import org.example.model.Technique;
import org.example.parser.*;
import org.example.parser.JsonMissionParser;
import org.example.parser.MissionParser;
import org.example.parser.TxtMissionParser;
import org.example.parser.XmlMissionParser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionViewerUI extends JFrame {
    private JTextArea textArea;
    private Mission currentMission;
    private List<MissionParser> parsers;

    public MissionViewerUI() {
        parsers = new ArrayList<>();
        parsers.add(new JsonMissionParser());
        parsers.add(new XmlMissionParser());
        parsers.add(new TxtMissionParser());

        setTitle("Просмотр миссий");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton loadButton = new JButton("Загрузить файл");
        JButton clearButton = new JButton("Очистить");

        topPanel.add(loadButton);
        topPanel.add(clearButton);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Информация о миссии"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        loadButton.addActionListener(e -> loadFile());
        clearButton.addActionListener(e -> clearText());
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл миссии");

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON файлы", "json"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML файлы", "xml"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT файлы", "txt"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                MissionParser parser = null;
                for (MissionParser p : parsers) {
                    if (p.supportsFormat(selectedFile)) {
                        parser = p;
                        break;
                    }
                }

                if (parser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Неподдерживаемый формат файла",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                currentMission = parser.parse(selectedFile);

                showMissionInfo();

            } catch (InvalidMissionFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка в файле: " + e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Неизвестная ошибка: " + e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showMissionInfo() {
        if (currentMission == null) return;

        StringBuilder sb = new StringBuilder();

        sb.append("МИССИЯ\n");
        sb.append("ID: ").append(currentMission.getMissionId()).append("\n");
        sb.append("Дата: ").append(currentMission.getDate()).append("\n");
        sb.append("Место: ").append(currentMission.getLocation()).append("\n");
        sb.append("Результат: ").append(currentMission.getOutcome()).append("\n");
        sb.append("Ущерб: ").append(currentMission.getDamageCost()).append(" ¥\n");
        sb.append("\n");

        if (currentMission.getCurse() != null) {
            sb.append("ПРОКЛЯТИЕ\n");
            sb.append("Имя: ").append(currentMission.getCurse().getName()).append("\n");
            sb.append("Уровень: ").append(currentMission.getCurse().getThreatLevel()).append("\n");
            sb.append("\n");
        }

        if (currentMission.getSorcerers() != null && !currentMission.getSorcerers().isEmpty()) {
            sb.append("МАГИ\n");
            for (int i = 0; i < currentMission.getSorcerers().size(); i++) {
                var sorcerer = currentMission.getSorcerers().get(i);
                sb.append(i + 1).append(". ").append(sorcerer.getName());
                sb.append(" (").append(sorcerer.getRank()).append(")\n");
            }
            sb.append("\n");
        }


        if (currentMission.getTechniques() != null && !currentMission.getTechniques().isEmpty()) {
            sb.append("ТЕХНИКИ\n");
            long totalDamage = 0;
            for (int i = 0; i < currentMission.getTechniques().size(); i++) {
                Technique t = currentMission.getTechniques().get(i);
                sb.append(i + 1).append(". ").append(t.getName()).append("\n");
                sb.append("   Тип: ").append(t.getType()).append("\n");
                sb.append("   Владелец: ").append(t.getOwner()).append("\n");
                sb.append("   Урон: ").append(t.getDamage()).append(" ¥\n");
                totalDamage += t.getDamage();
            }
            sb.append("\n   Общий урон: ").append(totalDamage).append(" ¥\n");
            sb.append("\n");
        }

        String extra = currentMission.getAdditionalInfo();
        if (extra != null && !extra.isEmpty()) {
            sb.append("ПРИМЕЧАНИЯ\n");
            sb.append(extra).append("\n");
        }

        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);
    }

    private void clearText() {
        textArea.setText("");
        currentMission = null;
    }
}