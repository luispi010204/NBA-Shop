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

        return UserRole.createResponse(200, schuhMap);
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
        int httpStatus = 404;

        Schuh schuh = DataHandler.readSchuh(schuhUUID);
        if (schuh.getSchuhName() != null) {
            httpStatus = 200;
        }
        return UserRole.createResponse(httpStatus, schuh);
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
        schuh.setSchuhUUID(UUID.randomUUID().toString());
        DataHandler.saveSchuh(schuh);

        return UserRole.createResponse(200, "");
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
        int httpStatus = DataHandler.updateSchuh(schuh) ? 200 : 404;

        return UserRole.createResponse(httpStatus, "");
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
        int httpStatus = 404;

        int errorcode = DataHandler.deleteSchuh(schuhUUID);
        if (errorcode == 0) httpStatus = 200;
        else if (errorcode == -1) httpStatus = 409;

        return UserRole.createResponse(httpStatus, "");
    }


}
