package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Jersey;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String jerseyUUID
    ) {
        Jersey jersey = null;
        int httpStatus;

        jersey = DataHandler.readJersey(jerseyUUID);
        if (jersey.getSpielerName() != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }


        Response response = Response
                .status(httpStatus)
                .entity(jersey)
                .build();
        return response;
    }

    /**
     * creates a new jersey without player
     *
     * @param jersey
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createJersey(
            @Valid @BeanParam Jersey jersey

    ) {
        int httpStatus = 200;
        jersey.setJerseyUUID(UUID.randomUUID().toString());
        DataHandler.saveJersey(jersey);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the shoes
     *
     * @param jersey
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateJersey(
            @Valid @BeanParam Jersey jersey
    ) {
        int httpStatus = 200;

        if (DataHandler.updateJersey(jersey)) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteJersey(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String jerseyUUID
    ) {
        int httpStatus;

        int errorcode = DataHandler.deleteSchuh(jerseyUUID);
        if (errorcode == 0) httpStatus = 200;
        else if (errorcode == -1) httpStatus = 409;
        else httpStatus = 404;


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}