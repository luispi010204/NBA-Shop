package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;
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
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
     * @param schuh
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSchuh(
            @Valid @BeanParam Schuh schuh
    ) {
        int httpStatus = 200;
        schuh.setSchuhUUID(UUID.randomUUID().toString());
        DataHandler.saveSchuh(schuh);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the shoes
     * @param schuh
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSchuh(
            @Valid @BeanParam Schuh schuh
    ) {
        int httpStatus = 200;

        ;
            if (DataHandler.updateSchuh(schuh)) {
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

    /**
     * deletes schoes
     *
     * @param schuhUUID the uuid of the shoe to be deleted
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSchuh(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String schuhUUID
    ) {
        int httpStatusSchuh;

            int errorcode = DataHandler.deleteSchuh(schuhUUID);
            if (errorcode == 0) httpStatusSchuh = 200;
            else if (errorcode == -1) httpStatusSchuh = 409;
            else httpStatusSchuh = 404;


        Response response = Response
                .status(httpStatusSchuh)
                .entity("")
                .build();
        return response;
    }


}
