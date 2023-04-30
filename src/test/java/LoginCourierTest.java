import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest{
    CourierClient courierClient = new CourierClient();
    Courier courier = new Courier ("TestCourier07","TestCourier07");

    @Before
    public void setUp(){
        courierClient.setUp();
    }

    @Test
    @DisplayName("Check successful authorization")
    @Description("Authorization with correct data is successful")
    public void checkLoginCourier(){
        courierClient.setCourier(courier);
        courierClient.createCourier();
        courierClient.loginCourier()
                .then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check authorization without login (400)")
    @Description("Authorization without login isn't possible")
    public void checkLoginWithoutLogin(){
        courierClient.setCourier(new Courier("","TestCourier07"));
        courierClient.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check authorization without password (400)")
    @Description("Authorization without password isn't possible")
    public void checkLoginWithoutPassword(){
        courierClient.setCourier(new Courier("TestCourier07",""));
        courierClient.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check authorization non-existent courier (404)")
    @Description("Authorization non-existent courier isn't possible")
    public void checkLoginNonExisting(){
        courierClient.setCourier(courier);
        courierClient.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check authorization with incorrect login (404)")
    @Description("Authorization with incorrect login isn't possible")
    public void checkLoginIncorrectLogin() {
        courierClient.setCourier(courier);
        courierClient.createCourier();
        courierClient.setCourier(new Courier("TestCourier071","TestCourier07"));
        courierClient.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierClient.setCourier(courier);
    }

    @Test
    @DisplayName("Check authorization with incorrect password 404)")
    @Description("Authorization with incorrect password isn't possible")
    public void checkLoginIncorrectPassword() {
        courierClient.setCourier(courier);
        courierClient.createCourier();
        courierClient.setCourier(new Courier("TestCourier07","TestCourier071"));
        courierClient.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierClient.setCourier(courier);
    }
    @After
    public void cleanUp(){
        courierClient.deleteCourier();
    }

}
