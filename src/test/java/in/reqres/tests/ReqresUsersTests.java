package in.reqres.tests;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.ReqresUsersSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresUsersTests {

    @Test
    void getListOfUsersTest() {

        ListOfUsersLombokModel listOfUsers = step("Make request", () ->
            given()
                .spec(usersSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(listOfUsersResponseSpec200)
                .extract().as(ListOfUsersLombokModel.class));
        step("Check response", () -> {
            assertEquals(12, listOfUsers.getTotal());
            assertEquals(2, listOfUsers.getTotal_pages());
        });
    }

    @Test
    void unsuccessfulLoginWithLombokModelTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("peter@klaven");

        UnsuccessfulLoginLombokModel unsuccessfulResponse = step("Make request", () ->
            given()
                        .spec(userActionsSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(unsuccessfulLoginResponseSpec400)
                        .extract().as(UnsuccessfulLoginLombokModel.class));
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
                        .spec(updateUserInfoResponseSpec200)
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
                        .spec(updateUserInfoResponseSpec200)
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
                .spec(deleteUserResponseSpec204);
    }
}
