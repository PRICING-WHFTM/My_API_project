package com.company.Tests;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.*;

public class PUT_JSONObject {
    /**
     * JSONObject is used to create data as Map and use as payload
     */

    @Test
    public void updateUser() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("first_name", new Faker().name().firstName());
        jsonObject.put("last_name", new Faker().name().lastName());
        System.out.println(jsonObject.toJSONString());
        baseURI = "https://reqres.in/";
        basePath = "/api/users/3";
        Response res =
                given()
                        .accept(ContentType.JSON)
                        .body(jsonObject)
                        .when()
                        .put()
                        .then().assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().response();
        res.prettyPrint();
        JsonPath js = res.jsonPath();
        //current day
        String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Assert.assertNotNull(js.getString("updatedAt"));
//        Assert.assertTrue(updatedMessage.contains(currentDay)); //1 day ahead

    }


}

