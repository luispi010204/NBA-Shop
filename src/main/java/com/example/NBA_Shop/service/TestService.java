package com.example.NBA_Shop.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("backupJSON")));

            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("spielerJSON"));
            fileOutputStream.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response
                .status(200)
                .entity("Daten wiederhergestellt")
                .build();
    }
}