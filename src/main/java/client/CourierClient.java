package client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import static io.restassured.RestAssured.given;

public class CourierClient extends ApiBase {
    private Courier courier;
    private final static String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    private final static String LOGIN_COURIER_ENDPOINT ="/api/v1/courier/login";

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Create new courier. POST /api/v1/courier")
    public Response createCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (CREATE_COURIER_ENDPOINT);
        return response;
    }

    @Step("Login courier. POST /api/v1/courier/login")
    public Response loginCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (LOGIN_COURIER_ENDPOINT);
        return response;
    }
    @Step("Delete courier DELETE /api/v1/courier/{id}")
    public void deleteCourier(){
        Integer id = given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (LOGIN_COURIER_ENDPOINT)
                        .then().extract().body().path("id");
        if (id != null) {given().delete (CREATE_COURIER_ENDPOINT + "/{id}", id.toString());}
    }

}

