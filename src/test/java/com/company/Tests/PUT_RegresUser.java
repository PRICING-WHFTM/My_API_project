package com.company.Tests;

import com.company.Pojos.RegresUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import com.company.Pojos.Employee;

import com.company.Utils.JsonUtil;
import org.testng.annotations.Test;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PUT_RegresUser {


    @Test
    public void updateUser() throws IOException {
        RegresUser regresUser = new RegresUser();
        regresUser.setName("Anar");
        regresUser.setJob("Engineer");
        baseURI = "https://reqres.in/";
        String bodyJson = JsonUtil.convertJavaToJson(regresUser);
        System.out.println("body " + bodyJson);

        Response res =
                given().accept(ContentType.JSON)
                        .body(bodyJson)
                        .when().put("/api/users/3")
                        .then().assertThat().statusCode(200).contentType(ContentType.JSON)
                        .extract().response();
        res.prettyPrint();
        JsonPath js = res.jsonPath();
        String updatedMessage = js.getString("updatedAt");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDay = simpleDateFormat.format(date);
        System.out.println(currentDay);
        Assert.assertTrue(updatedMessage.contains(currentDay));

    }


}

