package GetTest;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetTest {
    @Test

    public void morty() {
        Response data1 = given()
                .header("Content-type", "application/json")
                .header("charset", "UTF-8")
                .baseUri("https://rickandmortyapi.com/")
                .when()
                .get("api/character/2")
                .then()
                .extract()
                .response();

        JSONObject infoMorty = new JSONObject(data1.getBody().asString());
        JSONArray episodeMorty = infoMorty.getJSONArray("episode");
        int countEpisodes = episodeMorty.length();
        String lastEpisode = episodeMorty.getString(countEpisodes - 1);
        String raceMorty = infoMorty.getString("species");
        String locMorty = infoMorty.getJSONObject("location").getString("name");

        System.out.println("Последний эпизод с Морти: " + lastEpisode + "\n" +
                           "Номер последнего эпизода: " + countEpisodes + "\n" +
                           "Раса Морти: " + raceMorty + "\n" +
                           "Местоположение Морти: " + locMorty);

        Response data2 = given()
                .header("Content-type", "application/json")
                .header("charset", "UTF-8")
                .baseUri("https://rickandmortyapi.com/")
                .when()
                .get(lastEpisode)
                .then()
                .extract()
                .response();

        JSONObject lastEpisodeMorty = new JSONObject(data2.getBody().asString());
        JSONArray characterEpisode = lastEpisodeMorty.getJSONArray("characters");
        int countCharacter = characterEpisode.length();
        String lastCharacter = characterEpisode.getString(countCharacter - 1);

        Response data3 = given()
                .header("Content-type", "application/json")
                .header("charset", "UTF-8")
                .baseUri("https://rickandmortyapi.com/")
                .when()
                .get(lastCharacter)
                .then()
                .extract()
                .response();

        JSONObject ourCharacter = new JSONObject(data3.getBody().asString());
        String ourName = ourCharacter.getString("name");
        String ourRace = ourCharacter.getString("species");
        String ourLocation = ourCharacter.getJSONObject("location").getString("name");

        System.out.println("\nПоследний персонаж в эпизоде: " + lastCharacter + "\n" +
                           "Его имя: " + ourName + "\n" +
                           "Его раса: " + ourRace + "\n" +
                           "Его локация: " + ourLocation + "\n");

        if(raceMorty.equals(ourRace)){ System.out.println("Персонажи одной расы"); }
        else { System.out.println("Раса персонажей разная");}
        System.out.println();
        if(locMorty.equals(ourLocation)){ System.out.println("Местоположение персонажей одинаково"); }
        else { System.out.println("Местоположение персонажей разное");}
    }
}
