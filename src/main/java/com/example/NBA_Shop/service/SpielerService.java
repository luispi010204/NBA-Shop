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
            @CookieParam("userRole") String userRole
    ) {
        if (UserRole.isInvalid(userRole)) return UserRole.createInvalidUserResponse(userRole);

        return UserRole.createResponse(200, DataHandler.getSpielerMap(), userRole);
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
            @QueryParam("uuid") String spielerUUID,
            @CookieParam("userRole") String userRole
    ) {
        if (UserRole.isInvalid(userRole)) return UserRole.createInvalidUserResponse(userRole);

        Spieler spieler = DataHandler.readSpieler(spielerUUID);;

        int httpStatus = 404;
        if (spieler.getVorname() != null) {
            httpStatus = 200;
        }

        return UserRole.createResponse(httpStatus, spieler);
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
            @FormParam("jerseyUUID") String jerseyUUID,
            @CookieParam("userRole") String userRole
    ) {
        if (UserRole.isInvalid(userRole)) return UserRole.createInvalidUserResponse(userRole);

        int httpStatus = 404;

        spieler.setSpielerUUID(UUID.randomUUID().toString());
        Schuh schuh = DataHandler.readSchuh(schuhUUID);
        Jersey jersey = DataHandler.readJersey(jerseyUUID);
        if (schuh != null || jersey != null) {
            spieler.setSchuh(schuh);
            spieler.setJersey(jersey);
            DataHandler.saveSpieler(spieler);
            httpStatus = 200;
        }

        return UserRole.createResponse(httpStatus, "");
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
            @FormParam("jerseyUUID") String jerseyUUID,
            @CookieParam("userRole") String userRole
    ) {
        if (UserRole.isInvalid(userRole)) return UserRole.createInvalidUserResponse(userRole);

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
        return UserRole.createResponse(httpStatus, "");
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
            @QueryParam("uuid") String spielerUUID,
            @CookieParam("userRole") String userRole
    ) {

        if (UserRole.isInvalid(userRole)) return UserRole.createInvalidUserResponse(userRole);

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
        return UserRole.createResponse(httpStatus, "");
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
