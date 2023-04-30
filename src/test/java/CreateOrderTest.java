import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp(){
        orderClient.setUp();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new Order("ЯН","ЛИ","ЛЕНИНА, д.19, кв.17","Арбатская","+79999999999",1,"2023-04-30","ТЕСТОВЫЙ КОММЕНТАРИЙ",new String[]{"BLACK"})},
                {new Order("SU","LI","Lenin str, b.19, a.17","Белорусская","89999999999",2,"2023-05-01","TEST COMMENT",new String[]{"GREY"})},
                {new Order("Абдурахмангаджи","Кеиханаикукауакахихулихеекахаунаеле","г. Москва, ул. Центральный проезд  Ленина, д. 049","ВДНХ","89000000000",3,"2023-05-02","4815162342",new String[]{"BLACK","GREY"})},
                {new Order("иван","иванов","Красная площадь","Зорге","89876543210",4,"2023-05-03","Привезете вовремя скину на чай 500 рублей",new String[]{})}
        };
    }

    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Check create a new order")
    @Description("An order with different valid parameters is created successfully")
    public void checkCreateOrder(){
        orderClient.setOrder(order);
        orderClient.createOrderRequest()
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}

