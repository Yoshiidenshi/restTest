package org.ibs;

import pojos.productPojo;

import static io.restassured.RestAssured.given;

public class Test {

    @org.junit.jupiter.api.Test
    void test1() {

        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users")
                .when()
                .log().all()
                .get("?page=2")
                .then()
                .log().all()
                .extract()
                .jsonPath().getList("data", productPojo.class)
                ;

//         given()
//                .baseUri("https://reqres.in")
//                .when()
//                .log().all()
//                .get("/api/user/2")
//                 .then()
//                 .assertThat()
//                 .statusCode(200)
//                 .body("data.id", equalTo(2))
//         ;

        //Assertions.assertEquals(200, res.getStatusCode());
        //Assertions.assertEquals(2, res.jsonPath().getInt("data.id"));
        //System.out.println(res.getStatusCode());
    }
}
