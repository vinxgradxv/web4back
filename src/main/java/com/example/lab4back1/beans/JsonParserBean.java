package com.example.lab4back1.beans;

import com.example.lab4back1.model.Hit;
import com.example.lab4back1.model.SessionEntity;
import com.example.lab4back1.model.User;
import com.example.lab4back1.util.UserResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.Singleton;

import java.util.ArrayList;

@Singleton
public class JsonParserBean {

    private ObjectMapper mapper = new ObjectMapper();
    public String toJSON(ArrayList<Hit> collection) throws JsonProcessingException {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        if (collection.size() != 0) {
            for (int i = 0; i < collection.size(); i++) {
                if (i != collection.size() - 1) {
                    json.append(toJSON(collection.get(i))).append(",\n");
                } else {
                    json.append(toJSON(collection.get(i))).append("\n");
                }
            }
        }
        json.append("]");
        return json.toString();
    }

    public String toJSON(Hit shot) throws JsonProcessingException {
        return mapper.writeValueAsString(shot);
    }

    public User getUserFromJson(String json) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(json);
        User user = new User();
        user.setUsername(rootNode.get("login").asText());
        user.setPassword(rootNode.get("password").asText());
        return user;
    }

    public String getJsonResponseBody(String login, String password, boolean status) throws JsonProcessingException {
        UserResponseBody userResponseBody = new UserResponseBody(login, password, status);
        return mapper.writeValueAsString(userResponseBody);

    }

    public String[] getHitFieldArrayFromJson (String json) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(json);
        String[] resultArray = new String[5];
        resultArray[0] = rootNode.get("username").asText();
        resultArray[1] = rootNode.get("userPassword").asText();
        resultArray[2] = rootNode.get("x").asText();
        resultArray[3] = rootNode.get("y").asText();
        resultArray[4] = rootNode.get("r").asText();
        return resultArray;
    }

    public SessionEntity getSessionEntityFromJson (String json) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(json);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUsername(rootNode.get("username").asText());
        sessionEntity.setToken(rootNode.get("userPassword").asText());
        return sessionEntity;
    }

    public Hit getHitFromJson (String json) throws JsonProcessingException, NumberFormatException {
        JsonNode rootNode = mapper.readTree(json);
        Hit hit = new Hit();
        hit.setX(Double.parseDouble(rootNode.get("x").asText()));
        hit.setY(Double.parseDouble(rootNode.get("y").asText()));
        hit.setR(Double.parseDouble(rootNode.get("r").asText()));
        return hit;
    }
}
