package homework;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import org.ibs.Specifications;
import org.junit.jupiter.api.*;
import pojos.productPojo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;

public class restTest {

    private static final CookieFilter cookiefilter = new CookieFilter();

    @BeforeAll
    static void prep() {
        Specifications.installSpec(
                Specifications.requestSpecification("http://localhost:8080"),
                Specifications.responseSpecification(200)
        );
    }

    @Test
    void addExoticFruit() {
        String data = "{\"name\": \"Манго\", \"type\": \"FRUIT\", \"exotic\": true}";

        given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Test
    void addNonExoticFruit() {
        String data = "{\"name\": \"Груша\", \"type\": \"FRUIT\", \"exotic\": false}";

        given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Test
    void addNonExoticVegetable() {
        String data = "{\"name\": \"Огурчик\", \"type\": \"VEGETABLE\", \"exotic\": false}";

        given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Test
    void addExoticVegetable() {
        String data = "{\"name\": \"Пекинская капуста\", \"type\": \"VEGETABLE\", \"exotic\": true}";

        given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @AfterAll

    static void testEnd(){
        List<productPojo> products = given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .when()
                .contentType(ContentType.JSON)
                .get("")
                .then()
                .log().all()
                .extract()
                .jsonPath().getList("", productPojo.class);

        Assertions.assertTrue(products.stream().anyMatch(
                product -> "Манго".equals(product.getName()) && "FRUIT".equals(product.getType())));
        Assertions.assertTrue(products.stream().anyMatch(
                product -> "Груша".equals(product.getName()) && "FRUIT".equals(product.getType())));
        Assertions.assertTrue(products.stream().anyMatch(
                product -> "Огурчик".equals(product.getName()) && "VEGETABLE".equals(product.getType())));
        Assertions.assertTrue(products.stream().anyMatch(
                product -> "Пекинская капуста".equals(product.getName())
                        && "VEGETABLE".equals(product.getType())));

        given()
                .filter(cookiefilter)
                .basePath("/api/data/reset")
                .when()
                .post();

        reset();

        given()
                .filter(cookiefilter)
                .basePath("/api/food")
                .when()
                .contentType(ContentType.JSON)
                .get("")
                .then()
                .log().all()
                .extract()
                .jsonPath().getList("", productPojo.class);
    }
}
