package com.example.NBA_Shop.service;

import com.example.NBA_Shop.data.UserData;
import com.example.NBA_Shop.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * provides services for the user
 * <p>
 *
 * @author Luigi Spina
 */

@Path("user")
public class UserService {

    /**
     * login eines Users mit Passwort und Benutzernamen
     *
     * @param username the username
     * @param password the password
     * @return Response-object with the userrole
     */

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        User user = UserData.findUser(username, password);
        String userRole = user.getRole();

        int httpStatus = userRole.equals("guest") ? 401 : 200;

        return UserRole.createResponse(httpStatus, "", userRole);
    }

    /**
     * logout current user
     *
     * @return Response object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {

        return UserRole.createResponse(200, "", "guest");
    }
}
