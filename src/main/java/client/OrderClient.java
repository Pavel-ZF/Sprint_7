package client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static io.restassured.RestAssured.given;
public class OrderClient extends ApiBase {

    private final static String CREATE_ORDER_ENDPOINT = "/api/v1/orders";

    @Step("Create order. POST /api/v1/orders")
    public ValidatableResponse createOrderRequest(Order order){
               return given()
                       .header("Content-type", "application/json")
                       .body(order)
                       .when()
                       .post(CREATE_ORDER_ENDPOINT)
                       .then();

    }
    @Step("Get order list. GET /api/v1/orders")
    public Response getOrdersRequest(){
        Response response =
                given().get(CREATE_ORDER_ENDPOINT);
        return  response;
    }
}


