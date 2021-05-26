package com.example.NBA_Shop.data;

import com.example.NBA_Shop.model.Jersey;
import com.example.NBA_Shop.model.Schuh;
import com.example.NBA_Shop.model.Spieler;

import java.util.HashMap;
import java.util.Map;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
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
        //readJSON();
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
       // writeJSON();
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
        //writeJSON();
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
        //writeJSON();
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
    /* !!! In den anderen Methoden wurden readJSON und writeJSON noch auskommentiert.
    private static void readJSON() {
        try {
            String bookPath = Config.getProperty("bookJSON");
            byte[] jsonData = Files.readAllBytes(Paths.get(bookPath));
            ObjectMapper objectMapper = new ObjectMapper();
            Book[] books = objectMapper.readValue(jsonData, Book[].class);
            for (Book book : books) {
                String publisherUUID = book.getPublisher().getPublisherUUID();
                Publisher publisher;
                if (getPublisherMap().containsKey(publisherUUID)) {
                    publisher = getPublisherMap().get(publisherUUID);
                } else {
                    publisher = book.getPublisher();
                    getPublisherMap().put(publisherUUID, publisher);
                }
                book.setPublisher(publisher);
                getBookMap().put(book.getBookUUID(), book);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String bookPath = Config.getProperty("bookJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getBookMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

     */
}
