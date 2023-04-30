import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class CreateCourierTest {
    Courier courier = new Courier("TestCourier07", "TestCourier07", "FirstName07");
    CourierClient courierClient = new CourierClient();
    @Before
    public void setUp(){
        courierClient.setUp();
    }
    @Test
    @DisplayName("Check create new courier (201)")
    @Description("Test success create courier")
    public void checkCreateCourier(){
        courierClient.setCourier(courier);
        courierClient.createCourier()
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
    }
    @Test
    @DisplayName("Check create new courier without firstName (201)")
    @Description("Test success create courier without firstName")
    public void checkCreateCourierWithoutName() {
        courierClient.setCourier(new Courier("TestCourier07", "TestCourier07", ""));
        courierClient.createCourier()
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
    }
    @Test
    @DisplayName("Check create duplicate courier (409)")
    @Description("Creating a duplicate courier is not possible")
    public void checkCreateDuplicateCourier(){
        courierClient.setCourier(courier);
        courierClient.createCourier();
        courierClient.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }
    @Test
    @DisplayName("Check create new courier without login (400)")
    @Description("Creating a courier without a login is not possible")
    public void checkCreateCourierWithoutLogin(){
        courierClient.setCourier(new Courier("","TestCourier07","TestCourier07"));
        courierClient.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check create new courier without password (400)")
    @Description("Creating a courier without a password is not possible")
    public void checkCreateCourierWithoutPassword(){
        courierClient.setCourier(new Courier("TestCourier07","","TestCourier07"));
        courierClient.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void cleanUp(){
        courierClient.deleteCourier();
    }
}

