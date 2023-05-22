package client;
import io.restassured.RestAssured;

public class ApiBase {

    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
}

