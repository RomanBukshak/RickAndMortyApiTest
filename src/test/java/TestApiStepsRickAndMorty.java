import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;
import static utils.Configuration.getConfigurationValue;

public class TestApiStepsRickAndMorty {
    public static String mortySmithID;
    public static String mortySmithLocation;
    public static int mortySmithLastEpisode;
    public static String mortySmithSpecies;
    public static int lastEpisodeID;
    public static String lastEpisodeName;
    public static int lastCharacterInLastEpisodeID;
    public static String lastCharacterInLastEpisodeName;
    public static String lastCharacterInLastEpisodeSpecies;
    public static String lastCharacterInLastEpisodeLocation;

    public static void findInfoAboutMortySmith() {
        Response findInfoAboutMortySmith = given()
                .baseUri(getConfigurationValue("baseUriRickAndMorty"))
                .contentType(ContentType.JSON)
                .when().get("character/?name=Morty Smith&status=alive")
                .then()
                .assertThat()
                .statusCode(200)
                .and().body("results.id", notNullValue())
                .extract().response();
        JSONObject mortySmith = (JSONObject) new JSONObject(findInfoAboutMortySmith.getBody().asString()).getJSONArray("results").get(0);
        mortySmithID = mortySmith.get("id").toString();
        System.out.println("Morty Smith ID: " + mortySmithID);
        mortySmithLocation = mortySmith.getJSONObject("location").get("name").toString();
        System.out.println("Morty Smith location: " + mortySmithLocation);
        mortySmithSpecies = mortySmith.get("species").toString();
        System.out.println("Morty Smith species: " + mortySmithSpecies);
        int mortySmithNumberLastEpisode = mortySmith.getJSONArray("episode").length()-1;
        mortySmithLastEpisode = Integer.parseInt(mortySmith.getJSONArray("episode").get(mortySmithNumberLastEpisode).toString().replaceAll("[^0-9]",""));
        System.out.println("Morty Smith last episode: " + mortySmithLastEpisode);
    }

    public static void findLastEpisode() {
        Response findLastEpisode = given()
                .baseUri(getConfigurationValue("baseUriRickAndMorty"))
                .contentType(ContentType.JSON)
                .when().get("episode")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        lastEpisodeID = (int) new JSONObject(findLastEpisode.getBody().asString()).getJSONObject("info").get("count");
    }

    public static void findLastCharacterIDInLastEpisode() {
        Response findLastCharacterIDInLastEpisode = given()
                .baseUri(getConfigurationValue("baseUriRickAndMorty"))
                .contentType(ContentType.JSON)
                .when().get("episode/" + lastEpisodeID)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        lastEpisodeName = new JSONObject(findLastCharacterIDInLastEpisode.getBody().asString()).get("name").toString();
        System.out.println("Last episode ID: " + lastEpisodeID);
        System.out.println("Name last episode: " + lastEpisodeName);
        JSONArray characterInLastEpisode = new JSONObject(findLastCharacterIDInLastEpisode.getBody().asString()).getJSONArray("characters");
        int lastCharacterInLastEpisodeNumber = characterInLastEpisode.length()-1;
        lastCharacterInLastEpisodeID = Integer.parseInt(characterInLastEpisode.get(lastCharacterInLastEpisodeNumber).toString().replaceAll("[^0-9]",""));
    }

    public static void infoLastCharacterInLastEpisode() {
        Response infoLastCharacterInLastEpisode = given()
                .baseUri(getConfigurationValue("baseUriRickAndMorty"))
                .contentType(ContentType.JSON)
                .when().get("character/" + lastCharacterInLastEpisodeID)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        lastCharacterInLastEpisodeName = new JSONObject(infoLastCharacterInLastEpisode.getBody().asString()).get("name").toString();
        System.out.println("Last character in last episode ID: " + lastCharacterInLastEpisodeID);
        System.out.println("Name last character in last episode: " + lastCharacterInLastEpisodeName);
        lastCharacterInLastEpisodeSpecies = new JSONObject(infoLastCharacterInLastEpisode.getBody().asString()).get("species").toString();
        System.out.println(lastCharacterInLastEpisodeName + " species: " + lastCharacterInLastEpisodeSpecies);
        lastCharacterInLastEpisodeLocation =  new JSONObject(infoLastCharacterInLastEpisode.getBody().asString()).getJSONObject("location").get("name").toString();
        System.out.println(lastCharacterInLastEpisodeName + " location: " + lastCharacterInLastEpisodeLocation);
    }

    public static void assertSpecies() {
        String assertSpecies = mortySmithSpecies.equals(lastCharacterInLastEpisodeSpecies) ? "Персонажи одной расы!" : "Персонажи разной расы!";
        System.out.println(assertSpecies);
    }

    public static void assertLocation() {
        String assertLocation = mortySmithLocation.equals(lastCharacterInLastEpisodeLocation) ? "Персонажи находятся в одном месте!" : "Персонажи находятся в разных местах!";
        System.out.println(assertLocation);
    }

}
