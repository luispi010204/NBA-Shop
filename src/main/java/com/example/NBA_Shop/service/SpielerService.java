package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Spieler;
import com.example.NBA_Shop.model.Schuh;
import com.example.NBA_Shop.model.Jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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


}
