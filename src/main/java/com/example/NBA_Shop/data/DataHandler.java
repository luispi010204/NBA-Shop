package com.example.NBA_Shop.data;

import com.example.NBA_Shop.model.Jersey;
import com.example.NBA_Shop.model.Schuh;
import com.example.NBA_Shop.model.Spieler;
import com.example.NBA_Shop.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The DataHandler is used for reading and writing the json files
 *
 * M133: NBA-Shop
 *
 * @author Luigi Spina
 * @since 2021-06-04
 * @version 1.0
 *
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

    /**
     * reads the player using the param: spielerUUID
     */
    public static Spieler readSpieler(String spielerUUID) {
        Spieler spieler = new Spieler();
        if (getSpielerMap().containsKey(spielerUUID)) {
            spieler = getSpielerMap().get(spielerUUID);
        }
        return spieler;
    }

    /**
     * inserts a new Player into the SpielerMap
     *
     * @param spieler the player to be saved
     */
    public static void saveSpieler(Spieler spieler) {
        getSpielerMap().put(spieler.getSpielerUUID(), spieler);
        writeJSON();
    }

    /**
     * updates the Spielermap
     */
    public static void updateSpieler() {
        writeJSON();
    }

    /**
     * removes a player from the Spielermap
     *
     * @param spielerUUID the uuid of the player to be removed
     * @return success
     */
    public static boolean deleteSpieler(String spielerUUID) {
        if (getSpielerMap().remove(spielerUUID) != null) {
            writeJSON();
            return true;
        } else
            return false;
    }

    //end of Spieler

    /**
     * reads the shoe using the param: schuhUUID
     */
    public static Schuh readSchuh(String schuhUUID) {
        Schuh schuh = new Schuh();
        if (getSchuhMap().containsKey(schuhUUID)) {
            schuh = getSchuhMap().get(schuhUUID);
        }
        return schuh;
    }

    /**
     * inserts a new shoe into the SchuhMap
     *
     * @param schuh the shoe to be saved
     */
    public static void saveSchuh(Schuh schuh) {
        Spieler spieler  = new Spieler();
        spieler.setSpielerUUID(UUID.randomUUID().toString());
        spieler.setVorname("");
        spieler.setNachname("");
        spieler.setSchuh(schuh);
        saveSpieler(spieler);

    }

    /**
     * updates the Shuhmap
     */
    public static boolean updateSchuh(Schuh schuh) {
        boolean found = false;
        for (Map.Entry<String, Spieler> entry : getSpielerMap().entrySet()) {
            Spieler spieler = entry.getValue();
            if (spieler.getSchuh().getSchuhUUID().equals(schuh.getSchuhUUID())) {
                spieler.setSchuh(schuh);
                found = true;
            }
        }
            writeJSON();
            return found;
        }

    /**
     * removes a shoe from the Schuhmap
     *
     * @param schuhUUID the uuid of the shoe to be removed
     * @return errorcode  0=ok, -1=referential integrity, 1=not found
     */
    public static int deleteSchuh(String schuhUUID) {
        int errorcode = 1;
        for (Map.Entry<String, Spieler> entry : getSpielerMap().entrySet()) {
            Spieler spieler = entry.getValue();
            if (spieler.getSchuh().getSchuhUUID().equals(schuhUUID)) {
                if (spieler.getVorname() == null || spieler.getVorname().equals("")) {
                    deleteSpieler(spieler.getSpielerUUID());
                    errorcode = 0;
                } else {
                    return -1;
                }
            }
        }
        writeJSON();
        return errorcode;
    }

//end of Schuh


    /**
     * reads the jersey using the param: jerseyUUID
     */
    public static Jersey readJersey(String jerseyUUID) {
        Jersey jersey = new Jersey();
        if (getJerseyMap().containsKey(jerseyUUID)) {
            jersey = getJerseyMap().get(jerseyUUID);
        }
        return jersey;
    }

    /**
     * inserts a new jersey in an empty player
     * @param jersey
     */
    public static void saveJersey(Jersey jersey) {
        Spieler spieler = new Spieler();
        spieler.setSpielerUUID(UUID.randomUUID().toString());
        spieler.setVorname("");
        spieler.setNachname("");
        spieler.setJersey(jersey);
        saveSpieler(spieler);
    }

    /**
     * updates the Jerseymap
     */
    public static boolean updateJersey(Jersey jersey) {
        boolean found = false;
        for (Map.Entry<String, Spieler> entry : getSpielerMap().entrySet()) {
            Spieler spieler = entry.getValue();
            if (spieler.getJersey().getJerseyUUID().equals(jersey.getJerseyUUID())) {
                spieler.setJersey(jersey);
                found = true;
            }
        }
        writeJSON();
        return found;
    }

    /**
     * removes a jersey from the Jerseymap
     *
     * @param jerseyUUID the uuid of the jersey to be removed
     * @return success
     */
    public static int deleteJersey(String jerseyUUID) {
        int errorcode = 1;
        for (Map.Entry<String, Spieler> entry : getSpielerMap().entrySet()) {
            Spieler spieler = entry.getValue();
            if (spieler.getJersey().getJerseyUUID().equals(jerseyUUID)) {
                if (spieler.getVorname() == null || spieler.getVorname().equals("")) {
                    deleteSpieler(spieler.getSpielerUUID());
                    errorcode = 0;
                } else {
                    return -1;
                }
            }
        }
        writeJSON();
        return errorcode;
    }

//end of Jersey

    /**
     * gets the spielerMap
     *
     * @return the spielerMap
     */
    public static Map<String, Spieler> getSpielerMap() {
        return spielerMap;
    }

    /**
     * gets the schuhMap
     *
     * @return the schuhMap
     */
    public static Map<String, Schuh> getSchuhMap() {
        return schuhMap;
    }

    public static Map<String, Jersey> getJerseyMap() {
        return jerseyMap;
    }

    public static void setSchuhMap(Map<String, Schuh> Schuhmap) {
        DataHandler.schuhMap = schuhMap;
    }


    public static void setJerseyMap(Map<String, Jersey> JerseyMap) {
        DataHandler.jerseyMap = jerseyMap;
    }

    /**
     * reads the JSON-File (with Spieler, Schuh and Jersey)
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

    /**
     * writes the JSON-File (with Spieler, Schuh and Jersey)
     */
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