package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
;

/**
 * service for testings
 *
 * NBA-Shop
 *
 * @author Luigi Spina
 */
@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {

        DataHandler.restoreData();

        return Response
                .status(200)
                .entity("Daten wiederhergestellt")
                .build();
    }
}