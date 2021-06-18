package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Spieler;
import com.example.NBA_Shop.model.Schuh;
import com.example.NBA_Shop.model.Jersey;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the NBA-Shop
 * <p>
 * M133: NBA-Shop
 *
 * @author Luigi Spina
 */

@Path("spieler")
public class SpielerService {

    /**
     * produces a map of all players
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPlayers(
    ) {
        Map<String, Spieler> spielerMap = DataHandler.getSpielerMap();
        Response response = Response
                .status(200)
                .entity(spielerMap)
                .build();
        return response;

    }

    /**
     * reads a single player identified by the playerID
     *
     * @param spielerUUID the bookUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPlayer(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String spielerUUID
    ) {
        Spieler spieler = null;
        int httpStatus;

        try {
            UUID spielerKey = UUID.fromString(spielerUUID);
            spieler = DataHandler.readSpieler(spielerUUID);
            if (spieler.getNachname() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(spieler)
                .build();
        return response;
    }


    /**
     * creates a new player
     * @param schuhUUID shoe of player
     * @param jerseyUUID jersey of player
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSpieler(
            @Valid @BeanParam Spieler spieler,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("schuhUUID") String schuhUUID,
            @FormParam("jerseyUUID") String jerseyUUID
    ) {
        int httpStatus = 200;
        spieler.setSpielerUUID(UUID.randomUUID().toString());
        Schuh schuh = DataHandler.readSchuh(schuhUUID);
        Jersey jersey = DataHandler.readJersey(jerseyUUID);

        if (schuh != null){
            spieler.setSchuh(schuh);
            DataHandler.saveSpieler(spieler);
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        if (jersey != null){
            spieler.setJersey(jersey);
            DataHandler.saveSpieler(spieler);
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }


        /*setValues(
                spieler,
                vorname,
                nachname,
                spielerUUID,
                alter,
                schuhUUID,
                jerseyUUID

        );*/

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing player
     * @param schuhUUID shoe of player
     * @param jerseyUUID jersey of player
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSpieler(
            @Valid @BeanParam Spieler spieler,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")

            @FormParam("schuhUUID")String schuhUUID,
            @FormParam("jerseyUUID") String jerseyUUID
    ) {
        int httpStatus = 200;
        Spieler alterSpieler = DataHandler.readSpieler(spieler.getSpielerUUID());

        if (alterSpieler.getVorname() != null){
            httpStatus = 200;
            alterSpieler.setVorname(spieler.getVorname());
            alterSpieler.setNachname(spieler.getNachname());
            alterSpieler.setAlter(spieler.getAlter());
            alterSpieler.setSpielerUUID(spieler.getSpielerUUID());

            Schuh schuh = DataHandler.readSchuh(schuhUUID);
            alterSpieler.setSchuh(schuh);

            Jersey jersey = DataHandler.readJersey(jerseyUUID);
            alterSpieler.setJersey(jersey);

            DataHandler.updateSpieler();
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing player identified by its uuid
     * @param spielerUUID  the unique key for the player
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSpieler(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String spielerUUID
    ) {
        int httpStatus;

        try {
            UUID.fromString(spielerUUID);

            if (DataHandler.deleteSpieler(spielerUUID)) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
    /**
     * sets the attribute values of the book object
     * @param vorname firstname of player
     * @param nachname lastname of player
     * @param spielerUUID a unique id of player
     * @param alter the age of player
     * @param schuhUUID shoe of player
     * @param jerseyUUID jersey of player
     */

    private void setValues(
            Spieler spieler,
            String vorname,
            String nachname,
            String spielerUUID,
            int alter,
            String schuhUUID,
            String jerseyUUID) {
        spieler.setVorname(vorname);
        spieler.setNachname(nachname);
        spieler.setSpielerUUID(spielerUUID);
        spieler.setAlter(alter);

        Schuh schuh = DataHandler.getSchuhMap().get(schuhUUID);
        spieler.setSchuh(schuh);

        Jersey jersey = DataHandler.getJerseyMap().get(jerseyUUID);
        spieler.setJersey(jersey);
    }




}
