import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class CreateCourierTest {
    CourierClient courierClient = new CourierClient();

    Courier courier;
    private int id;
    @Before
    public void setUp(){
        courierClient.setUp();
        courier = ((new Courier("TestCourier07","TestCourier07","TestCourier07")));
    }
   @Test
    @DisplayName("Check create new courier (201)")
    @Description("Test success create courier")
    public void checkCreateCourier(){
       ValidatableResponse createResponse = courierClient.createCourier(courier);
       createResponse
               .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
       ValidatableResponse loginResponse = courierClient.loginCourier(courier);
      id = loginResponse.extract().path("id");
    }
    @Test
    @DisplayName("Check create new courier without firstName (201)")
    @Description("Test success create courier without firstName")
    public void checkCreateCourierWithoutName() {
        ValidatableResponse createResponse = courierClient.createCourier((new Courier("TestCourier07","TestCourier07","")));
        createResponse
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
        ValidatableResponse loginResponse = courierClient.loginCourier(courier);
        id = loginResponse.extract().path("id");
    }
    @Test
    @DisplayName("Check create duplicate courier (409)")
    @Description("Creating a duplicate courier is not possible")
    public void checkCreateDuplicateCourier(){
        ValidatableResponse createResponse =  courierClient.createCourier(courier);
        ValidatableResponse createResponseDuplicate =  courierClient.createCourier((new Courier("TestCourier07","TestCourier07","TestCourier07")));
        createResponseDuplicate
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
        ValidatableResponse loginResponse = courierClient.loginCourier(courier);
        id = loginResponse.extract().path("id");
    }
    @Test
    @DisplayName("Check create new courier without login (400)")
    @Description("Creating a courier without a login is not possible")
    public void checkCreateCourierWithoutLogin(){
        ValidatableResponse createResponse = courierClient.createCourier((new Courier("","TestCourier07","TestCourier07")));
        createResponse
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check create new courier without password (400)")
    @Description("Creating a courier without a password is not possible")
    public void checkCreateCourierWithoutPassword(){
        ValidatableResponse createResponse = courierClient.createCourier((new Courier("TestCourier07","","TestCourier07")));
        createResponse
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
  public void cleanUp() {
       if (id != 0) {
           courierClient.deleteCourier(id);
       } }
}

