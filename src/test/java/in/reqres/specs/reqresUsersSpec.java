package in.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.*;
import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class reqresUsersSpec {
    public static RequestSpecification usersSpec = with()
            .log().uri()
            .log().method()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static RequestSpecification userActionsSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification listOfUsersSpec = new ResponseSpecBuilder()
        .log(STATUS)
        .log(BODY)
        .expectStatusCode(200)
        .expectBody(matchesJsonSchemaInClasspath("schemas/list_of_users_responce.json"))
        .build();

    public static ResponseSpecification unsuccessfulLoginSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification updateUserInfoSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification deleteUserSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204)
            .build();
}
