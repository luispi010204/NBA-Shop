package com.example.NBA_Shop.data;

import com.example.NBA_Shop.model.Jersey;
import com.example.NBA_Shop.model.Schuh;
import com.example.NBA_Shop.model.Spieler;
import com.example.NBA_Shop.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * data handler for reading and writing the json files
 * <p>
 * M133: NBA Shop
 *
 * @author Luigi Spina
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Spieler> spielerMap;
    private static Map<String, Schuh> schuhMap;
    private static Map<String, Jersey> jerseyMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        spielerMap = new HashMap<>();
        schuhMap = new HashMap<>();
        jerseyMap = new HashMap<>();
        readJSON();
    }


    public static Spieler readSpieler(String spielerUUID) {
        Spieler spieler = new Spieler();
        if (getSpielerMap().containsKey(spielerUUID)) {
            spieler = getSpielerMap().get(spielerUUID);
        }
        return spieler;
    }


    public static void saveSpieler(Spieler spieler) {
        getSpielerMap().put(spieler.getSpielerUUID(), spieler);
       writeJSON();
    }

    //schuh


    public static Schuh readSchuh(String schuhUUID) {
        Schuh schuh = new Schuh();
        if (getSchuhMap().containsKey(schuhUUID)) {
            schuh = getSchuhMap().get(schuhUUID);
        }
        return schuh;
    }

    public static void saveSchuh(Schuh schuh) {
        getSchuhMap().put(schuh.getSchuhUUID(), schuh);
        writeJSON();
    }
//ende Schuh


    //Jersey
    public static Jersey readJersey(String jerseyUUID) {
        Jersey jersey = new Jersey();
        if (getJerseyMap().containsKey(jerseyUUID)) {
            jersey = getJerseyMap().get(jerseyUUID);
        }
        return jersey;
    }

    public static void saveJersey(Schuh schuh) {
        getSchuhMap().put(schuh.getSchuhUUID(), schuh);
        writeJSON();
    }


    /**
     * gets the bookMap
     *
     * @return the bookMap
     */
    public static Map<String, Spieler> getSpielerMap() {
        return spielerMap;
    }

    /**
     * gets the publisherMap
     *
     * @return the publisherMap
     */
    public static Map<String, Schuh> getSchuhMap() {
        return schuhMap;
    }

    public static void setSchuhMap(Map<String, Schuh> Schuhmap) {
        DataHandler.schuhMap = schuhMap;
    }


    public static Map<String, Jersey> getJerseyMap() {
        return jerseyMap;
    }

    public static void setJerseyMap(Map<String, Jersey> JerseyMap) {
        DataHandler.jerseyMap = jerseyMap;
    }

    /**
     * reads the books and publishers
     */

    private static void readJSON() {
        try {
            String spielerPath = Config.getProperty("spielerJSON");
            byte[] jsonData = Files.readAllBytes(Paths.get(spielerPath));
            ObjectMapper objectMapper = new ObjectMapper();
            Spieler[] spielers = objectMapper.readValue(jsonData, Spieler[].class);
            for (Spieler spieler : spielers) {
                String schuhUUID = spieler.getSchuh().getSchuhUUID();
                Schuh schuh;
                if (getSchuhMap().containsKey(schuhUUID)) {
                    schuh = getSchuhMap().get(schuhUUID);
                } else {
                    schuh = spieler.getSchuh();
                    getSchuhMap().put(schuhUUID, schuh);
                }
                spieler.setSchuh(schuh);
                //getSpielerMap().put(spieler.getSpielerUUID(), spieler);

                String jerseyUUID = spieler.getJersey().getJerseyUUID();
                Jersey jersey;

                if (getJerseyMap().containsKey(jerseyUUID)) {
                    jersey = getJerseyMap().get(jerseyUUID);
                } else {
                    jersey = spieler.getJersey();
                    getJerseyMap().put(jerseyUUID, jersey);
                }
                spieler.setJersey(jersey);
                getSpielerMap().put(spieler.getSpielerUUID(), spieler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String spielerPath = Config.getProperty("SpielerJSON");
        try {
            fileOutputStream = new FileOutputStream(spielerPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getSpielerMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}