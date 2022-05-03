package API;

import POJO.OrderPOJO;
import POJO.OrderTrack;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class OrderAPI extends MainAPI {
    @Step("Послать POST запрос на ручку /orders")
    public Response sendPostRequestCreateOrder(OrderPOJO orderPOJO) {
        return reqSpec.body(orderPOJO)
                .when()
                .post("/orders");
    }

    @Step("Получить статус успешного создания заказа - 201")
    public int responseCreatedOrderStatus201(Response response) {
        return response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Step("Послать GET запрос на ручку /orders, чтобы получить список заказов")
    public List<Object[]> sendGetRequestGetOrderList() {
        return reqSpec.get("/orders")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders");
    }
    //НЕ РАБОТАЕТ, ОШИБКА - НЕ ХВАТАЕТ ДАНЫХ, ХОТЯ ПРОБОВАЛ ДЕЛАТЬ TRACK int и String, разницы нет, без понятия в чем прикол
    //@Step("Отмена заказа")
//    public void cancelOrder(OrderTrack orderTrack) {
//        reqSpec.body(orderTrack)
//                .when()
//                .put("/orders/cancel")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
//    }
}
