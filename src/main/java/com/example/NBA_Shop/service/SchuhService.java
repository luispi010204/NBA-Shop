package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
import com.example.NBA_Shop.model.Schuh;


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

@Path("schuh")
public class SchuhService {

    /**
     * produces a map of every shoe
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listShoes(
    ) {
        Map<String, Schuh> schuhMap = DataHandler.getSchuhMap();
        Response response = Response
                .status(200)
                .entity(schuhMap)
                .build();
        return response;
    }

    /**
     * reads a single pair of shoes identified by the uuid
     *
     * @param schuhUUID the UUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readShoe(
            @QueryParam("uuid") String schuhUUID
    ) {
        Schuh schuh = null;
        int httpStatus;

        try {
            UUID.fromString(schuhUUID);
            schuh = DataHandler.readSchuh(schuhUUID);
            if (schuh.getSchuhName() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(schuh)
                .build();
        return response;
    }

    /**
     * creates a new shoe without player
     * @param schuhName
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSchuh(
            @FormParam("schuh") String schuhName
    ) {
        int httpStatus = 200;
        Schuh schuh = new Schuh();
        schuh.setSchuhUUID(UUID.randomUUID().toString());
        schuh.setSchuhName(schuhName);
        DataHandler.saveSchuh(schuh);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the shoes
     * @param schuhUUID  the uuid of the shoes
     * @param schuhName  the new Name of the model
     * @param preis  price of the shoe
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSchuh(
            @FormParam("schuhUUID") String schuhUUID,
            @FormParam("schuhName") String schuhName,
            @FormParam("preis") double preis
    ) {
        int httpStatus = 200;
        Schuh schuh = new Schuh();
        try {
            UUID.fromString(schuhUUID);
            schuh.setSchuhUUID(schuhUUID);
            schuh.setSchuhName(schuhName);
            if (DataHandler.updateSchuh(schuh)) {
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

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSchuh(
            @QueryParam("uuid") String schuhUUID
    ) {
        int httpStatus;
        try {
            UUID.fromString(schuhUUID);
            int errorcode = DataHandler.deleteSchuh(schuhUUID);
            if (errorcode == 0) httpStatus = 200;
            else if (errorcode == -1) httpStatus = 409;
            else httpStatus = 404;
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}
