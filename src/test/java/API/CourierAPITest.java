package API;

import POJO.CourierCredentials;
import POJO.CourierPOJO;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourierAPITest {
    private CourierPOJO courier;
    private CourierAPI courierAPI;
    private int createdCourierId;
    private boolean created;
    private Response response;

    @Before
    public void setup() {
        courierAPI = new CourierAPI();
    }

    @Test
    @DisplayName("Проверка создания курьера и авторизации") // имя теста
    public void createAndLoginCourier() {
        courier = CourierPOJO.getRandom();
        response = courierAPI.sendPostRequestCreateCourier(courier);
        created = courierAPI.courierCreatedStatus201(response);
        Assert.assertTrue("Курьер не был создан", created);
        CourierCredentials credentials = CourierCredentials.from(courier);
        createdCourierId = courierAPI.courierLogin(credentials);
        Assert.assertNotEquals("Авторизация не прошла, ожидается ответ с id больше 0", 0, createdCourierId);
    }

    @Test
    @DisplayName("Проверка ошибки при создании курьера без данных") // имя теста
    public void createCourierWithoutCredBadRequest400() {
        String expected = "Недостаточно данных для создания учетной записи";
        courier = new CourierPOJO();
        response = courierAPI.sendPostRequestCreateCourier(courier);
        String responseMessage = courierAPI.courierNotCreatedStatus400(response);
        Assert.assertEquals("Ожидается сообщение об ошибке", expected, responseMessage);
    }

    @Test
    @DisplayName("Проверка ошибки при создании курьера с уже существующим логином") // имя теста
    public void createCourierCantBeTheSameLogin409() {
        String expected = "Этот логин уже используется. Попробуйте другой.";
        courier = new CourierPOJO("l0g1n", "l0g1n", null);
        //создание курьера
        response = courierAPI.sendPostRequestCreateCourier(courier);
        //проверка на то, что курьер создан
        created = courierAPI.courierCreatedStatus201(response);
        CourierCredentials credentials = CourierCredentials.from(courier);
        //создание курьера
        response = courierAPI.sendPostRequestCreateCourier(courier);
        //проврка сообщения о конфликет
        String responseMessage = courierAPI.courierConflict409(response);
        createdCourierId = courierAPI.courierLogin(credentials);
        Assert.assertEquals("Ожидается сообщение о том, что УЗ с таким логином уже есть", expected, responseMessage);
    }

//    @Test
//    public void loginCourierWithoutPasswordBadRequest400(){
//        String expected = "Недостаточно данных для входа";
//        courier = new CourierPOJO("l0g1n",null,null);
//        CourierCredentials credentials = CourierCredentials.from(courier);
//        String responseMessage = courierAPI.courierLoginNotEnoughDataStatus400(credentials);
//        Assert.assertEquals("Ожидается сообщение о нехватке данных для входа", expected,responseMessage);
//    }

    @Test
    @DisplayName("Проверка ошибки при авторизации без логина") // имя теста
    public void loginCourierWithoutLoginBadRequest400() {
        String expected = "Недостаточно данных для входа";
        courier = new CourierPOJO(null, "l0g1n", null);
        CourierCredentials credentials = CourierCredentials.from(courier);
        String responseMessage = courierAPI.courierLoginNotEnoughDataStatus400(credentials);
        Assert.assertEquals("Ожидается сообщение о нехватке данных для входа", expected, responseMessage);
    }

    @Test
    @DisplayName("Проверка ошибки при авторизации не существущего курьера") // имя теста
    public void loginCourierDoesNotExistBadRequest404() {
        String expected = "Учетная запись не найдена";
        courier = new CourierPOJO("l0g1n", "l0g1n", null);
        CourierCredentials credentials = CourierCredentials.from(courier);
        String responseMessage = courierAPI.courierLoginCourierDoesNotExistStatus400(credentials);
        Assert.assertEquals("Ожидается сообщение о том, что не найдена учетная запись", expected, responseMessage);
    }


    @After
    public void teardown() {
        if (createdCourierId != 0) {
            courierAPI.deleteCourier(createdCourierId);
        }
    }
}
