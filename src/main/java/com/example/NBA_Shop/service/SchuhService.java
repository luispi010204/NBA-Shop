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

}
