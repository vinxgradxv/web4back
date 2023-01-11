package com.example.lab4back1.controllers;

import com.example.lab4back1.beans.HitBean;
import com.example.lab4back1.beans.JsonParserBean;
import com.example.lab4back1.beans.SessionTokenBean;

import com.example.lab4back1.model.Hit;
import com.example.lab4back1.model.SessionEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;




@Path("/hits")
public class HitController {
    @EJB
    private HitBean hitBean;
    @EJB
    private SessionTokenBean tokenBean;

    @EJB
    private JsonParserBean jsonParserBean;


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHit(String data) throws JsonProcessingException {
        SessionEntity sessionEntity = jsonParserBean.getSessionEntityFromJson(data);
        if (tokenBean.getTokenForUser(sessionEntity.getUsername()) != null && tokenBean.getTokenForUser(sessionEntity.getUsername()).equals(sessionEntity.getToken())) {
            Hit hit;
            try {
                hit = jsonParserBean.getHitFromJson(data);
            }catch (NumberFormatException e){
                return makeResponse(Response
                        .serverError()
                        .entity("Error: x, y, r have to be a number")
                );
            }
            hitBean.addHit(hit, sessionEntity.getUsername());
            return makeResponse(Response
                    .ok()
                    .entity(jsonParserBean.toJSON(hitBean.getHits(sessionEntity.getUsername())))
            );
        } else {
            return makeResponse(Response
                    .status(403)
                    .entity("User is not confirmed")
            );
        }
    }

    @GET
    @Path("/get")
    public Response getHits(@QueryParam("username") String username, @QueryParam("token") String token) throws JsonProcessingException {

        if (tokenBean.getTokenForUser(username) != null && tokenBean.getTokenForUser(username).equals(token)) {
            return makeResponse(Response
                    .ok()
                    .entity(jsonParserBean.toJSON(hitBean.getHits(username)))
            );
        } else {
            return makeResponse(Response
                    .status(403)
                    .entity("User is not confirmed")
            );
        }
    }

    @DELETE
    @Path("/delete")
    public Response deleteHits(@QueryParam("username") String username, @QueryParam("token") String token) throws JsonProcessingException {
        if (tokenBean.getTokenForUser(username) != null && tokenBean.getTokenForUser(username).equals(token)) {
            hitBean.deleteHits(username);
            return makeResponse(Response
                    .ok()
                    .entity(jsonParserBean.toJSON(hitBean.getHits(username))
            ));
        } else {
            return makeResponse(Response
                    .ok()
                    .entity(jsonParserBean.toJSON(hitBean.getHits(username))
            ));
        }
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
    @Path("/get")
    public Response optionsGet() {
        return Response.ok().entity("").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @OPTIONS
    @Path("/add")
    public Response optionsAdd() {
        return Response.ok().entity("").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @OPTIONS
    @Path("/delete")
    public Response optionsDelete() {
        return Response.ok().entity("").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}