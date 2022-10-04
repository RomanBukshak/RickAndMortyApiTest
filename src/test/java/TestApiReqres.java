import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class TestApiReqres {
    public static void createUser() throws IOException {
        JSONObject body = new JSONObject(new String(Files.readAllBytes(Paths.get("src/json/reqres.json"))));
        Response createUser = given()
                .contentType(ContentType.JSON)
                .body(body.put("name", "Tomato"))
                .body(body.put("job", "Eat maket"))
                .body(body.toString())
                .baseUri("https://reqres.in/")
                .when()
                .post("api/users")
                .then()
                .statusCode(201)
                .extract().response();
        JSONObject createNewUser = new JSONObject(createUser.getBody().asString());
        Assertions.assertEquals(createNewUser.getString("name"), body.getString("name"), "Имена не совпадают!");
        Assertions.assertEquals(createNewUser.getString("job"), body.getString("job"), "Работы не совпадают!");
        Assertions.assertNotNull(createNewUser.getString("id"),"Id пустое!");
        Assertions.assertNotNull(createNewUser.getString("createdAt"),"Дата создания пустая!");
    }
}
