package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Jersey;
import com.example.NBA_Shop.model.Schuh;

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

        return UserRole.createResponse(200, jerseyMap);
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
        int httpStatus = 404;

        Jersey jersey = DataHandler.readJersey(jerseyUUID);
        if (jersey.getSpielerName() != null) {
            httpStatus = 200;
        }

        return UserRole.createResponse(httpStatus, jersey);
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
        jersey.setJerseyUUID(UUID.randomUUID().toString());
        DataHandler.saveJersey(jersey);

        return UserRole.createResponse(200, "");
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
        int httpStatus = DataHandler.updateJersey(jersey) ? 200 : 404;

        return UserRole.createResponse(httpStatus, "");
    }



    /**
     * deletes a jersey
     *
     * @param jerseyUUID the uuid of the jersey to be deleted
     * @return Response
     */

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteJersey(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String jerseyUUID
    ) {
        int httpStatus = 404;

        int errorcode = DataHandler.deleteJersey(jerseyUUID);
        if (errorcode == 0) httpStatus = 200;
        else if (errorcode == -1) httpStatus = 409;

        return UserRole.createResponse(httpStatus, "");
    }

}