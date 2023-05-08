package client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import static io.restassured.RestAssured.given;

public class CourierClient extends ApiBase {

    private final static String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    private final static String LOGIN_COURIER_ENDPOINT ="/api/v1/courier/login";
    private final static String COURIER_DELETE_ENDPOINT ="/api/v1/courier/";

    @Step("Create new courier. POST /api/v1/courier")
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then();
    }


    @Step("Login courier. POST /api/v1/courier/login")
    public ValidatableResponse loginCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then();
    }
    @Step("Delete courier with id")
    public void deleteCourier(int id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_DELETE_ENDPOINT + id)
                .then();
    }

}

