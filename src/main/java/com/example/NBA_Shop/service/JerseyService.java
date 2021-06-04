package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the Shoes
 * <p>
 * M133: NBA-Shop
 *
 * @author Luigi Spina
 */

@Path("jersey")
public class JerseyService {

    /**
     * produces a map of every jersey
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listJerseys(
    ) {
        Map<String, Jersey> jerseyMap = DataHandler.getJerseyMap();
        Response response = Response
                .status(200)
                .entity(jerseyMap)
                .build();
        return response;
    }

    /**
     * reads a single jersey identified by the uuid
     *
     * @param jerseyUUID the UUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readJersey(
            @QueryParam("uuid") String jerseyUUID
    ) {
        Jersey jersey = null;
        int httpStatus;

        try {
            UUID.fromString(jerseyUUID);
            jersey = DataHandler.readJersey(jerseyUUID);
            if (jersey.getSpielerName() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(jersey)
                .build();
        return response;
    }

}