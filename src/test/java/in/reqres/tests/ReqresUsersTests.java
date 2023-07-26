package in.reqres.tests;

import in.reqres.models.LoginBodyPojoModel;
import in.reqres.models.UnsuccessfulLoginResponseModel;
import in.reqres.models.UpdateUserInfoBodyLombokModel;
import in.reqres.models.UpdateUserInfoBodyPajoModel;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.reqresUsersSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresUsersTests extends TestBase {

    @Test
    void getListOfUsersTest() {

        given()
                .spec(usersSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(listOfUsersSpec);
    }

    @Test
    void unsuccessfulLoginWithPojoModelsTest() {

        LoginBodyPojoModel authData = new LoginBodyPojoModel();
        authData.setEmail("peter@klaven");

        UnsuccessfulLoginResponseModel unsuccessfulResponse = step("Make request", () ->
                given()
                        .spec(userActionsSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(unsuccessfulLoginSpec)
                        .extract().as(UnsuccessfulLoginResponseModel.class));
        step("Check response", () ->
                assertEquals("Missing password", unsuccessfulResponse.getError()));
    }

    @Test
    void updateAllInfoOfTheUserWithLombokModelsTest() {

        UpdateUserInfoBodyLombokModel dataToUpdate = new UpdateUserInfoBodyLombokModel();
        dataToUpdate.setName("morpheus");
        dataToUpdate.setJob("zion resident");
        dataToUpdate.setUpdatedAt("2023-07-22T14:08:15.606Z");

        UpdateUserInfoBodyLombokModel updatedData = step("Make request", () ->
                given()
                        .spec(userActionsSpec)
                        .body(dataToUpdate)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(updateUserInfoSpec)
                        .extract().as(UpdateUserInfoBodyLombokModel.class));
        step("Check response", () -> {
            assertEquals("morpheus", dataToUpdate.getName());
            assertEquals("zion resident", dataToUpdate.getJob());
        });
    }

    @Test
    void updateSomeInfoOfTheUserWithPojoModelsTest() {

        UpdateUserInfoBodyPajoModel dataToUpdate = new UpdateUserInfoBodyPajoModel();
        dataToUpdate.setName("morpheus");
        dataToUpdate.setJob("zion resident");
        dataToUpdate.setUpdatedAt("2023-07-22T14:08:15.606Z");

        UpdateUserInfoBodyPajoModel updatedData = step("Make request", () ->
                given()
                        .spec(userActionsSpec)
                        .body(dataToUpdate)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(updateUserInfoSpec)
                        .extract().as(UpdateUserInfoBodyPajoModel.class));
        step("Check response", () -> {
            assertEquals("morpheus", dataToUpdate.getName());
            assertEquals("zion resident", dataToUpdate.getJob());
        });
    }

    @Test
    void deleteUsersTest() {
        given()
                .spec(usersSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(deleteUserSpec);
    }
}
