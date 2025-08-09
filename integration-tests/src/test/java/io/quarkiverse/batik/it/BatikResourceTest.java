package io.quarkiverse.batik.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BatikResourceTest {

    @Test
    public void testAddWatermarkEndpoint() {
        given()
                .when().get("/batik")
                .then()
                .statusCode(200)
                .body(is("Batik added SVG Watermark"));
    }
}