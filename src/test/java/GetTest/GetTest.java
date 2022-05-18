package GetTest;

import RunTest.RunTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetTest extends idCharacter {
    public static String lastEpisode;
    public static String lastCharacter;
    public static String ourName;
    public static String ourRace;
    public static String ourLocation;
    public static String raceMorty;
    public static String locMorty;
    public static String nameOurC;

    @Step
    public static void morty() {
        Response data1 = given()
                .header("Content-type", "application/json")
                .header("charset", "UTF-8")
                .baseUri("https://rickandmortyapi.com/")
                .when()
                .get("api/character/" + id)
                .then()
                .extract()
                .response();

        JSONObject infoMorty = new JSONObject(data1.getBody().asString());
        JSONArray episodeMorty = infoMorty.getJSONArray("episode");
        int countEpisodes = episodeMorty.length();
        lastEpisode = episodeMorty.getString(countEpisodes - 1);
        String subStr = lastEpisode.substring(40);
        nameOurC = infoMorty.getString("name");
        raceMorty = infoMorty.getString("species");
        locMorty = infoMorty.getJSONObject("location").getString("name");

        System.out.println("Последний эпизод с " + nameOurC + ": " + lastEpisode + "\n" +
                "Номер последнего эпизода: " + subStr + "\n" +
                "Раса " + nameOurC + ": " + raceMorty + "\n" +
                "Местоположение " + nameOurC + ": " + locMorty);
    }

    @Step
    public static void episode() {
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
        lastCharacter = characterEpisode.getString(countCharacter - 1);
    }

    @Step
    public static void character() {
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
        ourName = ourCharacter.getString("name");
        ourRace = ourCharacter.getString("species");
        ourLocation = ourCharacter.getJSONObject("location").getString("name");
    }

    @Step
    public static void result(){
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
