package com.example.lab4back1.controllers;

import com.example.lab4back1.beans.JsonParserBean;
import com.example.lab4back1.beans.SessionTokenBean;
import com.example.lab4back1.beans.UserBean;
import com.example.lab4back1.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/signin")
public class SignInController {
    @EJB
    private UserBean userBean;

    @EJB
    private SessionTokenBean tokenBean;

    @EJB
    private JsonParserBean jsonParserBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authorize(String data) throws JsonProcessingException {
        User user = jsonParserBean.getUserFromJson(data);
        if (userBean.isRegistered(user.getUsername(), user.getPassword())) {
            String token = tokenBean.generateTokenForUser(user.getUsername());
            return makeResponse(Response.ok().entity(jsonParserBean.getJsonResponseBody(user.getUsername(), token, true)));
        }
        else
            return makeResponse(Response.status(403).entity(jsonParserBean.getJsonResponseBody(user.getUsername(), "bad security", false)));
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

