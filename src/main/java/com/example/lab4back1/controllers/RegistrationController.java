package com.example.lab4back1.controllers;

import com.example.lab4back1.beans.JsonParserBean;
import com.example.lab4back1.beans.SessionTokenBean;
import com.example.lab4back1.beans.UserBean;
import com.example.lab4back1.model.User;
import com.google.common.hash.Hashing;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Path("/signup")
public class RegistrationController {

    @EJB
    private UserBean userBean;

    @EJB
    private SessionTokenBean tokenBean;

    @EJB
    private JsonParserBean jsonParserBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registration(String data) throws IOException {
        User user = jsonParserBean.getUserFromJson(data);
        if (userBean.isUsernameExists(user.getUsername())) {
            return makeResponse(Response.status(403).entity(jsonParserBean.getJsonResponseBody(user.getUsername(), "bad security", false)));
        }
        String token = tokenBean.generateTokenForUser(user.getUsername());
        user.setPassword(Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString());
        userBean.addUser(user);
        return makeResponse(Response.ok().entity(jsonParserBean.getJsonResponseBody(user.getUsername(), token, true)));
    }

    public Response makeResponse(Response.ResponseBuilder x){
        return x.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().entity("").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}
