package com.example.NBA_Shop.service;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * service for testings
 *
 * NBA-Shop
 *
 * @author Luigi Spina
 */

public class UserRole {

    /**
     * checks if a UserRole is valid
     *
     * @param userRole
     * @return
     */
    public static boolean isInvalid(String userRole) {
        boolean isValid = userRole.equals("user") || userRole.equals("admin");
        return !isValid;
    }

    /**
     * creates a response for invalid userRole
     *
     * @param userRole
     * @return
     */
    public static Response createInvalidUserResponse(String userRole) {
        return createResponse(403, "invalid user role", userRole);
    }

    /**
     * Ceates a basic response as httpStatus
     *
     * @param httpStatus
     * @param entity
     * @return
     */
    public static Response createResponse(int httpStatus, Object entity) {
        return Response
                .status(httpStatus)
                .entity(entity)
                .build();
    }

    /**
     * Creates a response with httpStatus and Cookie
     *
     * @param httpStatus
     * @param entity
     * @param userRole
     * @return
     */
    public static Response createResponse(int httpStatus, Object entity, String userRole) {
        NewCookie cookie = renewCookie(userRole);
        return Response
                .status(httpStatus)
                .entity(entity)
                .cookie(cookie)
                .build();
    }

    /**
     * updates the cookie
     *
     * @param userRole
     * @return
     */
    private static NewCookie renewCookie(String userRole) {
        return new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
    }
}
