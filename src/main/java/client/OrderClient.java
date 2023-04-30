package client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;
public class OrderClient extends ApiBase {

    private Order order;
    private final static String CREATE_ORDER_ENDPOINT = "/api/v1/orders";

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Create order. POST /api/v1/orders")
    public Response createOrderRequest(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post(CREATE_ORDER_ENDPOINT);
        return response;
    }
    @Step("Get order list. GET /api/v1/orders")
    public Response getOrdersRequest(){
        Response response =
                given().get(CREATE_ORDER_ENDPOINT);
        return  response;
    }
}


