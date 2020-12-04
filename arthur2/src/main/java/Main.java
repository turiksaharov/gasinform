import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Date;
import java.util.List;111

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) {

        String appidToken = "9c78eac14ed7631d20480a22e35eeb5a";

        RequestSpecification requestSpec = given()
                .filter(new RequestLoggingFilter())     // выводит содержимое запроса
                .filter(new ResponseLoggingFilter())    // выводит содержимое ответа
                .baseUri("http://api.openweathermap.org/data/2.5");

        Response resp = requestSpec.queryParam("lat", "59.939095")
                .queryParam("lon", "30.315868")
                .queryParam("units", "metric")
                .queryParam("lang", "ru")
                .queryParam("appid", appidToken)
                .get("/weather")
                .then()
                .statusCode(200)
                .extract()
                .response()
        ;

        Float mainTemp = (Float) resp.jsonPath().getMap("main").get("temp");
        String actualTime = new Date().toString();
        Float feelTemp = (Float) resp.jsonPath().getMap("main").get("feels_like");
        Integer humidity = (Integer) resp.jsonPath().getMap("main").get("humidity");
        Integer pressure = (Integer) resp.jsonPath().getMap("main").get("pressure");
        Integer wind = (Integer) resp.jsonPath().getMap("wind").get("speed");
        List<String> description = resp.jsonPath().getList("weather");

        System.out.println("\nТемпратура воздуха: " + mainTemp);
        System.out.println("Темпратура комфорта: " + feelTemp);
        System.out.println("Текущее время: " + actualTime);
        System.out.println("Влажность: " + humidity);
        System.out.println("Давление: " + pressure);
        System.out.println("Ветер: " + wind + "м/с");
        System.out.println("Описание погоды: " + description);
    }
}
